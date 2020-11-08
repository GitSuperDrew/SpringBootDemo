package com.study.module.springbootmybatis.enums;

import lombok.Getter;

/**
 * @author Administrator
 * @date 2020/11/8 下午 6:09
 */
@Getter
public enum ResultEnum {
    /**
     * 未找到
     */
    NO_FIND(404, "未找到", false),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(100, "未知异常", false),
    /**
     * 添加失败
     */
    ADD_ERROR(103, "添加失败", false),
    /**
     * 更新失败
     */
    UPDATE_ERROR(104, "更新失败", false),
    /**
     * 删除失败
     */
    DELETE_ERROR(105, "删除失败", false),
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 结果纤细
     */
    private String message;
    /**
     * 成功与否（true成功，false失败）
     */
    private boolean success = true;

    ResultEnum(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    /**
     * 通过状态码获取枚举对象
     *
     * @param code 状态码
     * @return 枚举对象
     */
    public static ResultEnum getByCode(int code) {
        for (ResultEnum result : ResultEnum.values()) {
            if (result.getCode() == code) {
                return result;
            }
        }
        return null;
    }
}
