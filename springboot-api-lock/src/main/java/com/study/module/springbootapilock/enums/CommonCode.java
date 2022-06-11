package com.study.module.springbootapilock.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author zl
 * @create 2022-06-11 10:54
 */
public enum CommonCode {
    /**
     * 成功
     */
    SUCCESS(true, 200, "操作成功!"),
    /**
     * 失败
     */
    FAIL(false, 500, "操作失败！"),
    /**
     * 未找到相关内容
     */
    NO_FIND(false, 500, "未找到相关内容！"),
    ;
    /**
     * 操作是否成功
     */
    private Boolean flag;
    /**
     * 操作代码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;

    CommonCode(Boolean flag, Integer code, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public static String getMsgByCode(Integer code) {
        if (Objects.isNull(code)) {
            return "";
        }
        CommonCode[] arr = CommonCode.values();
        return Arrays.stream(arr).filter(commonCode -> code.equals(commonCode.getCode())).findFirst().map(CommonCode::getMessage).orElse("");
    }

    public static CommonCode getByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        CommonCode[] arr = CommonCode.values();
        return Arrays.stream(arr).filter(commonCode -> commonCode.getCode().equals(code)).findFirst().orElse(null);
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
