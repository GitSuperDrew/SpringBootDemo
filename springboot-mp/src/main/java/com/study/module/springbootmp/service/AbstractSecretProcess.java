package com.study.module.springbootmp.service;

import com.study.module.springbootmp.config.SecretConfig;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;

/**
 * @author zl
 * @date 2021/1/18 19:09
 **/
public abstract class AbstractSecretProcess implements SecretProcess {

    @Resource
    private SecretConfig.SecretProperties props;

    @Override
    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, keySpec());
            byte[] decryptBytes = cipher.doFinal(Hex.decode(data));
            return new String(decryptBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec());
            return Hex.encode(cipher.doFinal(data.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>根据密钥生成不同的密钥材料</p>
     * <p>目前支持：AES, DES</p>
     *
     * @param algorithm 算法
     * @return Key
     */
    public Key getKeySpec(String algorithm) {
        if (algorithm == null || algorithm.trim().length() == 0) {
            return null;
        }
        String secretKey = props.getKey();
        switch (algorithm.toUpperCase()) {
            case "AES":
                return new SecretKeySpec(secretKey.getBytes(), "AES");
            case "DES":
                Key key = null;
                try {
                    DESKeySpec desKeySpec = new DESKeySpec(secretKey.getBytes());
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                    key = secretKeyFactory.generateSecret(desKeySpec);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return key;
            default:
                return null;
        }
    }

    /**
     * <p>生成密钥材料</p>
     *
     * @return Key 密钥材料
     */
    public abstract Key keySpec();
}
