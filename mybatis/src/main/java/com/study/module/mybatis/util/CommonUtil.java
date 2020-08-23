package com.study.module.mybatis.util;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2020/8/23 下午 9:24
 */
public class CommonUtil {

    private static SecureRandom random = new SecureRandom();

    public static final Pattern MAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

    public static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");

    public static final Pattern NAME_PATTERN = Pattern.compile("^[\\u4E00-\\u9FBF][\\u4E00-\\u9FBF(.|·)]{0,13}[\\u4E00-\\u9FBF]$");

    public static final Pattern NICKNAME_PATTERN = Pattern.compile("^((?!\\d{5})[\\u4E00-\\u9FBF(.|·)|0-9A-Za-z_]){2,11}$");

    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[\\s\\S]{6,30}$");

    public static final Pattern CODE_PATTERN = Pattern.compile("^0\\d{2,4}$");

    public static final Pattern POSTCODE_PATTERN = Pattern.compile("^\\d{6}$");

    public static final Pattern ID_PATTERN = Pattern.compile("^\\d{6}(\\d{8}|\\d{11})[0-9a-zA-Z]$");

    public static final Pattern BANK_CARD_PATTERN = Pattern.compile("^\\d{16,30}$");

    /**
     * 生成6位随机数字, 用于手机短信验证码.
     *
     * @return 6位随机数字
     */
    public static int random() {
        int x = Math.abs(random.nextInt(899999));

        return x + 100000;
    }

    /**
     * 对url字符串进行编码.
     *
     * @param url 要编码的url字符串
     * @return 编码后的字符串
     */
    public static String urlEncoder(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        try {
            return java.net.URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对url字符串进行解码.
     *
     * @param url 要解码的url字符串
     * @return 解码后的字符串
     */
    public static String urlDecoder(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证字符串是不是邮箱.
     *
     * @param email 要验证的邮箱
     * @return 是否正确邮箱
     */
    public static boolean validateEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        Matcher m = MAIL_PATTERN.matcher(email);
        return m.matches();
    }

    /**
     * 验证字符串是不是手机号.
     *
     * @param mobile 要验证的手机号
     * @return 是否正确手机号
     */
    public static boolean validateMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 验证姓名是否有效.
     *
     * @param name 要验证的姓名
     * @return 是否正确姓名
     */
    public static boolean validateName(String name) {
        if (StringUtils.isEmpty(name) || name.replaceAll("[^.·]", "").length() > 1) {
            return false;
        }
        Matcher m = NAME_PATTERN.matcher(name);
        return m.matches();
    }

    /**
     * 验证昵称是否有效.
     *
     * @param nickname 要验证的昵称
     * @return 是否正确昵称
     */
    public static boolean validateNickname(String nickname) {

        //规则 不能包含5个数字 允许中英文和数字 2-11位
        if (StringUtils.isEmpty(nickname) || nickname.replaceAll("[^0-9]", "").length() > 4) {
            return false;
        }
        Matcher m = NICKNAME_PATTERN.matcher(nickname);
        return m.matches();
    }

    /**
     * 验证密码格式是否有效.
     *
     * @param password 要验证的密码
     * @return 是否正确密码格式
     */
    public static boolean validatePassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        Matcher m = PASSWORD_PATTERN.matcher(password);
        return m.matches();
    }

    /**
     * 验证区号是否有效.
     *
     * @param code 要验证的区号
     * @return 是否正确身份证
     */
    public static boolean validateCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        Matcher m = CODE_PATTERN.matcher(code);
        return m.matches();
    }

    /**
     * 验证邮政编码是否有效.
     *
     * @param postcode 要验证的邮政编码
     * @return 是否正确邮政编码
     */
    public static boolean validatePostcode(String postcode) {
        if (StringUtils.isEmpty(postcode)) {
            return false;
        }
        Matcher m = POSTCODE_PATTERN.matcher(postcode);
        return m.matches();
    }

    /**
     * 验证银行卡是否有效.
     *
     * @param bankCardNumber 要验证的银行卡号
     * @return 是否正确银行卡号
     */
    public static boolean validateBankCardNumber(String bankCardNumber) {
        if (StringUtils.isEmpty(bankCardNumber)) {
            return false;
        }
        Matcher m = BANK_CARD_PATTERN.matcher(bankCardNumber);
        return m.matches();
    }

    /**
     * 获取客户端IP地址.
     *
     * @param request request请求
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.hasText(ip)) {
            return StringUtils.tokenizeToStringArray(ip, ",")[0];
        }
        return "";
    }

    /**
     * 获取当前系统时间,以java.sql.Timestamp类型返回.
     *
     * @return 当前时间
     */
    public static Timestamp getTimestamp() {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        return d;
    }

    /**
     * 生成32位编码,不含横线
     *
     * @return uuid串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid.toUpperCase();
    }

    /**
     * 生成MD5编码
     *
     * @param data 要编码的字符串
     * @return 加密后的字符串
     */
    public static String md5(String data) {
        return md5(data, 1);
    }


    /**
     * 生成MD5编码
     *
     * @param data 要编码的字符串
     * @param time 加密次数
     * @return 加密后的字符串
     */
    public static String md5(String data, int time) {
        byte[] bytes = data == null ? new byte[0] : data.getBytes();

        while (time-- > 1) {
            bytes = DigestUtils.md5Digest(bytes);
        }

        return DigestUtils.md5DigestAsHex(bytes).toUpperCase();
    }


    /**
     * 通过身份证获取性别
     *
     * @param idNumber 身份证号
     * @return 返回性别, 0 保密 , 1 男 2 女
     */
    public static Integer getGenderByIdNumber(String idNumber) {

        int gender = 0;

        if (idNumber.length() == 15) {
            gender = Integer.parseInt(String.valueOf(idNumber.charAt(14))) % 2 == 0 ? 2 : 1;
        } else if (idNumber.length() == 18) {
            gender = Integer.parseInt(String.valueOf(idNumber.charAt(16))) % 2 == 0 ? 2 : 1;
        }

        return gender;

    }

    /**
     * 通过身份证获取生日
     *
     * @param idNumber 身份证号
     * @return 返回生日, 格式为 yyyy-MM-dd 的字符串
     */
    public static String getBirthdayByIdNumber(String idNumber) {

        String birthday = "";

        if (idNumber.length() == 15) {
            birthday = "19" + idNumber.substring(6, 8) + "-" + idNumber.substring(8, 10) + "-" + idNumber.substring(10, 12);
        } else if (idNumber.length() == 18) {
            birthday = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-" + idNumber.substring(12, 14);
        }

        return birthday;

    }


    /**
     * 通过身份证获取年龄
     *
     * @param idNumber 身份证号
     * @return 返回年龄
     */
    public static Integer getAgeByIdNumber(String idNumber) {

        String birthString = getBirthdayByIdNumber(idNumber);
        if (StringUtils.isEmpty(birthString)) {
            return 0;
        }

        return getAgeByBirthString(birthString);

    }

    /**
     * 通过身份证获取年龄
     *
     * @param idNumber     身份证号
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByIdNumber(String idNumber, boolean isNominalAge) {

        String birthString = getBirthdayByIdNumber(idNumber);
        if (StringUtils.isEmpty(birthString)) {
            return 0;
        }

        return getAgeByBirthString(birthString, isNominalAge);

    }

    /**
     * 通过生日日期获取年龄
     *
     * @param birthDate 生日日期
     * @return 返回年龄
     */
    public static Integer getAgeByBirthDate(Date birthDate) {

        return getAgeByBirthString(new SimpleDateFormat("yyyy-MM-dd").format(birthDate));

    }


    /**
     * 通过生日字符串获取年龄
     *
     * @param birthString 生日字符串
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString) {

        return getAgeByBirthString(birthString, "yyyy-MM-dd");

    }

    /**
     * 通过生日字符串获取年龄
     *
     * @param birthString  生日字符串
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, boolean isNominalAge) {

        return getAgeByBirthString(birthString, "yyyy-MM-dd", isNominalAge);

    }

    /**
     * 通过生日字符串获取年龄
     *
     * @param birthString 生日字符串
     * @param format      日期字符串格式,为空则默认"yyyy-MM-dd"
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, String format) {
        return getAgeByBirthString(birthString, "yyyy-MM-dd", false);
    }


    /**
     * 通过生日字符串获取年龄
     *
     * @param birthString  生日字符串
     * @param format       日期字符串格式,为空则默认"yyyy-MM-dd"
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, String format, boolean isNominalAge) {

        int age = 0;

        if (StringUtils.isEmpty(birthString)) {
            return age;
        }

        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }

        try {

            Calendar birthday = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            birthday.setTime(sdf.parse(birthString));
            age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
            if (!isNominalAge) {
                if (today.get(Calendar.MONTH) < birthday.get(Calendar.MONTH) ||
                        (today.get(Calendar.MONTH) == birthday.get(Calendar.MONTH) &&
                                today.get(Calendar.DAY_OF_MONTH) < birthday.get(Calendar.DAY_OF_MONTH))) {
                    age = age - 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return age;

    }

    /**
     * 手机号中间四位替换成星号
     *
     * @param mobile
     * @return
     */
    public static String maskMobile(String mobile) {
        if (validateMobile(mobile)) {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return mobile;
    }

    /**
     * 手机号中间四位自定义替换
     *
     * @param mobile
     * @param transCode 中间四位目标值 如GXJF 将136GXJF1111
     * @return
     */
    public static String maskMobile(String mobile, String transCode) {
        if (validateMobile(mobile)) {
            transCode = StringUtils.isEmpty(transCode) ? "****" : transCode;
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", String.format("$1%s$2", transCode));
        }
        return mobile;
    }

    /**
     * 邮箱地址加星号
     *
     * @param email
     * @return
     */
    public static String maskEmail(String email) {
        if (validateEmail(email)) {
            String userName = email.substring(0, email.indexOf("@"));
            int len = userName.length();
            if (len >= 5) {
                int total = len - 3;
                int half = total / 2;
                int start = half;
                int end = len - half;
                if (total % 2 != 0) {
                    end = end - 1;
                }
                StringBuilder sb = new StringBuilder(email);
                for (int i = start; i < end; i++) {
                    sb.setCharAt(i, '*');
                }
                return sb.toString();
            }
        }
        return email;
    }

    /**
     * 账号中间四位自定义替换
     *
     * @param account
     * @return
     */
    public static String maskTradeAccount(String account) {
        return account.replaceAll("(\\d{7})\\d*(\\d{4})", "$1****$2");
    }

    /**
     * 验证是否为日期
     *
     * @param date
     * @return
     */
    public static boolean validateDate(String date) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取时间戳,作为递增的ID
     */
    private static final Lock lock = new ReentrantLock();   //锁对象

    public static long getUniqueLong() {
        long l;
        lock.lock();
        try {
            l = System.currentTimeMillis();
        } finally {
            lock.unlock();
        }
        return l;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param url url地址
     * @return url请求参数部分
     */
    public static String getUrlParams(String url, String key) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = null;
        java.net.URL aURL = null;
        try {
            aURL = new URL(url);
            strUrlParam = aURL.getQuery();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (strUrlParam == null) {
            return "";
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!StringUtils.isEmpty(arrSplitEqual[0])) {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        if (mapRequest.containsKey(key)) {
            try {
                return URLDecoder.decode(mapRequest.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        }
        return "";
    }


    /**
     * 生成随机密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

}
