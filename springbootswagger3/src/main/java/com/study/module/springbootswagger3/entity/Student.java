package com.study.module.springbootswagger3.entity;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2020-10-09 09:31:25
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 795709769862015046L;
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


    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
