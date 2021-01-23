package edu.study.module.springbootsecurity.entity;

import java.io.Serializable;

/**
 * (SysPermission)实体类
 *
 * @author drew
 * @since 2021-01-23 11:09:20
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -52478081988129194L;
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String permName;
    /**
     * 权限标识(//权限标志符，authorities集合的值)
     */
    private String permTag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermTag() {
        return permTag;
    }

    public void setPermTag(String permTag) {
        this.permTag = permTag;
    }

}