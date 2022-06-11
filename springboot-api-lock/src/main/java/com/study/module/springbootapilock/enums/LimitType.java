package com.study.module.springbootapilock.enums;

/**
 * @author zl
 * @create 2022-06-11 10:19
 */
public enum LimitType {

    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求者IP进行限流
     */
    IP

}
