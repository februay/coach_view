package indi.xp.coachview.common;

public enum BusinessErrorCodeEnum {

    TEAM_MEMBER_EXISTS("TEAM_MEMBER_EXISTS", "球队成员已存在"),
    IMPORT_FILE_NOT_MATCH_OBJECT("IMPORT_FILE_NOT_MATCH_OBJECT", "导入文件不匹配"),
    USER_EXISTS("USER_EXISTS", "用户已存在"),
    USER_PHONE_NUMBER_IS_BLANK("USER_PHONE_NUMBER_IS_BLANK", "用户手机号为空"),
    USER_NOT_EXISTS("USER_NOT_EXISTS", "用户不存在"),
    USER_VERIFICATION_CODE_ERROR("USER_VERIFICATION_CODE_ERROR", "用户验证码错误"),
    NO_SESSION("NO_SESSION", "用户未登录"),
    USER_CLUB_ID_IS_NULL("USER_CLUB_ID_IS_NULL", "非管理员用户，clubId不能为空"),
    USER_AND_SESSION_NOT_MATCH("USER_AND_SESSION_NOT_MATCH", "用户和session不匹配");

    private BusinessErrorCodeEnum(String code, String message) {
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
