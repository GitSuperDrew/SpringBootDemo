package com.study.module.mapstruct.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2020-07-26 08:53:12
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 665444367804476552L;
    /**
    * 学生ID，主键
    */
    private Integer stuId;
    /**
    * 学生姓名
    */
    private String stuName;
    /**
    * 学生年龄
    */
    private Integer stuAge;
    /**
    * 学生性别（0：未知，1：男，2：女）
    */
    private String stuSex;
    /**
    * 逻辑删除标识（0：有效数据，1：无效数据）
    */
    private Integer deleted;

}