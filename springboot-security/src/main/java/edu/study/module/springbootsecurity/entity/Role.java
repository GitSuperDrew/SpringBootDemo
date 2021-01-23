package edu.study.module.springbootsecurity.entity;

import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author drew
 * @since 2021-01-23 11:09:26
 */
public class Role implements Serializable {
    private static final long serialVersionUID = -63688410872508213L;
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

}