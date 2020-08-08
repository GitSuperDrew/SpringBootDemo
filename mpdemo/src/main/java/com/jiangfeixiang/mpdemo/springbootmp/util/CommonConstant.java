package com.jiangfeixiang.mpdemo.springbootmp.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 系统常量类
 *
 * @author Administrator
 * @date 2020/8/8 下午 5:36
 */
public class CommonConstant {
    public static Integer SC_OK_200 = 200;
    public static Integer SC_FAIL_400 = 400;
    public static Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    public static Integer SC_NO_AUTHZ = 401;

    //  数据操作错误定义

    public static Map<String, Object> BODY_NOT_MATCH = ImmutableMap.of("400", "请求数据格式错误！");
    public static Map<String, Object> SIGNATURE_NOT_MATCH = ImmutableMap.of("401","请求的数字签名不匹配!");
    public static Map<String, Object> NOT_FOUND = ImmutableMap.of("404", "未找到该资源!");
    public static Map<String, Object> INTERNAL_SERVER_ERROR = ImmutableMap.of("500", "服务器内部错误!");
    public static Map<String, Object> SERVER_BUSY = ImmutableMap.of("503","服务器正忙，请稍后再试!");


}
