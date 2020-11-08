package com.study.module.springbootmybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.module.springbootmybatis.enums.SexEnum;
import com.study.module.springbootmybatis.constant.CommonConstant;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "教师名称超过限制长度32位字符")
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
    @Pattern(regexp = CommonConstant.REG_IDENTITY, message = "身份证号码不合法")
    private String idCard;
    /**
     * 手机号码
     */
    @Pattern(regexp = CommonConstant.REG_PHONE_STRICT, message = "手机号不合法")
    private String phone;
    /**
     * 座机号码
     */
    @Pattern(regexp = CommonConstant.REG_MOBILE, message = "联系电话号码不合法")
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
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_1024, message = "备注内容超过最大字符1024的限制")
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
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "创建者名称超过最大字符32的限制")
    private String createdBy;
    /**
     * 创建日期时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_EN)
    @JsonFormat(pattern = CommonConstant.DATE_TIME_EN, timezone = "GTM+8")
    private Date createdTime;
    /**
     * 更新记录者
     */
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "更新者名称超过最大字符32的限制")
    private String updatedBy;
    /**
     * 更新日期时间
     */
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_EN)
    @JsonFormat(pattern = CommonConstant.DATE_TIME_EN, timezone = "GTM+8")
    private Date updatedTime;
}
