package com.study.module.springbootswagger3.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 设置指定行范围和列范围的 下拉选择框、文本提示
 * 学习网址：https://blog.csdn.net/pyl574069214/article/details/52995884
 *
 * @author Administrator
 * @date 2020/10/9 上午 11:27
 */
public class PoiTest2 {

    public static void main(String[] args) throws IOException {
//        // 测试指定单元格自动生成下拉框选项 和 文字提示
        testGenSelectorsAndMsg();
    }


    /**
     * 测试指定单元格自动生成下拉框选项
     *
     * @throws IOException
     */
    private static void testGenSelectorsAndMsg() throws IOException {
        // excel文件对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 工作表对象
        HSSFSheet sheetList = wb.createSheet("工作表（SheetList）");
        String filePath = "d:\\success.xls";
        FileOutputStream out = new FileOutputStream(filePath);
        String[] textList = {"列表1", "列表2", "列表3", "列表4", "列表5"};

        // 第一列的前501行都设置为选择列表形式.
        sheetList = setHSSFValidation(sheetList, textList, 0, 500, 0, 0);

        // 第二列的前501行都设置提示.
        sheetList = setHSSFPrompt(sheetList, "promt Title", "prompt Content", 0, 500, 1, 1);

        wb.write(out);
        out.close();
        System.out.println("执行成功，请查看文件: " + filePath);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textList 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textList, int firstRow, int endRow,
                                              int firstCol, int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidationList);
        return sheet;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent,
                                          int firstRow, int endRow, int firstCol, int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
        dataValidationView.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(dataValidationView);
        return sheet;
    }

}
