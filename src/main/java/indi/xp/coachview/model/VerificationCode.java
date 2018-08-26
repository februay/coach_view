package indi.xp.coachview.model;

import java.io.Serializable;

public class VerificationCode implements Serializable {

    private static final long serialVersionUID = 2323992623275494903L;

    public static final String TYPE_PHONE_LOGIN = "phone-login";

    private String key;
    private String value;
    private String type;
    private long expiredTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

}
