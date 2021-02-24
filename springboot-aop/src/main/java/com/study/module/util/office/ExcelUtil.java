package com.study.module.util.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // deleteBlankAndRow("D:\\file44.xlsx", "D:\\file4.xlsx");
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    static class User {
        private int id;
        private String name;
        private String sex;
        private int age;
    }

    public static void testExportExcel(HttpServletResponse response) {
        Collection userList = new ArrayList<>();
        User user = new User(1, "张三", "男", 39);
        userList.add(user);
        user = new User(2, "如花", "女", 21);
        userList.add(user);

        String[] headers = {"序号", "姓名", "性别", "年龄"};
        String fileName = "用户信息表";

        ExcelUtil.exportExcel(headers, userList, fileName, response);
    }

    /**
     * 导出数据到 excel中
     *
     * @param headers  表头
     * @param dataset  需要输出到excel的数据
     * @param fileName 输出excel文件所在地址（例如：D:/454e.xls）
     * @param response 输出
     */
    public static void exportExcel(String[] headers, Collection<Object> dataset, String fileName, HttpServletResponse response) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(fileName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            // 遍历集合数据，产生数据行
            Iterator<Object> it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                Object t = it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < headers.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // 其它数据类型都当作字符串简单处理
                    if (value != null && value != "") {
                        textValue = value.toString();
                    }
                    if (textValue != null) {
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                }
            }
            getExportedFile(workbook, fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 产生输出
     *
     * @param workbook excel文件对象
     * @param name     文件名称
     * @param response 返回输出excel文件的二进制流
     * @throws Exception
     */
    public static void getExportedFile(XSSFWorkbook workbook, String name, HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
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
