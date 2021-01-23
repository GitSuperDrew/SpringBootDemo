package edu.study.module.springbootsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (SysRolePermission)实体类
 *
 * @author drew
 * @since 2021-01-23 11:12:55
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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

}