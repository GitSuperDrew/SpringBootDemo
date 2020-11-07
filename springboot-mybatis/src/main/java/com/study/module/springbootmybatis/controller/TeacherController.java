package com.study.module.springbootmybatis.controller;

import com.study.module.springbootmybatis.entity.Teacher;
import com.study.module.springbootmybatis.entity.TeacherDO;
import com.study.module.springbootmybatis.service.TeacherService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * REST ful 接口，查看教师详情
     *
     * @param id     教师唯一标识 ID
     * @param delTag 逻辑删除标识
     * @return 教师详情信息
     */
    @GetMapping(value = "/getByIdLogic")
    public TeacherDO getByIdLogic(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "delTag", required = false, defaultValue = "0") Integer delTag) {
        return this.teacherService.getById(id, delTag);
    }

    /**
     * REST ful 接口，查看教师详情
     *
     * @param id 教师唯一标识 ID
     * @return 教师详情信息
     */
    @GetMapping(value = "/getById/{id}")
    public Teacher getById(@PathVariable(value = "id") Long id) {
        return this.teacherService.queryById(id);
    }

}
