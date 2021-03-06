/*
 * This file is generated by jOOQ.
 */
package com.study.module.jooq.tables.pojos;


import java.io.Serializable;


/**
 * 用户表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = -215480964;

    private Long    id;
    private String  name;
    private Integer age;
    private String  email;
    private Integer removeTag;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.name = value.name;
        this.age = value.age;
        this.email = value.email;
        this.removeTag = value.removeTag;
    }

    public User(
        Long    id,
        String  name,
        Integer age,
        String  email,
        Integer removeTag
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.removeTag = removeTag;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRemoveTag() {
        return this.removeTag;
    }

    public void setRemoveTag(Integer removeTag) {
        this.removeTag = removeTag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(age);
        sb.append(", ").append(email);
        sb.append(", ").append(removeTag);

        sb.append(")");
        return sb.toString();
    }
}
