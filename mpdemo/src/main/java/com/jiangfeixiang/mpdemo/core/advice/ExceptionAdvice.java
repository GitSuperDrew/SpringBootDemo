package com.jiangfeixiang.mpdemo.core.advice;

import com.jiangfeixiang.mpdemo.core.exception.MsgCode;
import com.jiangfeixiang.mpdemo.springbootmp.util.CommonConstant;
import com.jiangfeixiang.mpdemo.springbootmp.util.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import static com.jiangfeixiang.mpdemo.core.exception.CommMsgCode.NOT_FOUND;
import static com.jiangfeixiang.mpdemo.core.exception.CommMsgCode.NOT_SUPPORTED;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 全局异常捕获类
 *
 * @author zl
 * @create 2022-07-02 9:59
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return Result.error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, "系统发生异常《NULL》");
    }

    /**
     * 405 not support异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ErrorResp onException(HttpRequestMethodNotSupportedException e,
                                 HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("uri:{},code:{},message:{}", uri, NOT_SUPPORTED.getCode(), e.getMessage());

        return createErrorResp(NOT_SUPPORTED, null);
    }

    private ErrorResp createErrorResp(MsgCode msgCode, String message) {
        return new ErrorResp(msgCode.getCode(), isNotBlank(message) ? message : msgCode.getMessage());
    }

    private void createLog(Exception e, String uri, String params) {
        log.error("uri:" + uri + ",params:" + params, e);
    }


    @Data
    public static class ErrorResp {

        private int code;
        private String msg;
        /**
         * 响应时间戳
         */
        private Long timestamp = System.currentTimeMillis();

        public ErrorResp(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 404 not support异常
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ErrorResp onException(NoHandlerFoundException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("uri:{},code:{},message:{}", uri, NOT_FOUND.getCode(), e.getMessage());

        return createErrorResp(NOT_FOUND, null);
    }


    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return Result.error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, "系统出现异常错误");
    }
}
