package com.study.module.springbootmp.advice;

import com.study.module.springbootmp.annotation.SIProtection;
import com.study.module.springbootmp.service.SecretProcess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author zl
 * @date 2021/1/18 19:06
 **/
@ControllerAdvice
@ConditionalOnProperty(name = "secret.enabled", havingValue = "true")
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Resource
    private SecretProcess secretProcess;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(SIProtection.class)
                || methodParameter.getMethod().getDeclaringClass().isAnnotationPresent(SIProtection.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        final String body = secretProcess.decrypt(inToString(inputMessage.getBody()));
        return new HttpInputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(body.getBytes());
            }
        };
    }

    private String inToString(InputStream is) {
        byte[] buf = new byte[10 * 1024];
        int leng = -1;
        StringBuilder sb = new StringBuilder();
        try {
            while ((leng = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, leng));
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
