package com.study.module.springbooteasyexcel.dao;

import com.study.module.springbooteasyexcel.entity.AssetsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 固定资产分类代码（国标 GB/T 14885-2010）(AssetsType)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-13 11:00:51
 */
@Mapper
public interface AssetsTypeDao {

    /**
     * 判断资产表是否存在数据
     *
     * @return 1 true真实,false失败
     */
    @Select(value = "select 1 from assets_type limit 1;")
    Boolean hasData();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AssetsType queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AssetsType> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询第一个层级的数据
     *
     * @return 第一个层级所有的资产类型数据
     */
    List<AssetsType> queryOneAll();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param assetsType 实例对象
     * @return 对象列表
     */
    List<AssetsType> queryAll(AssetsType assetsType);

    /**
     * 新增数据
     *
     * @param assetsType 实例对象
     * @return 影响行数
     */
    int insert(AssetsType assetsType);

    /**
     * 修改数据
     *
     * @param assetsType 实例对象
     * @return 影响行数
     */
    int update(AssetsType assetsType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
