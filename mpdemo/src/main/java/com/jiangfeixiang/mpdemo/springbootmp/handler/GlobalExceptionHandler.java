package com.jiangfeixiang.mpdemo.springbootmp.handler;

import com.jiangfeixiang.mpdemo.springbootmp.util.CommonConstant;
import com.jiangfeixiang.mpdemo.springbootmp.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获类
 *
 * @author Administrator
 * @date 2020/8/8 下午 5:33
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        logger.error("发生空指针异常！原因是:", e);
        return Result.error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, "系统发生异常《NULL》");
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
        logger.error("未知异常！原因是:", e);
        return Result.error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, "系统出现异常错误");
    }
}
