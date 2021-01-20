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
 * (Stu)实体类
 *
 * @author drew
 * @since 2021-01-04 16:26:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Stu implements Serializable {
    private static final long serialVersionUID = -13651785347445977L;
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 乐观锁
     */
    private Long version;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:dd:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:dd:ss")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:dd:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:dd:ss")
    private Date updateTime;


}