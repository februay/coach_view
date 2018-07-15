package indi.xp.coachview.model.vo;

import java.io.Serializable;

public class UserSignInVo implements Serializable {

    private static final long serialVersionUID = -738844311442462185L;
    
    public static final String TYPE_PHONE = "phone";

    private String key;
    private String value;
    private String type;

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
