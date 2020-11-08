package com.study.module.springbootmybatis.enums;

import lombok.Getter;

/**
 * @author Administrator
 * @date 2020/11/7 下午 8:00
 */
@Getter
public enum SexEnum {
    /**
     * 其他
     */
    OTHER(0, "其他"),
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女");

    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    SexEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SexEnum getEnumById(int id) {
        for (SexEnum sex : SexEnum.values()) {
            if (sex.getId() == id) {
                return sex;
            }
        }
        return null;
    }
}
