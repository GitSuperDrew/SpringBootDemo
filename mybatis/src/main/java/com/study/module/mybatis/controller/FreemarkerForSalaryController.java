package com.study.module.mybatis.controller;

import com.google.common.collect.ImmutableMap;
import com.study.module.mybatis.entity.TSalary;
import com.study.module.mybatis.service.TSalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = "FreeMarker调试（薪资表）", value = "利用freemarker，调试薪资表")
@Controller
@RequestMapping(value = "/freemarkerForSalary")
public class FreemarkerForSalaryController {

    @Resource
    private TSalaryService tSalaryService;

    @ApiOperation(value = "查询所有的员工信息", notes = "get all employee info.")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<TSalary> findAll() {
        return tSalaryService.queryAllByLimit(0, 100);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("data", tSalaryService.queryAllByLimit(0, 100));
        return "/salary/list";
    }

    /**
     * 如果浏览器中发送：http://localhost:8089/freemarkerForSalary/list 得到是："/salary/salaryList";
     * 说明：此类的@RestController需要改为@Controller
     *
     * @return
     */
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public String list2() {
        return "/salary/salaryList";
    }

    @ResponseBody
    @RequestMapping(value = "/allSalary", method = RequestMethod.GET)
    public Map<String, Object> allSalary(){
        List<TSalary> lists = tSalaryService.queryAllByLimit(0,100);
        return ImmutableMap.of("data", lists);
    }

    @ApiOperation(value = "根据员工ID，得到员工信息", notes = "get employee`info by id.")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer", paramType = "path",
            defaultValue = "1")
    @RequestMapping(value = "/findOne/{id}", method = RequestMethod.GET)
    public TSalary findOne(@PathVariable(value = "id") int id) {
        return tSalaryService.queryById(id);
    }
}
