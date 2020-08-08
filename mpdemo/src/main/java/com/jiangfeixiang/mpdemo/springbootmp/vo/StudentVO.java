package com.jiangfeixiang.mpdemo.springbootmp.vo;

import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020/8/8 下午 6:09
 */
@Data
public class StudentVO extends Student {

    /**
     * 前端排序参数转化为数据库字段名
     * @return 数据库字段名
     */
    public static Map<String, String> toSqlCols (){
        Map<String, String> result = new HashMap<>(5);
        result.put("stuId", "stu_id");
        result.put("stuName", "stu_name");
        result.put("stuAge", "stu_age");
        result.put("stuSex", "stu_sex");
        return result;
    }
}
