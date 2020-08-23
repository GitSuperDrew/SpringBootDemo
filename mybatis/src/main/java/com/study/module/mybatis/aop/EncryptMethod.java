package com.study.module.mybatis.aop;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 安全字段注解
 * 添加在需要加密/解密的方法上
 * 实现自动加密解密
 *
 * @author Administrator
 * @date 2020/8/23 下午 5:54
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface EncryptMethod {
}
