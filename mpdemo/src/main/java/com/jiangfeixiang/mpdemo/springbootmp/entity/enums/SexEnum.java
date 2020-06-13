package com.jiangfeixiang.mpdemo.springbootmp.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Administrator
 * @date 2020/6/13 下午 2:32
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexEnum implements IEnum<String> {
    other("0", "其他"),
    male("1", "男"),
    female("2", "女");

    SexEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue // 标记响应的JSON值
    private String value;
    private String desc;

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return "SexEnum{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
