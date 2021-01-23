package edu.study.module.springbootsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author drew
 * @since 2021-01-23 11:12:54
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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

}