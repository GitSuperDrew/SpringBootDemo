package com.jiangfeixiang.mpdemo.springbootmp.controller;

import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Administrator
 * @date 2020/6/28 上午 11:29
 */
@SpringBootTest
class StudentControllerTest {

    @Autowired
    IStudentService iStudentService;

    @Test
    void getById() {
        System.out.println(iStudentService.getById(1));
    }
}
