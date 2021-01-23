package edu.study.module.springbootsecurity.entity;

import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author drew
 * @since 2021-01-23 11:12:54
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -75743931924954530L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 角色ID
     */
    private Integer rid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

}