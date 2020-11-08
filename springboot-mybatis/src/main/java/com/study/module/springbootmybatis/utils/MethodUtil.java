package com.study.module.springbootmybatis.utils;

/**
 * @author Administrator
 * @date 2020/11/8 下午 6:39
 */
public class MethodUtil {
    /**
     * 私有化工具类 防止被实例化
     */
    private MethodUtil() {
    }

    public static String getLineInfo() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getFileName() + " -> " + ste.getLineNumber() + "行";
    }
}
