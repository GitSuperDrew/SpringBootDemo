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
 * CSV文件规则
 * 1 开头是不留空，以“行”为单位。
 * 2 可含或不含列名，含列名则居文件第一行。
 * 3 一行数据不跨行，无空行。
 * 4 以半角逗号（即,）作分隔符，列为空也要表达其存在。
 * 5 列内容如存在半角逗号（即,）则用半角双引号（即""）将该字段值包含起来。
 * 6 列内容如存在半角引号（即"）则应替换成半角双引号（""）转义，并用半角引号（即""）将该字段值包含起来。
 * 7 文件读写时引号，逗号操作规则互逆。
 * 8 内码格式不限，可为 ASCII、Unicode 或者其他。
 * 9 不支持特殊字符
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
            if (Arrays.asList("exit", "quit", "q").contains(in)) {
                break;
            }
            System.out.println("文件的数据如下：\n" + JSONObject.toJSONString(readCsv(in, true)));
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

    /**
     * 将字节流转换成文件
     *
     * @param filePath 文件输出后存放的地址（例如：“D:/out.xlsx”）
     * @param data     子节数组流
     * @throws Exception
     */
    public static void saveFile(String filePath, byte[] data) {
        if (data != null) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取csv文件的数据
     *
     * @param csvFilePath csv文件所在位置(例如：“D:\评价数据.csv”)
     * @param isHasHeader true第一行存在数据表头，false第一行不存在数据表头
     * @return 数据集合
     */
    public static Map<String, Object> readCsv(String csvFilePath, boolean isHasHeader) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            // 中文乱码问题：源文件的编码格式与程序设置的读取格式不一致所致（调整csv文件为UTF-8集合）
            DataInputStream in = new DataInputStream(new FileInputStream(csvFilePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "gbk"));
            //第一行信息，为标题信息，不用,如果需要，注释掉
            // reader.readLine();
            if (isHasHeader) {
                result.put("header", reader.readLine());
            }
            String line;
            List<List<String>> dataList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = splitCSV(line);
                if (!ObjectUtils.isEmpty(parts)) {
                    List<String> lineData = Arrays.asList(parts);
                    dataList.add(lineData);
                }
            }
            result.put("data", dataList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public static void testSplitCSV() {
        String src1 = "\"fh,zg\",sdf,\"asfs,\",\",dsdf\",\"aadf\"\"\",\"\"\"hdfg\",\"fgh\"\"dgnh\",hgfg'dfh,\"asdfa\"\"\"\"\",\"\"\"\"\"fgjhg\",\"gfhg\"\"\"\"hb\"";
        try {
            String[] Ret = splitCSV(src1);
            for (int i = 0; i < Ret.length; i++) {
                System.out.println(i + ": " + Ret[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Split one line of csv file
     *
     * @return a String array results
     */
    public static String[] splitCSV(String src) throws Exception {
        if (src == null || src.equals("")) {
            return new String[0];
        }
        StringBuffer st = new StringBuffer();
        Vector result = new Vector();
        boolean beginWithQuote = false;
        for (int i = 0; i < src.length(); i++) {
            char ch = src.charAt(i);
            if (ch == '\"') {
                if (beginWithQuote) {
                    i++;
                    if (i >= src.length()) {
                        result.addElement(st.toString());
                        st = new StringBuffer();
                        beginWithQuote = false;
                    } else {
                        ch = src.charAt(i);
                        if (ch == '\"') {
                            st.append(ch);
                        } else if (ch == ',') {
                            result.addElement(st.toString());
                            st = new StringBuffer();
                            beginWithQuote = false;
                        } else {
                            throw new Exception("Single double-quote char mustn't exist in filed " + (result.size() + 1) + " while it is begined with quote\nchar at:" + i);
                        }
                    }
                } else if (st.length() == 0) {
                    beginWithQuote = true;
                } else {
                    throw new Exception("Quote cannot exist in a filed which doesn't begin with quote!\nfield:" + (result.size() + 1));
                }
            } else if (ch == ',') {
                if (beginWithQuote) {
                    st.append(ch);
                } else {
                    result.addElement(st.toString());
                    st = new StringBuffer();
                    beginWithQuote = false;
                }
            } else {
                st.append(ch);
            }
        }
        if (st.length() != 0) {
            if (beginWithQuote) {
                throw new Exception("last field is begin with but not end with double quote");
            } else {
                result.addElement(st.toString());
            }
        }
        String rs[] = new String[result.size()];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = (String) result.elementAt(i);
        }
        return rs;
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

}
