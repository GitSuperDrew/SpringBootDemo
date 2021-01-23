package edu.study.module.springbootsecurity.entity;

import java.io.Serializable;

/**
 * (SysRolePermission)实体类
 *
 * @author drew
 * @since 2021-01-23 11:12:55
 */
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 216768168180083751L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 角色ID
     */
    private Integer rid;
    /**
     * 权限ID
     */
    private Integer pid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

}