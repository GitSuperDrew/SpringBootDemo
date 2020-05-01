package com.study.module.mybatis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户表（用来测试代码生成插件EasyCode）(User)实体类
 *
 * @author makejava
 * @since 2020-04-11 22:45:37
 * <pre>
 *  `@ApiModel`           参数Model对应的接口文档<br/>
 *  `@ApiModelProperty`   参数Model对应属性的接口文档描述
 * <pre/>
 */
@ApiModel(value = "用户信息")
public class User implements Serializable {
    private static final long serialVersionUID = -48812651257143828L;
    /**
     * 用户主键ID
     */
    @ApiModelProperty("用户ID")
    private Integer id;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String name;
    /**
     * 用户密码
     */
    @ApiModelProperty("用户登录密码")
    private String password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}