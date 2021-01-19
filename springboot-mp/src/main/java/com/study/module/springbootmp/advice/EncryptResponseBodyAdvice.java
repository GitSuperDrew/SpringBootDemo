package com.study.module.springbootmp.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.module.springbootmp.annotation.SIProtection;
import com.study.module.springbootmp.service.SecretProcess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zl
 * @date 2021/1/18 19:12
 **/
@ControllerAdvice
@ConditionalOnProperty(name = "secret.enabled", havingValue = "true")
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private SecretProcess secretProcess;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(SIProtection.class)
                || returnType.getMethod().getDeclaringClass().isAnnotationPresent(SIProtection.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        try {
            String jsonStr = new ObjectMapper().writeValueAsString(body);
            return secretProcess.encrypt(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

