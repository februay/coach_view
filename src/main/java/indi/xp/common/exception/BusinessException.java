package indi.xp.common.exception;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.BusinessErrorCodeEnum;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5565189427865086856L;

    private String errorCode;
    private String errorMsg;
    private Map<String, Object> params;

    public BusinessException() {
    }

    public BusinessException(String errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, Map<String, Object> params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorCode, String errorMsg, Map<String, Object> params) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.params = params;
    }

    public BusinessException(BusinessErrorCodeEnum errorCodeEnum) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMsg = errorCodeEnum.getMessage();
    }

    public BusinessException(BusinessErrorCodeEnum errorCodeEnum, Map<String, Object> params) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMsg = errorCodeEnum.getMessage();
        this.params = params;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String getMessage() {
        return this.errorCode + " ==> " + this.errorMsg + " ==> " + JSON.toJSONString(this.params);
    }

}
