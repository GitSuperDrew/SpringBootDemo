package edu.study.module.springbootsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (SysPermission)实体类
 *
 * @author drew
 * @since 2021-01-23 11:09:20
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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

}