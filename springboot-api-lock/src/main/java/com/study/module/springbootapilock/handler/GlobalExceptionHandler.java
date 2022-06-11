package com.study.module.springbootapilock.handler;

import com.study.module.springbootapilock.enums.CommonCode;
import com.study.module.springbootapilock.exception.CustomException;
import com.study.module.springbootapilock.exception.ServiceException;
import com.study.module.springbootapilock.util.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常捕获类
 *
 * @author zl
 * @create 2022-06-11 10:27
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ApiResult serviceException(ServiceException e) {
        return ApiResult.result(CommonCode.FAIL, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ApiResult exception(CustomException e) {
        return ApiResult.result(CommonCode.FAIL, e.getMessage());
    }
}
