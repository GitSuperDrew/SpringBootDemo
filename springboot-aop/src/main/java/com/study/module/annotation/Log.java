package com.study.module.annotation;

import com.study.module.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @author zl
 * @date 2021/1/19 18:41
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */

    BusinessType businessType() default BusinessType.OTHER;
}
