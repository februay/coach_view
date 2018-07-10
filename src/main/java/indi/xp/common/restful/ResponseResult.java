package indi.xp.common.restful;

import java.util.Map;

import indi.xp.common.exception.BusinessException;
import indi.xp.common.exception.ErrorCodeEnum;

/**
 * 统一请求返回结果.
 */
public class ResponseResult<T> {

    private Boolean success;

    private T data;

    private String message;

    private String errorCode;

    private String stackTrace;

    private Map<String, Object> params;

    private ResponseResult() {
    }

    public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult<>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 生成响应成功的(不带正文)的结果
     * 
     * @param message
     *            成功提示信息
     */
    public static ResponseResult<Object> buildMessageResult(String message) {
        ResponseResult<Object> responseResult = ResponseResult.newInstance();
        responseResult.setSuccess(true);
        responseResult.setMessage(message);
        return responseResult;
    }

    /**
     * 生成响应成功(带正文)的结果
     * 
     * @param data
     *            结果正文
     * @param message
     *            成功提示信息
     */
    public static <T> ResponseResult<T> buildResult(T data, String message) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 生成响应成功无正文的结果
     *
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> buildResult() {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(true);
        return result;
    }

    /**
     * 生成响应成功(带正文没有描述)的结果
     *
     * @param data
     *            结果正文
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> buildResult(T data) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 生成响应失败的结果
     */
    public static <T> ResponseResult<T> buildErrorResult(String errorCode, String message) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
    }

    /**
     * 生成响应失败的结果
     */
    public static <T> ResponseResult<T> buildErrorResult(String errorCode) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        return result;
    }

    /**
     * 生成响应失败的结果
     */
    public static <T> ResponseResult<T> buildErrorResult(BusinessException e) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorCode(e.getErrorCode());
        result.setMessage(e.getErrorMsg());
        result.setParams(e.getParams());
        return result;
    }

    public static <T> ResponseResult<T> buildErrorResult(ErrorCodeEnum errorCodeEnum) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorCode(errorCodeEnum.getCode());
        result.setMessage(errorCodeEnum.getMessage());
        return result;
    }

}
