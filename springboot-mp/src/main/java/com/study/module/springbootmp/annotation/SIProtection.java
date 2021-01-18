package com.study.module.springbootmp.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author zl
 * @date 2021/1/18 19:07
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SIProtection {
}
