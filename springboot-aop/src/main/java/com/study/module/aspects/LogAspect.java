package com.study.module.aspects;

import com.alibaba.fastjson.JSONObject;
import com.study.module.annotation.Log;
import com.study.module.entity.SysOperLog;
import com.study.module.service.SysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zl
 * @date 2021/1/19 18:42
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

//    @Autowired
//    private AsyncLogService asyncLogService;

    private static final String SPELL_TIME = "spellTime";
    private static final String PARAMS = "params";
    private static final String RESPONSE_DATA = "responseData";

    @Autowired
    private SysOperLogService operLogService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.study.module.annotation.Log)")
    public void logPointCut() {
    }

    @Before(value = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("传入的参数有：" + args);
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Map<String, Object> jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 环绕
     *
     * @param point 切点
     * @return
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            long spellTime = (endTime - beginTime);
            System.out.println("方法请求耗时：" + spellTime);
            Map<String, Object> jsonResult = new HashMap<>(16);
            jsonResult.put(SPELL_TIME, spellTime);
            // 方法入参
            String args = Arrays.toString(point.getArgs());
            jsonResult.put(PARAMS, args);
            // 方法返回值
            jsonResult.put(RESPONSE_DATA, point.proceed());
            handleLog(point, null, jsonResult);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 核心处理方法：正对切点需要处理的参数
     *
     * @param joinPoint  切点
     * @param e          异常值传入
     * @param jsonResult 其他参数
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception e, Map<String, Object> jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(0);
            if (e != null) {
                operLog.setStatus(1);
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置操作时间
            operLog.setOperTime(new Date());
            // 设置方法总耗时  和  参数记录
            if (ObjectUtils.isEmpty(jsonResult.isEmpty())) {
                System.out.println("0");
                System.out.println("params is null");
            } else {
                // 耗时
                if (ObjectUtils.isEmpty(jsonResult.get(SPELL_TIME))) {
                    System.out.println("spell time is 0");
                } else {
                    System.out.println(Long.parseLong(jsonResult.get(SPELL_TIME).toString()));
                    operLog.setSpellTime(Long.parseLong(jsonResult.get(SPELL_TIME).toString()));
                }

                // 参数
                if (ObjectUtils.isEmpty(jsonResult.get(PARAMS))) {
                    System.out.println("params is null");
                } else {
                    System.out.println(jsonResult.get(PARAMS));
                    operLog.setParams(String.valueOf(jsonResult.get(PARAMS)));
                }
            }
            // 获取方法返回值
            operLog.setResponseData(JSONObject.toJSONString(jsonResult.get(RESPONSE_DATA)));
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // 保存数据库
            operLogService.insert(operLog);
        } catch (Exception exp) {
            log.error("==前置通知异常==");
            log.error("日志异常信息 {}", exp);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
