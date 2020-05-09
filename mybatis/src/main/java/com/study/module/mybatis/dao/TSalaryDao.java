package com.study.module.mybatis.dao;

import com.study.module.mybatis.entity.TSalary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资表(TSalary)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-25 22:08:25
 */
public interface TSalaryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSalary queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TSalary> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tSalary 实例对象
     * @return 对象列表
     */
    List<TSalary> queryAll(TSalary tSalary);

    /**
     * 新增数据
     *
     * @param tSalary 实例对象
     * @return 影响行数
     */
    int insert(TSalary tSalary);

    /**
     * 修改数据
     *
     * @param tSalary 实例对象
     * @return 影响行数
     */
    int update(TSalary tSalary);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}