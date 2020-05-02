package com.study.module.jpa.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.study.module.jpa.entity.Student;
import com.study.module.jpa.service.StudentService;
import com.study.module.jpa.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RequestMapping(value = "/student")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/list")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping(value = "/list2")
    public List<Student> findAllSqlDao() {
        return studentService.findAllSqlDao();
    }

    @RequestMapping(value = "/list/{id}")
    public Student findById(@PathVariable("id") Integer id) {
        return studentService.findById(id);
    }

    /**
     * http://localhost:8888/student/stuName/1
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/stuName/{id}")
    public String findStudentByStuIdEquals(@PathVariable("id") Integer id) {
        return studentService.findStudentByStuIdEquals(id);
    }

    /**
     * 放入到freemarker中，也页面的形式显示<br/>
     * http://localhost:8888/student/students
     *
     * @return
     */
    @RequestMapping(value = "/students")
    public ModelAndView findAllStudent() {
        ModelAndView modelAndView = new ModelAndView("students");
        List<Student> students = studentService.findAll();
        modelAndView.addObject("students", students);
        return modelAndView;
    }


    @Resource
    JdbcTemplate jdbcTemplate;

    /**
     * Spring Data JDBC 的数据查询
     * 示例： http://localhost:8888/student/findAllStus
     *
     * @return 全部学生信息
     */
    @RequestMapping(value = "/findAllStus", method = RequestMethod.GET)
    public List<Map<String, Object>> findAllStus() {
        List<Map<String, Object>> students = jdbcTemplate.queryForList("select * from student;");
        return students;
    }

    /**
     * 示例：http://localhost:8888/student/studentsOfDataTable
     *
     * @return
     */
    @RequestMapping(value = "/studentsOfDataTable", method = RequestMethod.GET)
    public ModelAndView studentsOfDataTable() {
        ModelAndView modelAndView = new ModelAndView("studentsOfDataTable");
        return modelAndView;
    }

    // ====================== Shiro ========================
    // ====================== 👇👇👇 ========================

    /**
     * 登录限制（Shiro）
     *
     * @return
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public ModelAndView toLogin() {
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password) {
        ModelAndView modelAndView = new ModelAndView();
        // 使用shiro写验证操作
        // 1.获取 Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 3. 执行登录方法
        try {
            subject.login(token);
            // 登录成功，跳转到指定页面
            modelAndView = new ModelAndView("/student/students");
        } catch (UnknownAccountException e) {
            // 登入失败
            modelAndView.addObject("msg", "登陆失败");
            modelAndView = new ModelAndView("/login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/stu_module-list", method = RequestMethod.GET)
    public ModelAndView stuList() {
        ModelAndView modelAndView = new ModelAndView("student/list");
        return modelAndView;
    }

    @RequestMapping(value = "/stu_module-add", method = RequestMethod.GET)
    public ModelAndView stuAdd() {
        ModelAndView modelAndView = new ModelAndView("student/add");
        return modelAndView;
    }

    @RequestMapping(value = "/stu_module-update", method = RequestMethod.GET)
    public ModelAndView stuUpdate() {
        ModelAndView modelAndView = new ModelAndView("student/update");
        return modelAndView;
    }
    // ==================== Shiro ==========================
    // ==================== 👆👆👆 ==========================

    /**
     * 请求的地址：http://localhost:8888/student/layui-form
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/layui-form", method = RequestMethod.GET)
    public ModelAndView layUI_table(ServletRequest request) {
        // 这里的 layui-form-add指的是页面名
        ModelAndView modelAndView = new ModelAndView("layui-form-add");
        return modelAndView;
    }

    /**
     * 示例：http://localhost:8888/student/findAllToLayUI
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/findAllToLayUI", method = RequestMethod.GET)
    public Map<String, Object> findAllToLayUI(ServletRequest request) {
        Map<String, Object> params = getRequestParams(request);
        String curr = request.getParameter("currentPage");
        String nums = request.getParameter("pageCount");
        // 当前页码
        int currentPage = Integer.parseInt(StringUtils.isNotBlank(curr) ? curr : "1");
        // 每页显示显示的最大条目
        int pageCount = Integer.parseInt(StringUtils.isNotBlank(nums) ? nums : "10");
        int startIndex, endIndex;
        if (currentPage == 1) {
            startIndex = 0;
        } else {
            startIndex = (currentPage - 1) * pageCount;
        }
        String sql = "select * from student where 1=1 ";
        String search_name = request.getParameter("search_name");
        if (StringUtils.isNotBlank(search_name)) {
            sql += "\n\t and stu_name like '%" + search_name + "%'";
        }
        sql += "\tlimit " + startIndex + "," + pageCount;
        List<Map<String, Object>> students = jdbcTemplate.queryForList(sql);
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", 0);
        result.put("msg", "success");
        result.put("count", studentService.count());
        result.put("data", students);
        return result;
    }

    /**
     * 示例：http://localhost:8888/student/pageFindAll
     *
     * @return
     */
    @RequestMapping(value = "/pageFindAll", method = RequestMethod.GET)
    public List<Student> pageFindAll() {
        PageBean pageBean = new PageBean(1, 3, "stuId", "desc");
        List<Student> students = studentService.pageFindAll(pageBean);
        return students;
    }

    /**
     * 例如：http://localhost:8888/student/pageFindAllLikeStuName/a
     *
     * @param stuName 学生姓名
     * @return
     */
    @RequestMapping(value = "/pageFindAllLikeStuName/{stuName}", method = RequestMethod.GET)
    public List<Student> pageFindAllLikeStuName(@PathVariable("stuName") String stuName) {
        List<Student> students = studentService.pageFindAllLikeStuName(stuName);
        return students;
    }

    /**
     * 示例：http://localhost:8888/student/pageByIds
     *
     * @return
     */
    @RequestMapping(value = "/pageByIds", method = RequestMethod.GET)
    public List<Student> pageByIds() {
        List<Integer> stuIds = Lists.newArrayList(1, 2, 3, 4, 6);
        PageBean pageBean = new PageBean(1, 3, "stuId", "desc");
        List<Student> students = studentService.pageByIds(stuIds, pageBean);
        return students;
    }

    /**
     * 查询动态多字段-分页[自定义规则]
     * 示例一： http://localhost:8888/student/pageLike?stuId=&stuName=D&stuAge=
     * 示例二： http://localhost:8888/student/pageLike?stuId=&stuName=&stuAge=22
     * 示例三： http://localhost:8888/student/pageLike?stuId=3&stuName=&stuAge=
     * 示例四：http://localhost:8888/student/pageLike?stuId=2&stuName=&stuAge=22
     *
     * @param stuId   学生ID
     * @param stuName 学生名称
     * @param stuAge  学生年龄
     * @return
     */
    @RequestMapping(value = "/pageLike", method = RequestMethod.GET)
    public List<Student> pageLike(@RequestParam(value = "stuId") Integer stuId,
                                  @RequestParam(value = "stuName") String stuName,
                                  @RequestParam(value = "stuAge") Integer stuAge) {
        PageBean pageBean = new PageBean(1, 3, "stuId", "desc");
        List<Student> students = studentService.pageLike(stuId, stuName, stuAge, pageBean);
        return students;
    }

    /**
     * 自定义规则查询[单一字段模糊查询]：如果名字是英文的，则不区分大小写查询得到
     * 示例一：http://localhost:8888/student/pageLikeStuName?stuName=  (得到所有的数据)
     * 示例二：http://localhost:8888/student/pageLikeStuName?stuName=d （得到 d或D 的匹配到的数据）
     *
     * @param stuName 学生姓名中的部分或全部字符
     * @return
     */
    @RequestMapping(value = "/pageLikeStuName", method = RequestMethod.GET)
    public List<Student> pageLikeStuName(@RequestParam(value = "stuName") String stuName) {
        PageBean pageBean = new PageBean(1, 3, "stuId", "desc");
        List<Student> students = studentService.pageLikeName(stuName, pageBean);
        return students;
    }

    /**
     * 示例：http://localhost:8888/student/pageLikeRegExpForStuName?stuNameIsCN=true
     * 示例：http://localhost:8888/student/pageLikeRegExpForStuName?stuNameIsCN=false
     *
     * @param stuNameIsCN 是否是中文？true是中文，false非中文 (JPA中CriteriaBuilder不能和MySQL中的regexExpression中连用，改用@Query)
     * @return 匹配的学生集合
     */
    @RequestMapping(value = "/pageLikeRegExpForStuName", method = RequestMethod.GET)
    public List<Student> pageLikeRegExpForStuName(@RequestParam(value = "stuNameIsCN") Boolean stuNameIsCN) {
//        PageBean pageBean = new PageBean(1, 3, "stuId", "desc");
        List<Student> students = studentService.pageStuNameRegExp(stuNameIsCN);
        return students;
    }


    /**
     * 得到请求的所有的参数
     *
     * @param request
     * @return
     */
    private static Map<String, Object> getRequestParams(ServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length > 0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }
}
