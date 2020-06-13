package com.jiangfeixiang.mpdemo.springbootmp.controller;

import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
