package com.study.module.mybatisoracle.dao;

import com.study.module.mybatisoracle.entity.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批流程测试表(Salary)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-10 10:12:22
 */
@Mapper
public interface SalaryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Salary queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Salary> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param salary 实例对象
     * @return 对象列表
     */
    List<Salary> queryAll(Salary salary);

    /**
     * 新增数据
     *
     * @param salary 实例对象
     * @return 影响行数
     */
    int insert(Salary salary);

    /**
     * 修改数据
     *
     * @param salary 实例对象
     * @return 影响行数
     */
    int update(Salary salary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}