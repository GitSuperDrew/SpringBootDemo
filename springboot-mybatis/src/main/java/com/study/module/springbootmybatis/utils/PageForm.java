package com.study.module.springbootmybatis.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * @author Administrator
 * @date 2020/11/8 下午 2:58
 */
@Data
@ApiModel(value = "分页数据", description = "分页需要的表单数据")
public class PageForm<T extends PageForm<?>> {
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码，从第一页开始 1")
    @Min(value = 1, message = "页码输入有误")
    private Integer current;
    /**
     * 每页显示条目数
     */
    @ApiModelProperty(value = "每页显示的数量，范围在1~100")
    @Range(min = 1, max = 100, message = "每页显示的数量输入有误")
    private Integer size;

    public T calcCurrent() {
        current = (current - 1) * size;
        return (T) this;
    }
}
