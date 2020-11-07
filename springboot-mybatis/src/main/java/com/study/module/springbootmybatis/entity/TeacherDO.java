package com.study.module.springbootmybatis.entity;

import com.study.module.springbootmybatis.SexEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author Administrator
 * @date 2020/11/7 下午 8:15
 */
@Data
@Alias(value = "teacherDO") // mybatis 指定的别名
public class TeacherDO {

    /**
     * 唯一标识，主键ID
     */
    private Long id;
    /**
     * 教师名称
     */
    private String name;
    /**
     * 教师年龄
     */
    private Integer age;
    /**
     * 教师性别
     */
    private SexEnum sex = null;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 座机号码
     */
    private String mobile;
    /**
     * 现住址
     */
    private String address;
    /**
     * 入职日期
     */
    private Long entryDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 逻辑删除标识：0有效数据，1无效数据
     */
    private Integer delTag;
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 创建记录者
     */
    private String createdBy;
    /**
     * 创建日期时间
     */
    private Date createdTime;
    /**
     * 更新记录者
     */
    private String updatedBy;
    /**
     * 更新日期时间
     */
    private Date updatedTime;
}
