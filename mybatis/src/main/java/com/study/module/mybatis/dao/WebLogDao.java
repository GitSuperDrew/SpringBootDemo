package com.study.module.mybatis.dao;

import com.study.module.mybatis.entity.WebLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志表(WebLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-21 21:31:02
 */
@Mapper
public interface WebLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WebLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webLog 实例对象
     * @return 对象列表
     */
    List<WebLog> queryAll(WebLog webLog);

    /**
     * 新增数据
     *
     * @param webLog 实例对象
     * @return 影响行数
     */
    int insert(WebLog webLog);

    /**
     * 修改数据
     *
     * @param webLog 实例对象
     * @return 影响行数
     */
    int update(WebLog webLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
