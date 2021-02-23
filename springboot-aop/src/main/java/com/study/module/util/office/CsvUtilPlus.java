package com.study.module.util.office;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 引入第三方的jar包操作CSV文件（导入导出）
 *
 * @author drew
 * @date 2021/2/23 13:45
 **/
public class CsvUtilPlus {

    public static void main(String[] args) throws Exception {
        // 生成CSV文件
        String outputFilePath = "D:/" + UUID.randomUUID().toString().substring(0, 4) + ".csv";
        List<String> headers = Arrays.asList("姓名", "年龄", "性别", "成绩");
        List<List<String>> content = Arrays.asList(
                Arrays.asList("drew", "12", "男", null),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList("Mark", "", null, "89.3"),
                Arrays.asList()
        );
        writer(headers, content, outputFilePath);
        System.out.println("导出CSV文件成功, 文件所在位置：" + outputFilePath);

        // 读取上方生成的CSV文件
        System.out.println("读取数据成功，数据如下：\n" + JSONObject.toJSONString(read(outputFilePath)));
    }

    /**
     * 读取 CSV 文件的数据【读物CSV文件的API：http://javacsv.sourceforge.net/com/csvreader/CsvReader.html】
     *
     * @param srcCsvPath csv文件存放的位置(例如："F:/demo.csv")
     * @throws IOException
     */
    public static List<List<String>> read(String srcCsvPath) throws IOException {
        List<List<String>> result = new LinkedList<>();
        // 第一参数：读取文件的路径 第二个参数：分隔符（不懂仔细查看引用百度百科的那段话） 第三个参数：字符集
        CsvReader csvReader = new CsvReader(srcCsvPath, ',', StandardCharsets.UTF_8);

        // 如果你的文件没有表头，这行不用执行；这行不要是为了从表头的下一行读，也就是过滤表头
        csvReader.readHeaders();

        // 读取每行的内容
        while (csvReader.readRecord()) {
            String lineData = csvReader.getRawRecord();
            String[] arr = lineData.split(",");
            result.add(Arrays.asList(arr));

//            // 获取内容的两种方式
//            // 1. 通过下标获取
//            System.out.print(csvReader.get(0));
//            // 2. 通过表头的文字获取
//            System.out.println(" " + csvReader.get("年龄"));
        }
        return result;
    }


    /**
     * 导出CSV文件(写入CSV文件的API：http://javacsv.sourceforge.net/com/csvreader/CsvWriter.html)
     *
     * @param outPutFilePath csv文件输出路径(例如："D:/demo.csv")
     * @param headers        表头
     * @param content        数据集合
     * @throws IOException
     */
    public static void writer(List<String> headers, List<List<String>> content, String outPutFilePath) throws IOException {

        // 第一参数：新生成文件的路径 第二个参数：分隔符（不懂仔细查看引用百度百科的那段话） 第三个参数：字符集
        CsvWriter csvWriter = new CsvWriter(outPutFilePath, ',', StandardCharsets.UTF_8);
        // 写表头和内容，因为csv文件中区分没有那么明确，所以都使用同一函数，写成功就行
        csvWriter.writeRecord(headers.toArray(new String[0]));
        for (List<String> line : content) {
            csvWriter.writeRecord(line.toArray(new String[0]));
        }
        // 关闭csvWriter
        csvWriter.close();
    }

}
