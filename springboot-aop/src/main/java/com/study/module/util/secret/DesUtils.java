package com.study.module.util.secret;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Java 常用对称加密的加密和解密算法之 DES算法。
 * DES 加密解密算法
 * des 是一组分组密码，以64位为分组对数据加密，他的密钥长度是 56位，加密解密用同一算法。DES加密算法是对密钥进行保密；
 * 而公开算法，包括加密和解密算法。这样，只有掌握了和发送方相同密钥的人才能解读有 DES 加密算法的密文数据。因此，破译 DES加密算法
 * 实际上就是 搜索密钥的编码。对于 56 位长度的密钥来说，如果用穷举法来进行搜索的话，其运算次数位 2的56次方。
 *
 * @author drew
 * @date 2021/1/27 14:17
 **/
public class DesUtils {
    private final static String DES = "DES";

    public static void main(String[] args) throws Exception {
        String data = "123 456";
        String key = "wang!@#$";
        System.err.println("加密的数据：" + encrypt(data, key));
        System.err.println("原始的数据：" + decrypt(encrypt(data, key), key));

    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 原始数据
     * @param key  加密键byte数组
     * @return 密文
     * @throws Exception 异常父类
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        return new BASE64Encoder().encode(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 原始数据（明文）
     * @throws IOException 输入输出异常
     * @throws Exception   异常父类
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     * @throws Exception 异常
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     * @throws Exception 异常
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
