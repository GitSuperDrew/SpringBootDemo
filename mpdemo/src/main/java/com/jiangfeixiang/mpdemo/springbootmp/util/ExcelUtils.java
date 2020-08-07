package com.jiangfeixiang.mpdemo.springbootmp.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel 导入导出工具类（集成 Hutool工具包）
 *
 * @author Administrator
 * @date 2020/8/6 下午 1:42
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    /**
     * 文件最大限制 <10M
     */
    private static final long FILE_SIZE_MAX = 1024 * 1024 * 10;
    /**
     * EXCEL文件后缀
     */
    private static final String FILE_SUFFIX = ".xlsx";

    private static List<List<Object>> lineList = new ArrayList<>();

    /**
     * excel 导出工具类
     *
     * @param response
     * @param fileName    文件名
     * @param projects    对象集合
     * @param columnNames 导出的excel中的列名
     * @param keys        对应的是对象中的字段名字（列名对象数大于等于列名数）
     * @return true导出成功，false导出失败
     * @throws Exception
     */
    public static boolean export(HttpServletResponse response, String fileName, List<?> projects, String[] columnNames,
                                 String[] keys) {
        if (columnNames == null || keys == null) {
            return false;
        }
        try {
            ExcelWriter bigWriter = ExcelUtil.getBigWriter();
            long keyLen = keys.length, columnLen = columnNames.length;
            if (keyLen < columnLen) {
                // 对象属性不足
                return false;
            }
            for (int i = 0; i < columnLen; i++) {
                bigWriter.addHeaderAlias(columnNames[i], keys[i]);
                bigWriter.setColumnWidth(i, 20);
            }
            // 一次性写出内容，使用默认样式，强制输出标题
            bigWriter.write(projects, true);
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            bigWriter.flush(out, true);
            // 关闭writer，释放内存
            bigWriter.close();
            //此处记得关闭输出Servlet流
            IoUtil.close(out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * excel导入工具类
     *
     * @param file        文件
     * @param columnNames 列对应的字段名
     * @return 返回数据集合
     * @throws IOException
     */
    public static List<Map<String, Object>> excelImport(MultipartFile file, String[] columnNames) throws IOException {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (StringUtils.isEmpty(fileName)) {
            logger.info("没有导入文件");
            //  throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "没有导入文件");
        }
        //上传文件大小为1000条数据
        if (file.getSize() > FILE_SIZE_MAX) {
            logger.error("upload | 上传失败: 文件大小超过10M，文件大小为：{}", file.getSize());
            //  throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "上传失败: 文件大小不能超过10M!");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !FILE_SUFFIX.equals(fileName.substring(fileName.lastIndexOf(".")))) {
            logger.info("文件名格式不正确, 请使用后缀名为.XLSX的文件");
            // throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }

        //读取数据
        ExcelUtil.read07BySax(file.getInputStream(), 0, createRowHandler());
        //去除excel中的第一行数据
        lineList.remove(0);

        //将数据封装到list<Map>中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
                for (int j = 0; j < columnNames.length; j++) {
                    Object property = lineList.get(i).get(j);
                    hashMap.put(columnNames[j], property);
                }
                dataList.add(hashMap);
            } else {
                break;
            }
        }
        return dataList;
    }

    /**
     * 通过实现handle方法编写我们要对每行数据的操作方式
     */
    private static RowHandler createRowHandler() {
        //清空一下集合中的数据
        lineList.removeAll(lineList);
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List rowlist) {
                //将读取到的每一行数据放入到list集合中
                JSONArray jsonObject = new JSONArray(rowlist);
                lineList.add(jsonObject.toList(Object.class));
            }
        };
    }
}
