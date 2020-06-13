package com.jiangfeixiang.mpdemo.springbootmp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author zhongl
 * @since 2019-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "stu_id", type = IdType.AUTO)
    private Integer stuId;

    private String stuName;

    private Integer stuAge;

    private String stuSex;

    @TableField(value = "deleted")
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

}
