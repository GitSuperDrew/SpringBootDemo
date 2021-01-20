package com.study.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录(SysOperLog)实体类
 *
 * @author drew
 * @since 2021-01-19 18:37:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 131511269239068345L;
    /**
     * 日志主键
     */
    private Long id;
    /**
     * 模块标题
     */
    private String title;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 方法耗时
     */
    private Long spellTime;
    /**
     * 方法入参
     */
    private String params;
    /**
     * 方法返回值
     */
    private String responseData;
    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:dd:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:dd:ss")
    private Date operTime;

}