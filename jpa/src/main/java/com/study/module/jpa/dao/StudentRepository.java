package com.study.module.jpa.dao;

import com.study.module.jpa.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA 的使用：
 * 1.继承extends JpaRepository<Student, Integer> ：第一个参数为实体对应于数据库表，第二个字段为主键
 * 2.自定义方法的时候有一定的规范，例如查询为findStudent By... Or... And... 且需要按照参数的顺序来些（当然这样会导致方法名很长）
 *
 * @author Administrator
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

    /**
     * 根据学生名称模糊查询
     *
     * @param stuName
     * @return
     */
    @Query(value = "select s from student s where s.stuName like %?1%")
    List<Student> findStudentByStuName(String stuName);

    /**
     * 原生SQL执行
     *
     * @return
     */
    @Query(nativeQuery = true, value = "select * from student")
    List<Student> findAllSqlDao();

    @Query(nativeQuery = true, value = "select * from student where stu_name REGEXP ?1")
    List<Student> findStudentsByStuNameRegex(String stuNameRegExp);

    @Query(value = "select s.stuName from student s where s.stuId=:stuId")
    String findStudentByStuIdEquals(@Param("stuId") int stuId);

    /**
     * 批量查询学生信息
     *
     * @param stuIds   学生ID集合
     * @param pageable
     * @return
     */
    Page<Student> findAllByStuIdIn(List<Integer> stuIds, Pageable pageable);

    Page<Student> findAll(Specification specification, Pageable pageable);

    Page<Student> findStudentByStuIdEqualsOrStuNameLikeOrStuAgeEquals(int stuId, String stuName, int stuAge,
                                                                      Pageable pageable);

    Page<Student> findStudentsByStuAgeIsOrStuNameIsLikeOrStuIdIs(int stuAge, String stuName, int stuId,
                                                                 Pageable pageable);

    Page<Student> findStudentsByStuAgeEqualsOrStuNameLikeOrStuAgeEquals(int stuAge, String stuName, int stuId,
                                                                        Pageable pageable);

}
