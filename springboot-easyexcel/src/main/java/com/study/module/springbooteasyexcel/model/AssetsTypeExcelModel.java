package com.study.module.springbooteasyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.sun.rowset.internal.BaseRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Administrator
 * @date 2020/10/13 上午 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsTypeExcelModel extends BaseRowModel implements Serializable {
    /**
     * 资产分类代码
     */
    @NotBlank(message = "资产分类代码不能为空")
    @Size(max = 10)
    @ExcelProperty(value = "资产分类代码", index = 0)
    private String assetsCode;

    /**
     * 资产分类名称
     */
    @NotBlank(message = "资产分类名称不能为空")
    @Size(max = 255)
    @ExcelProperty(value = "资产分类名称", index = 1)
    private String assetsName;
}
