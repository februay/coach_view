package indi.xp.coachview.common;

public enum BusinessErrorCodeEnum {

    USER_EXISTS("USER_EXISTS", "用户已存在");

    BusinessErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
