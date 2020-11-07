package com.study.module.springbootmybatis.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师表(Teacher)实体类
 *
 * @author makejava
 * @since 2020-11-07 19:04:38
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = 400805595020859927L;
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
    private Integer sex;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelTag() {
        return delTag;
    }

    public void setDelTag(Integer delTag) {
        this.delTag = delTag;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
