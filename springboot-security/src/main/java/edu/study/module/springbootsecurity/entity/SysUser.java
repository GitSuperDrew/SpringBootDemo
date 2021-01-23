package edu.study.module.springbootsecurity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.study.module.springbootsecurity.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (SysUser)实体类
 *
 * @author drew
 * @since 2021-01-23 11:09:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUser implements UserDetails, Serializable {
    private static final long serialVersionUID = 309934130618892203L;
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATETIME)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.DATETIME)
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATETIME)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.DATETIME)
    private Date updateTime;
    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATETIME)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.DATETIME)
    private Date lastLoginTime;
    /**
     * 是否有效用户(1代表true，0代表false，默认为1)
     */
    private boolean enabled;
    /**
     * 账户是否过期
     */
    private boolean accountNonExpired;
    /**
     * 账户是否锁定
     */
    private boolean accountNonLocked;
    /**
     * 权限是否过期
     */
    private boolean credentialsNonExpired;
    /**
     * 权限集合
     */
    private List<GrantedAuthority> authorities;
}