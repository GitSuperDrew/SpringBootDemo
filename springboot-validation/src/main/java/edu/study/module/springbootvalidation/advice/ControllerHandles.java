package edu.study.module.springbootvalidation.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zl
 * @date 2021/1/24 11:30
 **/
@ControllerAdvice(value = {"edu.study.module.springbootvalidation.controller.*"})
public class ControllerHandles {

    @ExceptionHandler
    public String errorHandler(Exception e) {
        return "error";
    }
}
