package com.study.module.mapstruct.controller;

import com.study.module.mapstruct.dto.StudentDTO;
import com.study.module.mapstruct.entity.Student;
import com.study.module.mapstruct.service.StudentConverter;
import com.study.module.mapstruct.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Student)表控制层
 *
 * @author makejava
 * @since 2020-07-26 08:53:15
 */
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
    @GetMapping("/selectOne")
    public Student selectOne(Integer id) {
        return this.studentService.queryById(id);
    }

    /**
     * 经过DTO处理过的Entity数据
     *
     * @param id 学生ID
     * @return 前端需要展示的数据（VO-DTO-AO）
     */
    @GetMapping("/getStuDto")
    public StudentDTO getOne(@RequestParam(value = "id") Integer id) {
        return StudentConverter.INSTANCE.demain2DTO(this.studentService.queryById(id));
    }

}