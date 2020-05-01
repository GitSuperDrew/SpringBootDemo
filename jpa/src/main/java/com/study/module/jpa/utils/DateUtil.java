package com.study.module.jpa.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

/**
 * 基于JDK13，联合Joda-Time类的时间工具类<br/>
 * Joda 是一种令人惊奇的高效工具。无论您是计算日期、打印日期，或是解析日期，Joda都将是工具箱中的便捷工具<br/>
 * 1.引入joda-time的jar包
 * 2.官方学习网址：https://www.joda.org/joda-time/
 * 3.Maven引入示例如下：
 * <pre>
 *   ========================
 *   <dependency>
 *     <groupId>joda-time</groupId>
 *     <artifactId>joda-time</artifactId>
 *     <version>2.10.5</version>
 *   </dependency>
 *   ========================
 * <pre/>
 *
 * @author Administrator
 */
public class DateUtil {

    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS_CN = "yyyy年MM月dd日 HH时:mm分:ss秒";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD_CN = "yyyy年MM月dd日";
    public static final String HMS = "HH:mm:ss";
    public static final String HMS_CN = "HH时mm分ss秒";
    public static final String MdyhmsSA = "MM/dd/yyyy hh:mm:ss.SSSa"; // 09/06/2009 02:30:00.000PM
    public static final String dMyHms = "dd-MM-yyyy HH:mm:ss"; // 06-Sep-2009 14:30:00
    public static final String EdMyHmsA = "EEEE dd MMMM, yyyy HH:mm:ssa";// Sunday 06 September, 2009 14:30:00PM
    public static final String MdyHmZZ = "MM/dd/yyyy HH:mm ZZZZ"; // 09/06/2009 14:30 America/Chicago
    public static final String MdyHmZ = "MM/dd/yyyy HH:mm Z"; // 09/06/2009 14:30 -0500

    public static final String DAY = "day", WEEK = "week", MONTH = "month", YEAR = "year";
    public static final String HOUR = "hour", MINUTE = "minute", SECOND = "second";

    /**
     * 日期 → 字符
     *
     * @param date    日期
     * @param pattern 格式（例如 ”yyyy-MM-dd HH:mm:ss“）
     * @return 数据的格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr(Date date, String pattern) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    public static String getDateTimeStrLocale(Date date, String pattern) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern, Locale.CHINA);
    }

    public static Date getDateTime(String dateTimeStr, String pattern) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = new DateTime(dateTimeStr).toDateTime();
        return dateTime.toDate();
    }

    /**
     * 时间加减运算
     *
     * @param datetime joda-time时间类
     * @param period   周期（年月日时分秒周）
     * @param count    偏移量
     * @return 时间字符串（格式：yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime(DateTime datetime, String period, int count) {
        DateTime result = new DateTime();
        switch (period) {
            case DAY:
                result = datetime.plusDays(count);
                break;
            case MONTH:
                result = datetime.plusMonths(count);
                break;
            case YEAR:
                result = datetime.plusYears(count);
                break;
            case WEEK:
                result = datetime.plusWeeks(count);
                break;
            case HOUR:
                result = datetime.plusHours(count);
                break;
            case MINUTE:
                result = datetime.plusMinutes(count);
                break;
            case SECOND:
                result = datetime.plusSeconds(count);
                break;
            default:
                break;
        }
        return result.toString(YMDHMS, Locale.CHINA);
    }


    /**
     * 重置当前时间(如果有非法值输入则自动默认为日期元素的起始值)
     *
     * @param year   年份（1900~9999）
     * @param month  月份（1~12）
     * @param day    日（如果是2月，平年最大为28日，闰年最大为29日）
     * @param hour   小时（0~23）
     * @param minute 分钟（0~59）
     * @param second 秒钟（0~59）
     * @return
     */
    public static DateTime setCurrDateTime(int year, int month, int day, int hour, int minute, int second) {
        year = String.valueOf(year).length() <= 9999 && String.valueOf(year).length() >= 1900 ? year :
                new DateTime().getYear();
        month = month >= 1 && month <= 12 ? month : 1;
        List<Integer> monthHas31 = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
        if (month == 2) {
            // flag(true:平年，false:闰年)
            boolean flag = (year % 4 != 0 || year % 100 == 0 && year % 400 != 0);
            day = day >= 1 && day <= (flag ? 28 : 29) ? day : 1;
        } else {
            day = day >= 1 && day <= (monthHas31.contains(month) ? 31 : 30) ? day : 1;
        }
        hour = (hour >= 0 && hour <= 23) ? hour : 0;
        minute = (minute >= 0 && minute <= 59) ? minute : 0;
        second = (second >= 0 && second <= 59) ? second : 0;
        DateTime dateTime = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day)
                .withHourOfDay(hour).withMinuteOfHour(minute).withSecondOfMinute(second);
        return dateTime;
    }

    /**
     * d1 是否在 d2 之前？ true 是的，false 不是
     *
     * @param d1 第一个时间
     * @param d2 第二个时间
     * @return true/false
     */
    public static boolean compare(DateTime d1, DateTime d2) {
        if (d1.isBefore(d2)) {
            return true;
        }
        return false;
    }

    /**
     * 根据周期计算两个时间的偏移量
     *
     * @param d1     第一个时间
     * @param d2     第二个时间
     * @param period 周期（年月日时分秒周）
     * @return
     */
    public static int calRange(DateTime d1, DateTime d2, String period) {
        int result = 0;
        if (DAY.equals(period)) {
            result = Days.daysBetween(d1, d2).getDays();
        } else if (MONTH.equals(period)) {
            result = Months.monthsBetween(d1, d2).getMonths();
        } else if (YEAR.equals(period)) {
            result = Years.yearsBetween(d1, d2).getYears();
        } else if (HOUR.equals(period)) {
            result = Hours.hoursBetween(d1, d2).getHours();
        } else if (MINUTE.equals(period)) {
            result = Minutes.minutesBetween(d1, d2).getMinutes();
        } else if (SECOND.equals(period)) {
            result = Seconds.secondsBetween(d1, d2).getSeconds();
        } else if (WEEK.equals(period)) {
            result = Weeks.weeksBetween(d1, d2).getWeeks();
        } else {
            return result;
        }
        return Math.abs(result);
    }

    /**
     * 获取指定日期的所在周期的最后日期或第一个日期（返回格式：yyyy-MM-dd HH:mm:ss）<br/>
     * 例如: 指定日期为：”2020-02-20“，周期为：”day“, isMax为true； 返回结果为：”2020-02-29“
     *
     * @param dateTime joda-time 指定日期
     * @param period   周期（年月日时周）
     * @param isMax    true：最大日期，false：最小日期
     * @return
     */
    public static DateTime getStartOrEnd(DateTime dateTime, String period, boolean isMax) {
        DateTime d = new DateTime();
        if (YEAR.equals(period)) {
            d = isMax ? dateTime.dayOfYear().withMaximumValue() : dateTime.dayOfYear().withMinimumValue();
        } else if (MONTH.equals(period)) {
            d = isMax ? dateTime.dayOfMonth().withMaximumValue() : dateTime.dayOfMonth().withMinimumValue();
        } else if (WEEK.equals(period)) {
            d = isMax ? dateTime.dayOfWeek().withMaximumValue() : dateTime.dayOfWeek().withMinimumValue();
        } else if (HOUR.equals(period)) {
            d = isMax ? dateTime.hourOfDay().withMaximumValue() : dateTime.hourOfDay().withMinimumValue();
        }
        d = isMax ? d.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
                : d.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        return d;
    }

    /**
     * 获取指定日期的所在周期的起止时间
     *
     * @param dateTime 日期（Joda-Time）
     * @param period   周期（年月周）
     * @return ”yyyy-MM-dd,yyyy-MM-dd“
     */
    public static String getStartAndEndByPeriod(DateTime dateTime, String period) {
        DateTime d = new DateTime();
        String start = "", end = "";
        if (YEAR.equals(period)) {
            start = dateTime.dayOfYear().withMinimumValue().toString(YMD);
            end = dateTime.dayOfYear().withMaximumValue().toString(YMD);
        } else if (MONTH.equals(period)) {
            start = dateTime.dayOfMonth().withMinimumValue().toString(YMD);
            end = dateTime.dayOfMonth().withMaximumValue().toString(YMD);
        } else if (WEEK.equals(period)) {
            start = dateTime.dayOfWeek().withMinimumValue().toString(YMD);
            end = dateTime.dayOfWeek().withMaximumValue().toString(YMD);
        } else {
            // TODO 季度
        }
        return start.concat(",").concat(end);
    }

    /***
     * String → Date（JDK）
     *
     * @param dateTimeStr 需要转化的string类型的字符串
     * @param pattern 转化规则
     * @return 返回转化后的Date类型的时间
     */
    public static Date strToDate(String dateTimeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * String → DateTime（JodaTime）
     *
     * @param dateTimeStr 日期字符串（格式最低要求：yyyy-MM-dd）
     * @param pattern     转化规则
     * @return
     */
    public static DateTime strToDateTime(String dateTimeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime;
    }

    /**
     * 计算：两个时间相距天数
     *
     * @param d1 时间1 DateTime:joda-time
     * @param d2 时间2 DateTime:joda-time
     * @return 偏移数
     */
    public static int getDays(DateTime d1, DateTime d2) {
        Period p = new Period(d1, d2, PeriodType.days());
        return Math.abs(p.getDays());
    }

    /**
     * 计算两时间相隔多少周期
     *
     * @param dateTimeStr1 (格式为: yyyy-MM-dd HH:mm:ss)
     * @param dateTimeStr2 (格式为: yyyy-MM-dd HH:mm:ss)
     * @param pattern      转化规则
     * @param period       周期（年月日周时分秒: year,month,day,week,hour,minute,second）
     * @return
     */
    public static int getPeriods(String dateTimeStr1, String dateTimeStr2, String pattern, String period) {
        int num = 0;
        if (dateTimeStr1 == null || dateTimeStr1.isBlank() || dateTimeStr2 == null || dateTimeStr2.isBlank()) {
            return num;
        }
        DateTime d1 = DateTimeFormat.forPattern(pattern).parseDateTime(dateTimeStr1);
        DateTime d2 = DateTimeFormat.forPattern(pattern).parseDateTime(dateTimeStr2);
        // 默认 计算相隔天数
        PeriodType periodType = PeriodType.days();
        if (DAY.equals(period)) {
            num = new Period(d1, d2, periodType).getDays();
        } else if (MONTH.equals(period)) {
            periodType = PeriodType.months();
            num = new Period(d1, d2, periodType).getMonths();
        } else if (WEEK.equals(period)) {
            periodType = PeriodType.weeks();
            num = new Period(d1, d2, periodType).getWeeks();
        } else if (YEAR.equals(period)) {
            periodType = PeriodType.years();
            num = new Period(d1, d2, periodType).getYears();
        } else if (HOUR.equals(period)) {
            periodType = PeriodType.hours();
            num = new Period(d1, d2, periodType).getHours();
        } else if (MINUTE.equals(period)) {
            periodType = PeriodType.minutes();
            num = new Period(d1, d2, periodType).getMinutes();
        } else if (SECOND.equals(period)) {
            periodType = PeriodType.seconds();
            num = new Period(d1, d2, periodType).getSeconds();
        }
        return Math.abs(num);
    }

    /**
     * 判断时间是否在指定区间内
     *
     * @param start       开始时间
     * @param end         结束时间
     * @param aimDateTime 被判断的时间
     * @return true：是，false：否
     */
    public static boolean isExist(DateTime start, DateTime end, String aimDateTime) {
        Interval interval = new Interval(start, end);
        boolean flag = interval.contains(new DateTime(strToDateTime(aimDateTime, YMDHMS)));
        return flag;
    }

    /**
     * 判断指定日期是否在当前时间之后
     */
    public static boolean isAfterNow(DateTime dateTime) {
        return dateTime.isAfterNow();
    }

    /**
     * 判断指定日期是否在当前时间之前
     */
    public static boolean isBeforeNow(DateTime dateTime) {
        return dateTime.isBeforeNow();
    }

    /**
     * 得到指定日期为星期几
     *
     * @param dateTime 指定日期（Joda-time）
     * @return 中文星期几
     */
    public static String getWeekDay(DateTime dateTime) {
        String result = null;
        if (DateTimeConstants.SUNDAY == dateTime.getDayOfWeek()) {
            result = "星期日";
        } else if (DateTimeConstants.SATURDAY == dateTime.getDayOfWeek()) {
            result = "星期六";
        } else if (DateTimeConstants.FRIDAY == dateTime.getDayOfWeek()) {
            result = "星期五";
        } else if (DateTimeConstants.THURSDAY == dateTime.getDayOfWeek()) {
            result = "星期四";
        } else if (DateTimeConstants.WEDNESDAY == dateTime.getDayOfWeek()) {
            result = "星期三";
        } else if (DateTimeConstants.TUESDAY == dateTime.getDayOfWeek()) {
            result = "星期二";
        } else if (DateTimeConstants.MONDAY == dateTime.getDayOfWeek()) {
            result = "星期一";
        }
        return result;
    }

    /**
     * JDK实现：判断指定日期所在的季度<br/>
     * <pre>
     * 1 第一季度 (一月,二月,三月)
     * 2 第二季度 (四月,五月,六月)
     * 3 第三季度 (七月,八月,九月)
     * 4 第四季度 (十月,十一月,十二月)
     * <pre/>
     * @param date 指定JDK的日期
     * @return 1：第一季度，2：第二季度，3：第三季度，4：第四季度 (如果返回0，请检查输入参数)
     */
    public static int getSeason(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH), season = 0;
        if (month == Calendar.JANUARY || month == Calendar.FEBRUARY || month == Calendar.MARCH) {
            season = 1;
        } else if (month == Calendar.APRIL || month == Calendar.MAY || month == Calendar.JUNE) {
            season = 2;
        } else if (month == Calendar.JULY || month == Calendar.AUGUST || month == Calendar.SEPTEMBER) {
            season = 3;
        } else if (month == Calendar.OCTOBER || month == Calendar.NOVEMBER || month == Calendar.DECEMBER) {
            season = 4;
        }
        return season;
    }

    /**
     * 取得季度月
     *
     * @param date 指定日期
     * @return 日期数组
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];
        if (date == null) {
            return season;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int nSeason = getSeason(date);
        if (nSeason == 1) {
            // 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {
            // 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {
            // 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {
            // 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 计算当指定日期距离所在日期的第几天<br/>
     * 例如：当前日期为 2020-04-01，四月一日是第二季度的一天，传入的日期为2020-04-02；则输出的结果为：1 （即：已经相隔一天了）
     *
     * @param datetime 指定日期
     * @return num==0 可能1.传入参数为null,2.传入的日期为当前季度所在的第一天
     */
    public static int calDuringSeasonDays(DateTime datetime) {
        if (datetime == null) {
            return 0;
        }
        int month = datetime.getMonthOfYear();
        DateTime startTime = null;
        if (month == 1 || month == 2 || month == 3) {
            startTime = datetime.withMonthOfYear(1).dayOfMonth().withMinimumValue();
        } else if (month == 4 || month == 5 || month == 6) {
            startTime = datetime.withMonthOfYear(4).dayOfMonth().withMinimumValue();
        } else if (month == 7 || month == 8 || month == 9) {
            startTime = datetime.withMonthOfYear(7).dayOfMonth().withMinimumValue();
        } else if (month == 10 || month == 11 || month == 12) {
            startTime = datetime.withMonthOfYear(10).dayOfMonth().withMinimumValue();
        }
        return Math.abs(Days.daysBetween(startTime, datetime).getDays());
    }

    public static void main(String[] args) {
        // JDK 得到当前时间
        Date date = new Date();
        // JodaTime 得到当前时间
        Date dateNow = DateTime.now().toDate();
        // 创建日期的方式一：（以此为：年月日时分秒毫秒）
        DateTime dateTime = new DateTime(2020, 4, 2, 0, 0, 0, 0);
        String dateTimeStr = "2021-01-02 20:20:20", dateTimeStr2 = "2020-02-12 23:20:20";
        DateTime nextFirstDay = new DateTime().dayOfYear().withMinimumValue();
        DateTime nextLastDay = new DateTime().dayOfYear().withMaximumValue();
        System.out.println(calDuringSeasonDays(dateTime));
//        System.out.println(Arrays.asList(getSeasonDate(dateNow)));
//        System.out.printf("当前日期所在的季度为：%s\n", getSeason(new Date()));
//        System.out.printf("JDK获取当前日期时间戳推荐System.currentTimeMillis：%s\n", System.currentTimeMillis());
//        System.out.println("jodaTime得到当前时间戳（建议）：" + DateTimeUtils.currentTimeMillis());
//        System.out.println("JDK得到当前时间戳1(建议)：" + System.currentTimeMillis());
//        System.out.println("JDK得到当前时间戳2：" + "new Date().getTime() 这种方式已经不推荐使用！");
//        System.out.println(getWeekDay(DateTime.now()));
//        System.out.println(getStartAndEndByPeriod(new DateTime(date), YEAR));
//        System.out.println(dateTime.toString());
//        System.out.println(isExist(nextFirstDay, nextLastDay, dateTimeStr));
//        System.out.println(getPeriods(dateTimeStr, dateTimeStr2, YMDHMS, MINUTE));
//        System.out.println(strToDate(dateTimeStr, YMDHMS));
//        System.out.println(strToDateTime(dateTimeStr, YMDHMS));
//        System.out.println(getDays(nextLastDay, nextFirstDay));
//        System.out.println(getStartOrEnd(new DateTime(date), YEAR, true).toString(YMDHMS));
//        System.out.println(calRange(nextLastDay, nextFirstDay, SECOND));
//        System.out.println(setCurrDateTime(220, 21, 33, 23, 59, 59).toString(YMDHMS));
//        System.out.println(new DateTime().getYear());
//        System.out.println(DateTime.now().toString(YMDHMS, Locale.CHINA));
//        System.out.println(getDateTime(new DateTime(date), DAY, -3));
//        System.out.println(getDateTime(dateTimeStr, YMDHMS));
//        System.out.println(getDateTimeStr(date, YMDHMS_CN));
//        System.out.println(getDateTimeStrLocale(date, YMD_CN));
//        System.out.println(new DateTime(date)); // 输出：2020-04-04T21:33:11.625+08:00
    }

}
