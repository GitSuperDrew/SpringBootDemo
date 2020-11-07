package com.study.module.springbootmybatis;

import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Autowired
    private TeacherService teacherService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetById() {
        Teacher teacher = this.teacherService.queryById(1L);
        System.out.println(teacher);
    }

}
