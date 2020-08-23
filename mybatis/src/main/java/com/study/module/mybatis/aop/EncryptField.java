package com.study.module.mybatis.aop;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 安全字段注解（放置在加密/解密的字段上）
 *
 * @author Administrator
 * @date 2020/8/23 下午 5:55
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface EncryptField {
}
