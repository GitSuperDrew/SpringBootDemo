package com.study.module.springbooteasyexcel.vo;

import com.study.module.springbooteasyexcel.model.AssetsTypeExcelModel;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/10/13 上午 11:08
 */
@Data
public class AssetsTypeExcelVO {
    /**
     * 成功列表
     */
    private List<AssetsTypeExcelModel> success;
    /**
     * 失败列表
     */
    private List<AssetsTypeExcelModel> fail;
}
