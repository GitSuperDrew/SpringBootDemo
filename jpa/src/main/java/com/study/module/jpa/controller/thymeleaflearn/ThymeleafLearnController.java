package com.study.module.jpa.controller.thymeleaflearn;

import com.google.common.collect.ImmutableMap;
import com.study.module.jpa.entity.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/learn-thymeleaf")
public class ThymeleafLearnController {

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        // 0.定位到那个页面
        ModelAndView modelAndView = new ModelAndView("thymeleaf-learn");
        // 1.添加数据到 Model 中，在 页面通过 thymeleaf 的语法提取指定的key,在处理value的数据类型
        modelAndView.addObject("text", "处理字符串");
        modelAndView.addObject("utext", "<strong>加粗</strong>");
        // 2.插入list集合
        List<String> stringList = Arrays.asList("drew", "Bob", "Allen", "Mary", "King");
        modelAndView.addObject("stringList", stringList);
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5);
        modelAndView.addObject("numList", numList);
        // 3.插入map
        Map<String, Object> map = ImmutableMap.of("stuId", 23, "stuName", "Drew", "isDesc", true);
        modelAndView.addObject("map", map);
        // 4.插入对象Student
        Student student = new Student();
        student.setStuId(100);
        student.setStuName("Boby");
        student.setStuAge(23);
        modelAndView.addObject("student", student);
        // 5.日期处理
        modelAndView.addObject("mydate", new Date());
        // 6. set集合
        Set<String> allNames = new HashSet<String>() ;
        List<Integer> allIds = new ArrayList<Integer>() ;
        for (int x = 0 ; x < 5 ; x ++) {
            allNames.add("boot-" + x) ;
            allIds.add(x) ;
        }
        modelAndView.addObject("names", allNames);
        modelAndView.addObject("ids", allIds);
        // 返回到前端的页面 thymeleaf-learn.html
        return modelAndView;
    }
}
