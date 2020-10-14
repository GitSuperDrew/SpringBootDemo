package com.study.module.springbooteasyexcel.service;

import com.study.module.springbooteasyexcel.entity.AssetsType;

import java.util.List;

/**
 * 固定资产分类代码（国标 GB/T 14885-2010）(AssetsType)表服务接口
 *
 * @author makejava
 * @since 2020-10-13 11:00:51
 */
public interface AssetsTypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AssetsType queryById(Long id);

    /**
     * 判断资产类型表是否存在数据
     *
     * @return 1:true, null/0false
     */
    Boolean hasData();

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AssetsType> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param assetsType 实例对象
     * @return 实例对象
     */
    AssetsType insert(AssetsType assetsType);

    /**
     * 修改数据
     *
     * @param assetsType 实例对象
     * @return 实例对象
     */
    AssetsType update(AssetsType assetsType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
