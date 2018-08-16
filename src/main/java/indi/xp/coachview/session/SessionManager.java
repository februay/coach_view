package indi.xp.coachview.session;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import indi.xp.coachview.model.vo.UserVo;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.DateUtils;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@Component("sessionManager")
public class SessionManager {

    private Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private long defaultSessionValidateTime = 2 * 60 * 60 * 1000; // 毫秒(2小时)
    private long rememberMeSessionValidateTime = 7 * 24 * 60 * 60 * 1000; // 毫秒(7天)

    private static HashMap<String, Session> sessionMap = new HashMap<String, Session>();

    private static boolean isActive = false; // 启动后标记为active状态，避免初始化多次
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

    @PostConstruct
    private synchronized void startSessionCheckSchedule() {
        // 启动定时任务扫描自动更新source任务队列，每个任务完成后触发下一个(最少有一个线程)
        if (!isActive) {
            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        checkSessionMap();
                    } catch (Exception e) {
                        logger.error("checkSessionMap error:" + e.getMessage(), e);
                    }
                }
            }, 10, 10, TimeUnit.MINUTES);
            isActive = true;
        }
    }

    /**
     * 检查sessionMap， 并清理过期的session
     * 
     * @date: 2018年7月28日
     * @author peng.xu
     */
    private void checkSessionMap() {
        logger.info("start checkSessionMap ...");
        int sessionCount = 0;
        if (CollectionUtils.isNotEmpty(sessionMap)) {
            Iterator<Map.Entry<String, Session>> iterator = sessionMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Session> entury = iterator.next();
                String sessionId = entury.getKey();
                Session session = entury.getValue();
                if (entury.getValue().getSessionValidateTime() < System.currentTimeMillis()) {
                    logger.info(" >>> remove session<{}>", sessionId);
                    iterator.remove();
                } else {
                    sessionCount++;
                    logger.info(
                        " >>> {}\t current session<{}> to time"
                            + DateUtils.formatDate(session.getSessionValidateTime(), "yyyy-MM-dd HH:mm:ss"),
                        sessionCount, sessionId);
                }
            }
        }
        logger.info("success checkSessionMap: sessionCount:" + sessionCount);
    }

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
            String sessionId = uid + ":" + DateUtils.getDate("yyyyMMddHHmmss") + "-" + UuidUtils.generateUUID();
            Session session = new Session(sessionId);
            session.setSessionUser(user);
            session.setSessionConext(SessionConext.build(sessionId, user));
            if (rememberMe) {
                session.setSessionValidateTime(rememberMeSessionValidateTime + System.currentTimeMillis());
            } else {
                session.setSessionValidateTime(defaultSessionValidateTime + System.currentTimeMillis());
            }
            logger.info(sessionId + ": build remember<{}> session time to "
                + DateUtils.formatDate(session.getSessionValidateTime(), "yyyy-MM-dd HH:mm:ss"), rememberMe);
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
                session.setSessionValidateTime(System.currentTimeMillis() + defaultSessionValidateTime);
                logger.info(sessionId + ": update session time to "
                    + DateUtils.formatDate(session.getSessionValidateTime(), "yyyy-MM-dd HH:mm:ss"));
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
