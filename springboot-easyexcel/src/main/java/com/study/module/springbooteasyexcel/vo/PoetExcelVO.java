package com.study.module.springbooteasyexcel.vo;

import com.study.module.springbooteasyexcel.model.PoetExcelModel;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/10/11 下午 2:52
 */
@Data
public class PoetExcelVO {
    /**
     * 成功列表
     */
    private List<PoetExcelModel> success;
    /**
     * 失败列表
     */
    private List<PoetExcelModel> fail;
}
