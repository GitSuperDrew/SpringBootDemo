package com.study.module.springbooteasyexcel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 固定资产分类代码（国标 GB/T 14885-2010）(AssetsType)实体类
 *
 * @author makejava
 * @since 2020-10-13 11:00:51
 */
@Data
public class AssetsType implements Serializable {
    private static final long serialVersionUID = -23974336734018535L;
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 固定资产国标代码
     */
    private String assetsCode;
    /**
     * 固定资产国标代码名称
     */
    private String assetsName;
    /**
     * 上级资产国际代码
     */
    private String parentCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;

}
