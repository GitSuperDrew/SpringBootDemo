package com.study.module.util.office;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * excel工具类：
 *     1.删除excel中出现的空行;
 * </pre>
 *
 * @author drew
 */
public class ExcelUtil {
    private Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static void main(String[] args) throws IOException {
        deleteBlankAndRow("D:\\file44.xlsx", "D:\\file4.xlsx");
    }

    /**
     * 导出数据
     *
     * @param sheetTitle 表格名称
     * @param title      表头
     * @param list       数据
     * @return 二进制数据
     */
    public static byte[] export(String sheetTitle, String[] title, List<Object> list) {
        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框样式
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBold(true);

        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
        cellStyle2.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle2.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle2.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle2.setBorderRight(BorderStyle.THIN);//右边框

        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //设置边框样式
        cellStyle3.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle3.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle3.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle3.setBorderRight(BorderStyle.THIN);//右边框

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (title.length - 1)));

        //创建标题
        HSSFRow rowTitle = sheet.createRow(1);
        rowTitle.setHeightInPoints(20);

        HSSFCell hc;
        for (int i = 0; i < title.length; i++) {
            hc = rowTitle.createCell(i);
            hc.setCellValue(title[i]);
            hc.setCellStyle(cellStyle2);
        }

        byte result[] = null;
        ByteArrayOutputStream out = null;
        try {
            //创建表格数据
            Field[] fields;
            int i = 2;

            for (Object obj : list) {
                fields = obj.getClass().getDeclaredFields();
                HSSFRow rowBody = sheet.createRow(i);
                rowBody.setHeightInPoints(20);
                int j = 0;
                for (Field f : fields) {
                    f.setAccessible(true);
                    Object va = f.get(obj);
                    if (null == va) {
                        va = "";
                    }
                    hc = rowBody.createCell(j);
                    hc.setCellValue(String.valueOf(va));
                    hc.setCellStyle(cellStyle3);
                    j++;
                }
                i++;
            }

            out = new ByteArrayOutputStream();
            wb.write(out);
            result = out.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    wb.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 删除excel中多余空行
     *
     * @param srcFilePath    源文件地址
     * @param targetFilePath 目标文件地址
     * @throws IOException
     */
    public static void deleteBlankAndRow(String srcFilePath, String targetFilePath) throws IOException {
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
    private static String getCellValue(Cell cell) {
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
