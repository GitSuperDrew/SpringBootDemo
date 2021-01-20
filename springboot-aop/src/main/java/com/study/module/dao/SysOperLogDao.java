package com.study.module.dao;

import com.study.module.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志记录(SysOperLog)表数据库访问层
 *
 * @author drew
 * @since 2021-01-19 18:38:01
 */
@Mapper
public interface SysOperLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysOperLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysOperLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysOperLog 实例对象
     * @return 对象列表
     */
    List<SysOperLog> queryAll(SysOperLog sysOperLog);

    /**
     * 新增数据
     *
     * @param sysOperLog 实例对象
     * @return 影响行数
     */
    int insert(SysOperLog sysOperLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysOperLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysOperLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysOperLog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysOperLog> entities);

    /**
     * 修改数据
     *
     * @param sysOperLog 实例对象
     * @return 影响行数
     */
    int update(SysOperLog sysOperLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}