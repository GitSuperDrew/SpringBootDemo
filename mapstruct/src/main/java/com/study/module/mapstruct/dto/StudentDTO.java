package com.study.module.mapstruct.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Administrator
 * @date 2020/7/26 上午 9:42
 */
@Data
@Builder
public class StudentDTO {
    /**
     * 学生编号
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生年龄
     */
    private Integer age;
    /**
     * 学生性别
     */
    private String sex;
}
