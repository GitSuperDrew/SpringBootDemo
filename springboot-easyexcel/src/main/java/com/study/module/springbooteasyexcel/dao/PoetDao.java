package com.study.module.springbooteasyexcel.dao;

import com.study.module.springbooteasyexcel.entity.Poet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Poet)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-11 14:17:34
 */
@Mapper
public interface PoetDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Poet queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Poet> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param poet 实例对象
     * @return 对象列表
     */
    List<Poet> queryAll(Poet poet);

    /**
     * 新增数据
     *
     * @param poet 实例对象
     * @return 影响行数
     */
    int insert(Poet poet);

    /**
     * 修改数据
     *
     * @param poet 实例对象
     * @return 影响行数
     */
    int update(Poet poet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
