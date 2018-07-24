package indi.xp.coachview.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.model.vo.UserVo;

public class Session implements Serializable {

    private static final long serialVersionUID = -962224562523414811L;

    private String sessionId;
    private UserVo sessionUser;
    private Long createTime = System.currentTimeMillis();
    private Long updateSessionTime;
    private Long sessionValidateTime;
    private Map<String, Object> attributeMap = new HashMap<>();
    private Map<String, String> clazzMap = new HashMap<>();
    private Boolean loginBySuperKey; // 是否使用superKey登录产品

    public Session() {
    }

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserVo getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(UserVo sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Long getSessionValidateTime() {
        return sessionValidateTime;
    }

    public void setSessionValidateTime(Long sessionValidateTime) {
        this.sessionValidateTime = sessionValidateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) throws ClassNotFoundException {
        if (clazzMap.containsKey(key) && attributeMap.containsKey(key)) {
            return (T) JSON.parseObject(JSON.toJSONString(attributeMap.get(key)), bulidClass(clazzMap.get(key)));
        } else {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public Class bulidClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public void setAttribute(String key, Object object) {
        attributeMap.put(key, object);
        clazzMap.put(key, object.getClass().getName());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public Map<String, String> getClazzMap() {
        return clazzMap;
    }

    public void setClazzMap(Map<String, String> clazzMap) {
        this.clazzMap = clazzMap;
    }

    public Long getUpdateSessionTime() {
        return updateSessionTime;
    }

    public void setUpdateSessionTime(Long updateSessionTime) {
        this.updateSessionTime = updateSessionTime;
    }

    public Boolean getLoginBySuperKey() {
        return loginBySuperKey;
    }

    public void setLoginBySuperKey(Boolean loginBySuperKey) {
        this.loginBySuperKey = loginBySuperKey;
    }

}
