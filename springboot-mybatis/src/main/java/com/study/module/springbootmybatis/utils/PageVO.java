package com.study.module.springbootmybatis.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/11/8 下午 2:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页实体类")
public class PageVO<T> {
    /**
     * 分页总数
     */
    @ApiModelProperty(value = "分页数据")
    private List<T> records;
    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private Integer total;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Integer pages;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer current;
    /**
     * 查询数量
     */
    @ApiModelProperty(value = "查询数量")
    private Integer size;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderBy;
    /**
     * 排序规则：true降序，false升序
     */
    @ApiModelProperty(value = "排序规则：true降序，false升序")
    private Boolean isDesc;

    /**
     * 设置当前页和每页显示的数量
     *
     * @param pageForm 分页表单
     * @return 返回分页信息
     */
    @ApiModelProperty(hidden = true)
    public PageVO<T> setCurrentAndSize(PageForm<?> pageForm) {
        BeanUtils.copyProperties(pageForm, this);
        return this;
    }

    /**
     * 设置总记录数
     *
     * @param total 总记录数
     */
    @ApiModelProperty(hidden = true)
    public void setTotal(Integer total) {
        this.total = total;
        this.setPages(this.total % this.size > 0 ? this.total / this.size + 1 : this.total / this.size);
    }
}
