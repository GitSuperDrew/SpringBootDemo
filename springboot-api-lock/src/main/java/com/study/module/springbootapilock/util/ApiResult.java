package com.study.module.springbootapilock.util;

import com.study.module.springbootapilock.enums.CommonCode;
import lombok.Builder;
import lombok.Data;

/**
 * @author zl
 * @create 2022-06-11 11:18
 */
@Data
@Builder
public class ApiResult {
    /**
     * 是否成功
     */
    private Boolean flag;
    /**
     * 返回结果码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 操作数据
     */
    private Object data;

    public static ApiResult success() {
        return ApiResult.builder().flag(true).code(200).message("操作成功！").build();
    }

    public static ApiResult fail() {
        return ApiResult.builder().flag(false).code(500).message("操作失败！").build();
    }

    public static ApiResult result(CommonCode code) {
        return ApiResult.builder().flag(code.getFlag()).code(code.getCode()).message(code.getMessage()).build();
    }

    public static ApiResult result(CommonCode code, String message) {
        return ApiResult.builder().flag(code.getFlag()).code(code.getCode()).message(message).build();
    }
}
