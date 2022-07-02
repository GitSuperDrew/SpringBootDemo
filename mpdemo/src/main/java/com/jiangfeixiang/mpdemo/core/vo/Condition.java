package com.jiangfeixiang.mpdemo.core.vo;


import com.jiangfeixiang.mpdemo.core.enums.JoinType;
import com.jiangfeixiang.mpdemo.core.enums.OperationEnum;
import lombok.Data;

/**
 * 查询条件
 *
 * @author zl
 */
@Data
public class Condition {
    /**
     * 字段名称
     */
    //@ApiModelProperty(value = "字段名称")
    private String fieldName;
    /**
     * 操作符
     */
    //@ApiModelProperty(value = "操作符")
    private OperationEnum operation;
    /**
     * 值
     */
    //@ApiModelProperty(value = "值")
    private String value;
    /**
     * 与下一条件连接方式
     */
    //@ApiModelProperty(value = "与下一条件连接方式")
    private JoinType joinType;
}
