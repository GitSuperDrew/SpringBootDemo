package com.study.module.util.office;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * @author drew
 */
public class ExcelUtil {

    public static void main(String[] args) throws IOException {
        deleteBlankAndRow("D:\\file44.xlsx", "D:\\file4.xlsx");
    }


    /**
     * 删除excel中多余空行
     *
     * @param srcFilePath    源文件地址
     * @param targetFilePath 目标文件地址
     * @throws IOException
     */
    private static void deleteBlankAndRow(String srcFilePath, String targetFilePath) throws IOException {
        long startTime = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(srcFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                Iterator<Cell> iterator = row.cellIterator();
                while (iterator.hasNext()) {
                    String tempValue = getCellValue(iterator.next());
                    if (!ObjectUtils.isEmpty(tempValue)) {
                        i++;
                        break;
                    }
                }
            } else {
                sheet.shiftRows(i + 1, lastRowNum, -1);
                i--;
                //减去一条空行，总行数减一。
                lastRowNum--;
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        fileInputStream.close();
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "\n删除空行成功！新文件地址位：" + targetFilePath);
    }

    /**
     * 获取单元格的值（不同类型不同处理方法），最终返回字符串类型的数据
     *
     * @param cell 单元格
     * @return 单元格值（字符串）
     */
    public static String getCellValue(Cell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                //字符串类型
                case Cell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                //数字类型
                case Cell.CELL_TYPE_NUMERIC:
                    // 处理日期格式、时间格式
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = null;
                        // short dataFormat = cell.getCellStyle().getDataFormat();
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = cell.getDateCellValue();
                        strCell = sdf.format(date);
                    } else {
                        strCell = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                //boolean类型
                case Cell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    strCell = "";
                    break;
            }
        }
        if (ObjectUtils.isEmpty(strCell)) {
            strCell = "";
        }
        return strCell.trim();
    }


}
