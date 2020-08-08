package com.jiangfeixiang.mpdemo.springbootmp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import com.jiangfeixiang.mpdemo.springbootmp.util.ExcelUtilPlus;
import com.jiangfeixiang.mpdemo.springbootmp.util.ExcelUtils;
import com.jiangfeixiang.mpdemo.springbootmp.util.PdfFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author zhongl
 * @since 2019-06-16
 */
@RestController
@RequestMapping("/springbootmp/student")
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    /**
     * （逻辑查询）所有的学生信息
     * URL：http://localhost:8888/springbootmp/student/getAllStudent
     *
     * @return 所有的未被逻辑删除的学生信息
     */
    @RequestMapping("/getAllStudent")
    public List<Student> getAllStudent() {
        List<Student> list = iStudentService.list();
        return list;
    }

    /**
     * 分页查询：根据名称模糊匹配
     * 测试1：(用来测试全局异常，令 stuId 改为 id 排序字段)
     * URL：http://localhost:8888/springbootmp/student/pageAllByNameLike?currentPage=1&pageCount=3&orderBy=id&isDesc=true
     *
     * @param currentPage 当前页码
     * @param pageCount   当前页显示条目数
     * @param orderBy     排序字段
     * @param isDesc      排序规则（true：降序，false：升序）
     * @return
     */
    @GetMapping(value = "pageAllByNameLike")
    public Map<String, Object> pageAllByNameLike(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                                 @RequestParam(value = "pageCount", defaultValue = "10") int pageCount,
                                                 @RequestParam(value = "orderBy") String orderBy,
                                                 @RequestParam(value = "isDesc", defaultValue = "true") boolean isDesc,
                                                 @RequestParam(value = "search", required = false) String search) {
        Page<Student> page = new Page(currentPage, pageCount);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        if (StringUtils.isNotBlank(search)) {
            Student student = JSONUtil.toBean(search, Student.class);
            if (student.getStuId() != null) {
                queryWrapper.eq("stu_id", student.getStuId());
            }
            if (StringUtils.isNotBlank(student.getStuName())) {
                queryWrapper.like("stu_name", student.getStuName());
            }
            if (StringUtils.isNotBlank(student.getStuSex())) {
                queryWrapper.eq("stu_sex", student.getStuSex());
            }
            if (student.getStuAge() != null) {
                queryWrapper.eq("stu_age", student.getStuAge());
            }
        }
        // TODO 此处的 orderBy 是前端传递过来的（例如按照学生年龄排序，传入的值为 stuAge，而方法 page.setDesc(var) var需要的是数据库字段的值，所以需要一个VO或map将其转化为数据库字段）
        if (isDesc) {
            page.setDesc(orderBy);
        } else {
            page.setAsc(orderBy);
        }
        IPage<Student> iPage = iStudentService.page(page, queryWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", iPage.getRecords());
        result.put("page", ImmutableMap.builder()
                .put("currentPage", iPage.getCurrent())
                .put("pageCount", iPage.getSize())
                .put("orderBy", orderBy)
                .put("isDesc", isDesc)
                .put("totalPage", iPage.getPages())
                .put("totalCount", iPage.getTotal()).build());
        return result;
    }

    /**
     * (逻辑查询)根据学生ID，得到学生信息详情
     * URL：http://localhost:8888/springbootmp/student/getById?id=3
     *
     * @param id 学生ID
     * @return 学生信息
     */
    @GetMapping(value = "/getById")
    public Student getById(@RequestParam(value = "id", defaultValue = "1") int id) {
        return iStudentService.getById(id);
    }

    /**
     * 导出所有学生信息详情
     * URL：http://localhost:8888/springbootmp/student/exportAllStudent
     *
     * @return 是否导出成功
     */
    @GetMapping(value = "/exportAllStudent")
    public String exportAllStudent() {
        try {
            List<Student> students = iStudentService.list();
            Map<String, String> headerMap = ImmutableMap.of("stuId", "学生编号", "stuName", "学生姓名", "stuAge", "学生年龄",
                    "stuSex", "学生性别", "deleted", "数据有效性：0无效，1有效");
            ExcelUtilPlus.beanExport(UUID.fastUUID().toString(), students, headerMap);
            return "导出成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    /**
     * 基于Controller的导出
     *
     * @param response 回显
     * @return 是否导入成功
     */
    @GetMapping(value = "/exportStudent")
    public String exportStudent(HttpServletResponse response) {
        try {
            List<Student> students = iStudentService.list();
            Map<String, String> headerMap = ImmutableMap.of("stuId", "学生编号", "stuName", "学生姓名", "stuAge", "学生年龄",
                    "stuSex", "学生性别", "deleted", "数据有效性：1无效，0有效");
            String[] columnNames = Lists.newArrayList(headerMap.keySet()).toArray(new String[]{});
            //new String[]{"stuId", "stuName", "stuAge", "stuSex", "deleted"};
            String[] alias = Lists.newArrayList(headerMap.values()).toArray(new String[]{});
            //new String[]{"学生编号", "学生姓名", "学生年龄", "学生性别", "数据有效性：1无效，0有效"};
            ExcelUtils.export(response, UUID.fastUUID().toString(), students, columnNames, alias);
            return "导出成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    /**
     * 逻辑删除
     * 请使用Postman测试：http://localhost:8888/springbootmp/student/deleteById/4
     *
     * @param id 学生ID
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") int id) {
        return iStudentService.removeById(id) ? "删除成功" : "删除失败";
    }

}
