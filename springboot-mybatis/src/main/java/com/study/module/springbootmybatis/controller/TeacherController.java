package com.study.module.springbootmybatis.controller;

import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 教师表(Teacher)表控制层
 *
 * @author makejava
 * @since 2020-11-07 19:04:41
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    /**
     * 服务对象
     */
    @Resource
    private TeacherService teacherService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Teacher selectOne(Long id) {
        return this.teacherService.queryById(id);
    }

}
