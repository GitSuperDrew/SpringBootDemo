package edu.study.module.springbootswagger3.util;

import org.apache.poi.hssf.usermodel.*;
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

        // 指定单元格添加批注
        sheetList = setHSSFComment(sheetList, 3, 1, "测试批注单元格", "Drew");

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


    /**
     * 设置单元格批注
     *
     * @param sheet          需要设置的sheet
     * @param curRowIndex    指定行
     * @param curColIndex    指定列
     * @param contentComment 批注内容
     * @param author         指定批注的作者
     * @return
     */
    public static HSSFSheet setHSSFComment(HSSFSheet sheet, int curRowIndex, int curColIndex, String contentComment,
                                           String author) {
        //创建绘图对象
        HSSFPatriarch p = sheet.createDrawingPatriarch();
        //创建单元格对象,批注插入到4行,1列,B5单元格
        HSSFCell cell = sheet.createRow(curRowIndex).createCell(curColIndex);
        //插入单元格内容
        // cell.setCellValue(new HSSFRichTextString("批注"));
        //获取批注对象(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)  前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
        //输入批注信息
        comment.setString(new HSSFRichTextString(contentComment));
        //添加作者,选中B5单元格,看状态栏
        comment.setAuthor(author);
        //将批注添加到单元格对象中
        cell.setCellComment(comment);
        return sheet;
    }

}
