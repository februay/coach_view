package indi.xp.coachview.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.SysRoleEnum;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

/**
 * session上下文, 用于保存当前session上下文的通用参数信息（用于数据权限获取通用参数等）
 */
public class SessionConext implements SessionConextConstants, Serializable {

    private static final long serialVersionUID = 7020612461291439779L;

    private static ThreadLocal<SessionConext> threadLocalSessionContext = new ThreadLocal<SessionConext>();

    private String sessionId;
    private String uid;
    private UserVo sessionUser;
    private Map<String, Object> attributeMap = new HashMap<>();
    private Map<String, String> attributeClazzMap = new HashMap<>();

    /**
     * 获取当前线程的SessionContext对象
     */
    public static SessionConext getThreadLocalSessionContext() {
        SessionConext sessionConext = threadLocalSessionContext.get();
        return sessionConext;
    }

    /**
     * 设置当前线程的SessionContext对象
     */
    public static void setThreadLocalSessionContext(SessionConext sessionConext) {
        threadLocalSessionContext.set(sessionConext);
    }

    public static SessionConext build(String sessionId, UserVo user) {
        return new SessionConext(sessionId, user);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T getAttribute(String key) throws ClassNotFoundException {
        if (attributeClazzMap.containsKey(key) && attributeMap.containsKey(key)) {
            Class clazz = Class.forName(attributeClazzMap.get(key));
            return (T) JSON.parseObject(JSON.toJSONString(attributeMap.get(key)), clazz);
        } else {
            return null;
        }
    }

    public SessionConext addAttribute(String key, Object object) {
        this.attributeMap.put(key, object);
        this.attributeClazzMap.put(key, object.getClass().getName());
        return this;
    }

    public boolean hasRole(SysRoleEnum sysRoleEnum) {
        if (this.getSessionUser() != null && CollectionUtils.isNotEmpty(this.getSessionUser().getRoles())) {
            List<String> roles = this.getSessionUser().getRoles();
            for (String role : roles) {
                if (sysRoleEnum.getCode().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean available() {
        return this.getSessionUser() != null
            && StringUtils.isNotBlank(this.getSessionUser().getUid(), this.getSessionId());
    }

    public SessionConext() {
    }

    public SessionConext(String sessionId, UserVo sessionUser) {
        this.sessionId = sessionId;
        this.sessionUser = sessionUser;
        if (sessionUser != null) {
            this.uid = sessionUser.getUid();
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserVo getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(UserVo sessionUser) {
        this.sessionUser = sessionUser;
    }

}
