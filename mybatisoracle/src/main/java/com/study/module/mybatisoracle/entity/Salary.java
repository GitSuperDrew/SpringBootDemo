package com.study.module.mybatisoracle.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 审批流程测试表(Salary)实体类
 *
 * @author makejava
 * @since 2020-05-10 10:12:22
 */
public class Salary implements Serializable {
    private static final long serialVersionUID = -11008172794921765L;
    /**
     * 编号
     */
    private String id;
    /**
     * 流程实例ID
     */
    private String procInsId;
    /**
     * 变动用户
     */
    private String userId;
    /**
     * 归属部门
     */
    private String officeId;
    /**
     * 岗位
     */
    private String post;
    /**
     * 性别
     */
    private String age;
    /**
     * 学历
     */
    private String edu;
    /**
     * 调整原因
     */
    private String content;
    /**
     * 现行标准 薪酬档级
     */
    private String olda;
    /**
     * 现行标准 月工资额
     */
    private String oldb;
    /**
     * 现行标准 年薪总额
     */
    private String oldc;
    /**
     * 调整后标准 薪酬档级
     */
    private String newa;
    /**
     * 调整后标准 月工资额
     */
    private String newb;
    /**
     * 调整后标准 年薪总额
     */
    private String newc;
    /**
     * 月增资
     */
    private String addNum;
    /**
     * 执行时间
     */
    private String exeDate;
    /**
     * 人力资源部门意见
     */
    private String hrText;
    /**
     * 分管领导意见
     */
    private String leadText;
    /**
     * 集团主要领导意见
     */
    private String mainLeadText;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 删除标记
     */
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOlda() {
        return olda;
    }

    public void setOlda(String olda) {
        this.olda = olda;
    }

    public String getOldb() {
        return oldb;
    }

    public void setOldb(String oldb) {
        this.oldb = oldb;
    }

    public String getOldc() {
        return oldc;
    }

    public void setOldc(String oldc) {
        this.oldc = oldc;
    }

    public String getNewa() {
        return newa;
    }

    public void setNewa(String newa) {
        this.newa = newa;
    }

    public String getNewb() {
        return newb;
    }

    public void setNewb(String newb) {
        this.newb = newb;
    }

    public String getNewc() {
        return newc;
    }

    public void setNewc(String newc) {
        this.newc = newc;
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getHrText() {
        return hrText;
    }

    public void setHrText(String hrText) {
        this.hrText = hrText;
    }

    public String getLeadText() {
        return leadText;
    }

    public void setLeadText(String leadText) {
        this.leadText = leadText;
    }

    public String getMainLeadText() {
        return mainLeadText;
    }

    public void setMainLeadText(String mainLeadText) {
        this.mainLeadText = mainLeadText;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}