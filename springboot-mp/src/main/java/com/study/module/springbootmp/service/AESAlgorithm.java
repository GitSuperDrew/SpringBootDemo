package com.study.module.springbootmp.service;

import java.security.Key;

/**
 * @author zl
 * @date 2021/1/18 19:10
 **/
public class AESAlgorithm extends AbstractSecretProcess {
    @Override
    public String getAlgorithm() {
        return "AES/ECB/PKCS5Padding";
    }

    @Override
    public Key keySpec() {
        return this.getKeySpec("AES");
    }
}
