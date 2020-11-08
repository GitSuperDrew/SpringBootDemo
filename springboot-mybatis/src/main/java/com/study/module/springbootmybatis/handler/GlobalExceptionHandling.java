package com.study.module.springbootmybatis.handler;

import com.study.module.springbootmybatis.enums.ResultEnum;
import com.study.module.springbootmybatis.exceptions.CustomException;
import com.study.module.springbootmybatis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author Administrator
 * @date 2020/11/8 下午 6:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling {

    /**
     * 自定义异常（显示出错位置和出错信息）
     *
     * @param e 异常信息
     * @return
     */
    @ExceptionHandler(value = {CustomException.class})
    public Result<?> processException(CustomException e) {
        log.error("位置：{} -> 错误信息：{}", e.getMethod(), e.getLocalizedMessage());
        return Result.error(Objects.requireNonNull(ResultEnum.getByCode(e.getCode())));
    }

    /**
     * 通用异常
     *
     * @param e 异常信息
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result<?> exception(Exception e) {
        e.printStackTrace();
        return Result.error(ResultEnum.UNKNOWN_EXCEPTION);
    }
}
