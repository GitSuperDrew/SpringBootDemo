package com.study.module.springbooteasyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Administrator
 * @date 2020/10/11 下午 2:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoetExcelModel extends BaseRowModel implements Serializable {
    @NotBlank(message = "手机号不能为空")
    @Size(max = 4)
    @ExcelProperty(value = "用户名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$$", message = "手机号不合法")
    @NotBlank(message = "手机号不能为空")
    @ExcelProperty(value = "手机号", index = 2)
    private String mobile;

    @ExcelProperty(value = "性别", index = 3)
    private String sex;

}
