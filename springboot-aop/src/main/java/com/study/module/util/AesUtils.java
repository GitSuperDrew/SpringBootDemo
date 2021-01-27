package com.study.module.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加密算法
 * AES 是密码学中的高级加密标准，该加密算法采用对称分组密码体制，密钥长度的最少支持位128，192，256，分组长度为 128 位，
 * 算法应易于各种硬件和软件实现。这种加密算法是美国联邦政府采用的区块加密标准，AES 标准用来替代原先的DES，已经被多方分析且
 * 广为全世界使用。
 *
 * @author drew
 * @date 2021/1/27 14:22
 **/
public class AesUtils {

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "hello world!";
        System.out.println(cSrc);
        // 加密
        String enString = encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String deString = decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + deString);
    }

    /**
     * 加密
     *
     * @param sSrc 原始数据
     * @param sKey 密钥
     * @return 加密后的数据
     * @throws Exception 异常
     */
    public static String encrypt(String sSrc, @NotBlank String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));

        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 解密
     *
     * @param sSrc 密文
     * @param sKey 密钥
     * @return 原始数据
     */
    public static String decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
