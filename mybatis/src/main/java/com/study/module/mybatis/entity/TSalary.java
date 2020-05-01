package com.study.module.mybatis.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 薪资表(TSalary)实体类
 *
 * @author makejava
 * @since 2020-04-25 22:08:25
 */
public class TSalary implements Serializable {
    private static final long serialVersionUID = 170350254676844836L;
    
    private Integer id;
    /**
    * 员工姓名
    */
    private String name;
    /**
    * 职位
    */
    private String position;
    /**
    * 办公地点
    */
    private String office;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 入职日期

    */
    private Date startDate;
    /**
    * 薪水
    */
    private Integer salary;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

}