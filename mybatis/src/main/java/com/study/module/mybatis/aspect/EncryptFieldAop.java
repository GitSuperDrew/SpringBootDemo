package com.study.module.mybatis.aspect;

import com.study.module.mybatis.aop.EncryptField;
import com.study.module.mybatis.util.AseUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 安全字段加密/解密 切面
 *
 * @author Administrator
 * @date 2020/8/23 下午 6:00
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Component
@Slf4j
public class EncryptFieldAop {

    @Value("${secretKey}")
    private String secretKey;

    @Pointcut("@annotation(com.study.module.mybatis.aop.EncryptMethod)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object responseObj = null;
        try {
            Object requestObj = joinPoint.getArgs()[0];
            handleEncrypt(requestObj);
            responseObj = joinPoint.proceed();
            handleDecrypt(responseObj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("SecureFieldAop处理出现异常{}", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("SecureFieldAop处理出现异常{}", throwable);
        }
        return responseObj;
    }

    /**
     * 处理加密
     *
     * @param requestObj
     */
    private void handleEncrypt(Object requestObj) throws IllegalAccessException {
        if (Objects.isNull(requestObj)) {
            return;
        }
        Field[] fields = requestObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                String plaintextValue = (String) field.get(requestObj);
                String encryptValue = AseUtil.encrypt(plaintextValue, secretKey);
                field.set(requestObj, encryptValue);
            }
        }
    }

    /**
     * 处理解密
     *
     * @param responseObj
     */
    private Object handleDecrypt(Object responseObj) throws IllegalAccessException {
        if (Objects.isNull(responseObj)) {
            return null;
        }
        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                String encryptValue = (String) field.get(responseObj);
                String plaintextValue = AseUtil.decrypt(encryptValue, secretKey);
                field.set(responseObj, plaintextValue);
            }
        }
        return responseObj;
    }
}
