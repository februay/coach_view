package indi.xp.coachview.session;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import indi.xp.coachview.model.vo.UserVo;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Component("sessionManager")
public class SessionManager {

    private Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private long defaultSessionValidateTime = 24 * 60 * 60 * 1000; // 毫秒(24小时)
    private long rememberMeSessionValidateTime = 7 * 24 * 60 * 60 * 1000; // 毫秒(7天)

    private static HashMap<String, Session> sessionMap = new HashMap<String, Session>();

    public Session addSession(UserVo user) {
        return this.addSession(user, false);
    }

    public Session addSession(UserVo user, boolean rememberMe) {
        Session session = this.buildSession(user, rememberMe);
        if (session != null) {
            sessionMap.put(session.getSessionId(), session);
        }
        return session;
    }

    public Session getSession(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return null;
        }
        Session session = sessionMap.get(sessionId);
        if (session != null && session.getSessionValidateTime() < System.currentTimeMillis()) {
            this.clearSession(sessionId);
            return null;
        }
        return session;
    }

    public void clearSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    /**
     * 清空当前uid登录的所有session.
     */
    public void clearUserSession(String uid) {
        // TODO: 清空当前uid登录的所有session.
    }

    // 创建新session
    public Session buildSession(UserVo user, boolean rememberMe) {
        if (user != null) {
            String uid = user.getUid();
            String sessionId = uid + ":" + UuidUtils.generateUUID();
            Session session = new Session(sessionId);
            session.setSessionUser(user);
            session.setSessionConext(SessionConext.build(sessionId, user));
            if (rememberMe) {
                session.setSessionValidateTime(rememberMeSessionValidateTime + System.currentTimeMillis());
            } else {
                session.setSessionValidateTime(defaultSessionValidateTime + System.currentTimeMillis());
            }
            logger.info(sessionId + ": build remember<{}> session time to " + session.getSessionValidateTime(),
                rememberMe);
            return session;
        }
        return null;
    }

    public UserVo getSessionUser(String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            Session session = this.getSession(sessionId);
            if (session != null) {
                return session.getSessionUser();
            } else {
                return null;
            }
        }
        return null;
    }

    public synchronized void updateSessionTime(String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            Session session = this.getSession(sessionId);
            if (session != null) {
                session.setSessionValidateTime(session.getSessionValidateTime() + defaultSessionValidateTime);
                logger.info(sessionId + ": update session time to " + session.getSessionValidateTime());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, String fieldName)
        throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Session session = getSession(key);
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, Session.class);
        Method getMethod = pd.getReadMethod();
        return (T) getMethod.invoke(session);
    }

}
