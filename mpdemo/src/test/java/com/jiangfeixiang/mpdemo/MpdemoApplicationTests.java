package com.jiangfeixiang.mpdemo;

import com.google.common.collect.Lists;
import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpdemoApplicationTests {

    @Autowired
    IStudentService studentService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testOne() {
        Student student = studentService.getById(10);
        // junit 断言测试（如果，出现student为null的情况，将阻断程序继续向下执行，并将message返回在控制台）
        Assert.assertNotNull("not find", student);
        System.out.println("student name is : " + student.getStuAge());
        List<Integer> nums = Arrays.asList(1,2,2,3,4,5,6,8,13,10,14);
    }

}
