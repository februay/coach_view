package indi.xp.coachview.model.vo;

import java.io.Serializable;

public class UserSignInVo implements Serializable {

    private static final long serialVersionUID = -738844311442462185L;
    
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_PASSWORD = "password";

    private String key; // 手机号、用户名、邮箱
    private String value; // 验证码、密码
    private String type; // phone | password

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

}
