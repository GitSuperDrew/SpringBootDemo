package com.study.module.mybatis.controller;

import com.study.module.mybatis.entity.TSalary;
import com.study.module.mybatis.service.TSalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 薪资表(TSalary)表控制层
 *
 * @author makejava
 * @since 2020-04-25 22:08:25
 */
@Api(tags = "薪资CRUD", value = "对薪资的增加删除跟新和删除操作")
@RestController
@RequestMapping("tSalary")
public class TSalaryController {
    /**
     * 服务对象
     */
    @Resource
    private TSalaryService tSalaryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiIgnore
    @GetMapping("selectOne")
    public TSalary selectOne(Integer id) {
        return this.tSalaryService.queryById(id);
    }

    @ApiOperation(value = "查询所有的员工信息", notes = "get all employee info.")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<TSalary> findAll() {
        return tSalaryService.queryAllByLimit(0, 100);
    }

    @ApiOperation(value = "根据员工ID，查询员工信息")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "path", dataType = "Integer", defaultValue = "1")
    @RequestMapping(value = "/findOne/{id}", method = RequestMethod.GET)
    public TSalary findOne(@PathVariable(value = "id") int id) {
        return this.tSalaryService.queryById(id);
    }

}