package com.study.module.springbootapilock.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * @author zl
 * @create 2022-06-11 10:01
 */
public class MD5Util {

    public static String getMD5Str(String string) {
        return string;
    }

    public static void main(String[] args) {
        String str1 = "123432";
        String str2 = "123432";

        //测试普通MD5算法
        if (md5HashCode(str1).equals(md5HashCode(str2))) {
            System.out.println("普通MD5算法:密码相同");
        } else {
            System.out.println("普通MD5算法:密码不同");
        }
        System.out.println("===================");
        //测试加盐后的算法
        System.out.println("加盐后密码1与原密码的校验结果：" + verify(str1, salt(str1)));
        System.out.println("加盐后密码2与原密码的校验结果：" + verify(str1, salt(str2)));
        System.out.println("密码1加密结果：" + salt(str1));
        System.out.println("密码2加密结果：" + salt(str2));

    }

    /**
     * 普通MD5加密算法，http://www.cmd5.com/，可反编译出来密码
     *
     * @param string 原始字符
     * @return hash code
     */
    public static String md5HashCode(String string) {
        StringBuffer buffer = new StringBuffer();
        try {
            //实例化对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            /**
             * digest方法是完成哈希计算并返回结果
             * digest方法只能被调用一次，当调用后，MessageDigest对象被重新设置成初始化状态
             */
            byte[] md5Bytes = messageDigest.digest(string.getBytes());
            for (byte b : md5Bytes) {
                int num = b & 0xff;
                //Integer.toHexString()此方法返回的字符串表示的无符号整数参数所表示的值以十六进制
                String str = Integer.toHexString(num);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * MD5加盐
     *
     * @param password 加密后的密码
     * @return 添加盐值的密码
     */
    public static String salt(String password) {
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            sb.append("0".repeat(Math.max(0, 16 - len)));
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验原密码与加密后的对象是否相同
     *
     * @param password 原密码
     * @param md5      加密后的字符串
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return Objects.equals(md5Hex(password + salt), new String(cs1));
    }
}
