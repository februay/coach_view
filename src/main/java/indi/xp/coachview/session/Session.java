package indi.xp.coachview.session;

import java.io.Serializable;

import indi.xp.coachview.model.vo.UserVo;

public class Session implements Serializable {

    private static final long serialVersionUID = -962224562523414811L;

    private String sessionId;
    private String uid;
    private UserVo sessionUser;
    private Long createTime = System.currentTimeMillis();
    private Long updateSessionTime;
    private Long sessionValidateTime;
    private Boolean loginBySuperKey; // 是否使用superKey登录产品
    private SessionConext sessionConext;

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
        if (sessionUser != null) {
            this.uid = sessionUser.getUid();
        }
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

    public SessionConext getSessionConext() {
        return sessionConext;
    }

    public void setSessionConext(SessionConext sessionConext) {
        this.sessionConext = sessionConext;
    }

}
