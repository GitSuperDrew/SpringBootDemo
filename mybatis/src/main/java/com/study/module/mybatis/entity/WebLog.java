package com.study.module.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统日志表(WebLog)实体类
 *
 * @author makejava
 * @since 2020-09-21 21:34:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebLog implements Serializable {
    private static final long serialVersionUID = 929394533472742687L;
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 请求状态码（例如：200,500,404等）
     */
    private Integer code;
    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 操作时间
     */
    private Long startTime;
    /**
     * 消耗时间（毫秒）
     */
    private Integer spendTime;
    /**
     * 根路径
     */
    private String basePath;
    /**
     * URI
     */
    private String uri;
    /**
     * URL
     */
    private String url;
    /**
     * 请求类型
     */
    private String method;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private String parameter;
    /**
     * 请求返回的结果
     */
    private String result;

}
