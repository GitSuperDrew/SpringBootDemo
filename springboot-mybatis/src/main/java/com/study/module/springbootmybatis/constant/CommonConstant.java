package com.study.module.springbootmybatis.constant;

import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2020/11/7 下午 10:36
 */
public class CommonConstant {
    /**
     * 日期时间
     */
    public final static String DATE_TIME_EN = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_CN = "yyyy年MM月dd日 HH时mm分ss";
    /**
     * 日期
     */
    public final static String DATE_EN = "yyyy-MM-dd";
    public final static String DATE_CN = "yyyy年MM月dd日";
    /**
     * 时间
     */
    public final static String TIME_EN = "HH:mm:ss";
    public final static String TIME_CN = "HH时m分ss";

    /**
     * 严格手机号码匹配
     */
    public final static String REG_PHONE_STRICT = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";

    /**
     * 座机号码正则
     */
    public final static String REG_MOBILE = "^\\d{3}-\\d{8}$|^\\d{4}-\\d{7}$";

    /**
     * 直至1代15位和2代18位身份证号码验证
     */
    public final static String REG_IDENTITY = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";

    /**
     * 最大长度32
     */
    public final static String REG_MAX_LENGTH_32 = "^.{0,32}$";
    /**
     * 最大长度位1024
     */
    public final static String REG_MAX_LENGTH_1024 = "^.{0,1024}$";

    public static void main(String[] args) {
        String pattern = "诸葛量";
        System.out.println(Pattern.matches(REG_MAX_LENGTH_32, pattern));
    }


}
