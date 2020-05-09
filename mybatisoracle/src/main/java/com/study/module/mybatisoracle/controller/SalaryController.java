package com.study.module.mybatisoracle.controller;

import com.study.module.mybatisoracle.entity.Salary;
import com.study.module.mybatisoracle.service.SalaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 审批流程测试表(Salary)表控制层
 *
 * @author makejava
 * @since 2020-05-09 23:22:50
 */
@RestController
@RequestMapping("salary")
public class SalaryController {
    /**
     * 服务对象
     */
    @Resource
    private SalaryService salaryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Salary selectOne(String id) {
        return this.salaryService.queryById(id);
    }

}