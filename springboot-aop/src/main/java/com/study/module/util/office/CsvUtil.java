package com.study.module.util.office;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CSV 转换程 excel
 *
 * @author drew
 * @date 2021/2/23 10:32
 **/
public class CsvUtil {

    private static CsvUtil instance;

    public CsvUtil() {
    }

    public static CsvUtil getInstance() {
        if (instance == null) {
            synchronized (CsvUtil.class) {
                if (instance == null) {
                    instance = new CsvUtil();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {

         testReadCsv();
        // testExportCsv();
        // testExportCsvPlus();
    }

    private static void testReadCsv() {
        while (true) {
            System.out.println("读取csv文件的数据：（示例文件地址为：D:/csv-demo-1.csv）");
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            if ("exit".equals(in)) {
                break;
            }
            System.out.println("文件的数据如下：\n" + JSONObject.toJSONString(readCsv(in)));
        }
    }

    private static void testExportCsv() {
        String birthDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<Object> head = Arrays.asList("姓名", "年龄", "成绩", "性别", "出生日期", "是否有效");
        List<List<Object>> dataList = Arrays.asList(
                Arrays.asList("Drew", 23, 99.4, birthDay, "男", true),
                Arrays.asList("Mark", 43, 43.5, null, "男", true),
                Arrays.asList("Bob", 20, 20.0, "", "男", true),
                Arrays.asList("King", 19, null, null, "男", true),
                Arrays.asList("King", null, null, null, "男", true)
        );
        String outPutPath = "D:/";
        String filename = UUID.randomUUID().toString().substring(0, 8);

        exportCsv(head, dataList, outPutPath, filename);
        System.out.println("生成 CSV 文件成功！文件位置为：" + outPutPath + filename);
    }

    public static void testExportCsvPlus() throws IOException, IllegalArgumentException, IllegalAccessException {
        String[] titles = new String[]{"姓名", "年龄", "成绩", "性别", "出生日期", "是否有效"};
        String[] propertys = new String[]{"name", "age", "grades", "sex", "birthday", "enabled"};
        User user = new User();
        user.setName("abc");
        user.setAge(null);
        user.setGrades(45.5F);
        user.setSex("");
        user.setBirthday(new Date());
        user.setEnabled(true);

        List<User> dataList = Arrays.asList(user, user, user);
        String outputFilePath = "D:/" + UUID.randomUUID().toString().replaceAll("-", "").concat(".csv");
        CsvUtil.exportCsvPlus(titles, propertys, dataList, outputFilePath);
        System.out.println("导出成功，位置为：" + outputFilePath);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private Integer age;
        private Float grades;
        private String sex;
        private Date birthday;
        private Boolean enabled;
    }


    /**
     * 读取csv文件的数据
     *
     * @param csvFilePath csv文件所在位置(例如：“D:\评价数据.csv”)
     * @return 数据集合
     */
    public static List<List<Object>> readCsv(String csvFilePath) {
        List<List<Object>> result = new ArrayList<>();
        try {
            //换成你的文件名
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                //CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<Object> item = Arrays.asList(line.split(","));
                if (!ObjectUtils.isEmpty(item)) {
                    // TODO 针对item集合中的每一个数据，可以进行具体类型转换（例如转称 integer类型，string类型，集合set/list类型等）
                    result.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 导出生成csv格式的文件
     *
     * @param titles         csv格式头文
     * @param properties     需要导出的数据实体的属性，注意与title一一对应
     * @param list           需要导出的对象集合
     * @param outPutFilePath 输出文件地址（例如：D:\\test.csv）
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> String exportCsvPlus(String[] titles, String[] properties, List<T> list, String outPutFilePath) throws IOException, IllegalArgumentException, IllegalAccessException {
        File file = new File(outPutFilePath);
        //构建输出流，同时指定编码
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");

        //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
        for (String title : titles) {
            ow.write(title);
            ow.write(",");
        }
        //写完文件头后换行
        ow.write("\r\n");
        //写内容
        for (Object obj : list) {
            //利用反射获取所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (String property : properties) {
                for (Field field : fields) {
                    //设置字段可见性
                    field.setAccessible(true);
                    if (property.equals(field.getName())) {
                        ow.write(String.valueOf(field.get(obj)));
                        ow.write(",");
                    }
                }
            }
            //写完一行换行
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
        return "0";
    }


    /**
     * CSV文件生成方法
     *
     * @param head       表头信息
     * @param dataList   具体数据信息
     * @param outPutPath 数据文件路径（例如：”D:/outCsvDir/“）
     * @param filename   自定义文件名(例如：”csv-out-file“)
     * @return
     */
    public static File exportCsv(List<Object> head, List<List<Object>> dataList, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWriter);
            }
            csvWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            String rowStr = "\"" + data + "\",";
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

}
