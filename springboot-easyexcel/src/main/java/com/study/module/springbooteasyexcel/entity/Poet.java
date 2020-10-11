package com.study.module.springbooteasyexcel.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Poet)实体类
 *
 * @author makejava
 * @since 2020-10-11 14:17:32
 */
@Data
public class Poet implements Serializable {
    private static final long serialVersionUID = -53063569651471872L;
    /**
     * 唯一标识（主键）
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 联系电话（手机号/座机）
     */
    private String mobile;
    /**
     * 性别（男/女/其他）
     */
    private String sex;

}
