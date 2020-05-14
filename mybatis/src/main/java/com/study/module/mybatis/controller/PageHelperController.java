package com.study.module.mybatis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.module.mybatis.entity.TSalary;
import com.study.module.mybatis.service.TSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分页插件 PageHelper 的学习和使用。
 *
 * @author Administrator
 * @date 2020/5/14 上午 9:11
 */
@RestController
@RequestMapping(value = "/page")
public class PageHelperController {
    @Autowired
    private TSalaryService salaryService;

    /**
     * URL:  http://localhost:8089/page/salary/1
     *
     * @param currentPage 当前页码
     * @return
     */
    @RequestMapping(value = "/salary/{currentPage}", method = RequestMethod.GET)
    public PageInfo<TSalary> pageSalary(@PathVariable(value = "currentPage") int currentPage) {
        // 设置分页规则
        PageHelper.startPage(currentPage, 10);
        // 返回所有分页信息参数为查询所有记录的信息
        PageInfo<TSalary> pageInfo = new PageInfo<>(salaryService.tSalaryPage());
        return pageInfo;
    }

    /**
     * 分页插件的使用(默认：升序排序，没有实现 降序)
     * URL：http://localhost:8089/page/salary?currentPage=1&pageCount=5&orderColumn=id
     *
     * @param currentPage 当前页码，默认值未1
     * @param pageCount   当前页显示数量
     * @param orderColumn 排序字段
     * @return 分页后的数据
     */
    @RequestMapping(value = "/salary", method = RequestMethod.GET)
    public List<TSalary> salaryList(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                    @RequestParam(value = "pageCount", defaultValue = "10") int pageCount,
                                    @RequestParam(value = "orderColumn", defaultValue = "id") String orderColumn) {
        PageHelper.startPage(currentPage, pageCount, orderColumn);
        return salaryService.queryAll();
    }

    /**
     * 分页插件的使用
     * URL：http://localhost:8089/page/salaryPlus?currentPage=1&pageCount=5&orderColumn=id&sort=desc
     *
     * @param currentPage 当前页码，默认值未1
     * @param pageCount   当前页显示数量
     * @param orderColumn 排序字段
     * @return 分页后的数据
     */
    @RequestMapping(value = "/salaryPlus", method = RequestMethod.GET)
    public List<TSalary> salaryListPlus(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                        @RequestParam(value = "pageCount", defaultValue = "10") int pageCount,
                                        @RequestParam(value = "orderColumn", defaultValue = "id") String orderColumn,
                                        @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort) {
        // 分页, 指定字段是否降序【格式：orderBy("字段 排序规律")】，排序规律：desc 降序，asc 升序
        String o = orderColumn + " " + sort;
        PageHelper.startPage(currentPage, pageCount, o);
        return salaryService.queryAll();
    }

}
