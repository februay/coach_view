package indi.xp.common.exception;

public enum ErrorCodeEnum {

    // server
    ILLEGAL_PARAMS("ILLEGAL_PARAMS", "请求参数不合法!"), 
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "服务器内部异常!"), 
    INTERNAL_INTERFACE_ERROR("INTERNAL_INTERFACE_ERROR", "接口内部异常!");

    ErrorCodeEnum(String code, String message) {
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
