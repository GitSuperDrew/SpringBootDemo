package com.study.module.springbootmybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.module.springbootmybatis.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * VO 教师展示层对象
 *
 * @author Administrator
 * @date 2020/11/8 上午 9:29
 */
@ApiModel(value = "教师VO")
@Data
public class TeacherVO {
    /**
     * 唯一标识，主键ID
     */
    @ApiModelProperty(value = "唯一标识")
    private Long id;
    /**
     * 教师名称
     */
    @ApiModelProperty(value = "教师名称")
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "教师名称超过限制长度32位字符")
    private String name;
    /**
     * 教师年龄
     */
    @ApiModelProperty(value = "教师年龄")
    private Integer age;
    /**
     * 教师性别
     */
    @ApiModelProperty(value = "教师性别")
    private String sex;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @Pattern(regexp = CommonConstant.REG_IDENTITY, message = "身份证号码不合法")
    private String idCard;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = CommonConstant.REG_PHONE_STRICT, message = "手机号不合法")
    private String phone;
    /**
     * 座机号码
     */
    @ApiModelProperty(value = "座机号码")
    @Pattern(regexp = CommonConstant.REG_MOBILE, message = "联系电话号码不合法")
    private String mobile;
    /**
     * 现住址
     */
    @ApiModelProperty(value = "现住址")
    private String address;
    /**
     * 入职日期
     */
    @ApiModelProperty(value = "入职日期")
    private Long entryDate;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_1024, message = "备注内容超过最大字符1024的限制")
    private String remark;
    /**
     * 创建记录者
     */
    @ApiModelProperty(value = "创建记录者")
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "创建者名称超过最大字符32的限制")
    private String createdBy;
    /**
     * 创建日期时间
     */
    @ApiModelProperty(value = "创建日期时间")
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_EN)
    @JsonFormat(pattern = CommonConstant.DATE_TIME_EN, timezone = "GTM+8")
    private Date createdTime;
    /**
     * 更新记录者
     */
    @ApiModelProperty(value = "更新记录者")
    @Pattern(regexp = CommonConstant.REG_MAX_LENGTH_32, message = "更新者名称超过最大字符32的限制")
    private String updatedBy;
    /**
     * 更新日期时间
     */
    @ApiModelProperty(value = "更新日期时间")
    @DateTimeFormat(pattern = CommonConstant.DATE_TIME_EN)
    @JsonFormat(pattern = CommonConstant.DATE_TIME_EN, timezone = "GTM+8")
    private Date updatedTime;
}
