package com.study.module.mybatis.vo;

import com.study.module.mybatis.aop.EncryptField;
import lombok.Data;

/**
 * 测试加密入参的VO
 *
 * @author Administrator
 * @date 2020/8/23 下午 6:09
 */
@Data
public class EncryptRequest {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 银行卡号
     */
    @EncryptField
    private String bankCardNo;
    /**
     * 身份证号
     */
    @EncryptField
    private String idCard;

    @Override
    public String toString() {
        if (this == null) {
            return null;
        }
        return String.format("{\n" +
                " \"name\": \"%s\",\n" +
                " \"sex\": \"%s\",\n" +
                " \"bankCardNo\": \"%s\",\n" +
                " \"idCard\": \"%s\"\n" +
                "}", this.name, this.sex, this.bankCardNo, this.idCard);
    }

}
