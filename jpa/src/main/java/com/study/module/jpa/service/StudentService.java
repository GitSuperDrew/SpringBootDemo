package com.study.module.jpa.service;

import com.study.module.jpa.entity.Student;
import com.study.module.jpa.utils.DataTable;
import com.study.module.jpa.utils.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface StudentService {
    Long count();
    /**
     * 查询所有学生信息
     *
     * @return
     */
    List<Student> findAll();

    List<Student> findAllSqlDao();

    String findStudentByStuIdEquals(Integer stuId);

    /**
     * 根据ID，得到学生信息
     *
     * @param id 学生ID
     * @return 学生信息
     */
    Student findById(Integer id);

    /**
     * 分页查询学生集合
     *
     * @param pageBean 分页类
     * @return 学生集合
     */
    List<Student> pageFindAll(PageBean pageBean);

    List<Student> pageFindAllLikeStuName(String stuName);

    List<Student> pageByIds(List<Integer> stuIds, PageBean pageBean);

    List<Student> pageLike(Integer stuId, String stuName, Integer stuAge, PageBean pageBean);

    List<Student> pageLikeName(String stuName, PageBean pageBean);

    List<Student> pageStuNameRegExp(boolean stuNameIsCN);

}
