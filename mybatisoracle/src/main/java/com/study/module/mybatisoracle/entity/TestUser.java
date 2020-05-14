package com.study.module.mybatisoracle.entity;

import java.io.Serializable;

/**
 * (TestUser)实体类
 *
 * @author makejava
 * @since 2020-05-11 12:02:15
 */
public class TestUser implements Serializable {
    private static final long serialVersionUID = -85584612772481156L;
    
    private Double id;
    
    private String name;
    
    private String phone;


    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}