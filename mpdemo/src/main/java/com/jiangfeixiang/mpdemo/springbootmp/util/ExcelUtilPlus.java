package com.jiangfeixiang.mpdemo.springbootmp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.*;

/**
 * excel 导出测试类
 *
 * @author Administrator
 * @date 2020/8/6 上午 10:19
 */
public class ExcelUtilPlus {

    public static void main(String[] args) {
        // 1.export excel
        // listExport(UUID.randomUUID(true).toString());
        // 2.export map
        // mapExport(UUID.randomUUID(true).toString());

        // 3.export bean
        testBeanExport();
    }

    public static void testBeanExport() {
        Student student1 = new Student();
        student1.setStuId(1);
        student1.setStuName("Drew");
        student1.setStuAge(23);
        student1.setStuSex("男");
        student1.setDeleted(1);
        Student student2 = new Student();
        student2.setStuId(2);
        student2.setStuName("King");
        student2.setStuAge(22);
        student2.setStuSex("男");
        student2.setDeleted(1);
        List<Student> students = Arrays.asList(student1, student2);
        Map<String, String> headerAliasMap = MapUtil.newHashMap(true);
        headerAliasMap.put("stuName", "名字");
        headerAliasMap.put("stuAge", "年龄");
        headerAliasMap.put("stuSex", "性别");
        headerAliasMap.put("stuId", "ID");
        headerAliasMap.put("deleted", "数据有效性（1有效，0无效）");
        beanExport(UUID.randomUUID(true).toString(), students, headerAliasMap);
    }

    /**
     * excel导出单个Sheet表
     *
     * @param fileName       输出文件的指定名称
     * @param students       数据源：学生集信息
     * @param headerAliasMap 单个sheet表中列名
     */
    public static void beanExport(String fileName, List<Student> students, Map<String, String> headerAliasMap) {
        // 1.得到数据集合
        List<Student> rows = CollUtil.newArrayList(students);

        // 2.导出
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/" + fileName + ".xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "学生信息表");
        // 3.自定义标题别名(// 排序的MAP==LinkHashMap)
        if (headerAliasMap != null) {
            headerAliasMap.forEach((key, value) -> {
                writer.addHeaderAlias(key, value);
            });
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);

        // 4.定义单元格背景色
        StyleSet style = writer.getStyleSet();
        // 第二个参数表示是否也设置头部单元格背景
        style.setBackgroundColor(IndexedColors.AUTOMATIC, false);

        // 5.自定义内容字体
        Font font = writer.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_NORMAL);
        font.setItalic(true);
        //第二个参数表示是否忽略头部样式
        writer.getStyleSet().setFont(font, true);

        // 关闭writer，释放内存
        writer.close();
    }

    public static void mapExport(String fileName) {
        // 1.构造map对象
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());
        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());
        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        // 2.写出我们的row对象
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/" + fileName + ".xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }

    public static void listExport(String fileName) {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/" + fileName + ".xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
        writer.close();
    }
}
