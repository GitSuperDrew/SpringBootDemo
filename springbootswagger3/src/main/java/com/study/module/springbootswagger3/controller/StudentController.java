package com.study.module.springbootswagger3.controller;

import com.study.module.springbootswagger3.entity.Student;
import com.study.module.springbootswagger3.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Student)表控制层
 *
 * @author makejava
 * @since 2020-10-09 09:31:30
 */
@Api(tags = {"用户信息管理"})
@RestController
@RequestMapping("/student")
public class StudentController {
    /**
     * 服务对象
     */
    @Resource
    private StudentService studentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "获取学生详情", notes = "student details", tags = {"student-tags1", "student-tag2"})
    @GetMapping("/selectOne")
    public Student selectOne(@RequestParam(value = "id") Integer id) {
        return this.studentService.queryById(id);
    }

}
