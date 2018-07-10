package indi.xp.common.restful;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.common.constants.CommonConstants;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.exception.ErrorCodeEnum;
import indi.xp.common.exception.ExceptionUtil;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    /**
     * 统一的rest接口异常处理器
     *
     * @param e
     *            捕获的异常
     * @return 异常信息
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    private <T> ResponseResult<T> globalExceptionHandler(HttpServletRequest request, Exception e) {
        this.logException(request, e, "接口异常");
        ResponseResult<T> response = ResponseResult.buildErrorResult(ErrorCodeEnum.INTERNAL_INTERFACE_ERROR);
        String stackTrace = e.getMessage() + ": " + ExceptionUtil.getExceptionStackTraceStr(e);
        response.setStackTrace(stackTrace);
        return response;
    }

    /**
     * 统一的BusinessException异常处理器
     *
     * @param e
     *            捕获的异常
     * @return 异常信息
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    private <T> ResponseResult<T> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        String errorMsg = e.getErrorMsg();
        e.setErrorMsg(errorMsg);
        this.logException(request, e, "Business接口调用异常");
        return ResponseResult.buildErrorResult(e);
    }

    /**
     * bean校验未通过异常
     *
     * @see javax.validation.Valid
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseResult<T> illegalParamsExceptionHandler(HttpServletRequest request,
        MethodArgumentNotValidException e) {
        this.logException(request, e, "请求参数不合法");
        return ResponseResult.buildErrorResult(ErrorCodeEnum.ILLEGAL_PARAMS);
    }

    /**
     * 打印异常上下文信息
     */
    private void logException(HttpServletRequest request, Exception e, String errorMsg) {
        String traceId = request.getHeader(CommonConstants.TRACE_ID);
        traceId = StringUtils.isNotBlank(traceId) ? traceId : UuidUtils.generateUUID();

        logger.error("---------> 异常请求的TraceId: " + traceId);
        logger.error("---------> 异常请求的Method: " + request.getMethod());
        logger.error("---------> 异常请求的URI: " + request.getRequestURI());
        logger.error("---------> 异常请求的URL: " + request.getRequestURL().toString());
        logger.error("---------> 请求Header Token: " + request.getHeader(CommonConstants.TOKEN));
        logger.error("---------> 请求Header Uid: " + request.getHeader(CommonConstants.UID));
        logger.error("---------> 请求Header Language: " + request.getHeader(CommonConstants.LANGUAGE));
        logger.error("---------> 异常请求的Body: " + request.getAttribute("requestBody"));
        logger.error("---------> 异常请求的Param: " + request.getQueryString());
        logger.error("---------> " + errorMsg + ": ", e);
    }

}
