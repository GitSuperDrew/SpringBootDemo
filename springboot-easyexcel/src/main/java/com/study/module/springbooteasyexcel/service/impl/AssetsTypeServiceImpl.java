package com.study.module.springbooteasyexcel.service.impl;

import com.study.module.springbooteasyexcel.dao.AssetsTypeDao;
import com.study.module.springbooteasyexcel.entity.AssetsType;
import com.study.module.springbooteasyexcel.service.AssetsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 固定资产分类代码（国标 GB/T 14885-2010）(AssetsType)表服务实现类
 *
 * @author makejava
 * @since 2020-10-13 11:00:52
 */
@Service("assetsTypeService")
public class AssetsTypeServiceImpl implements AssetsTypeService {
    @Resource
    private AssetsTypeDao assetsTypeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AssetsType queryById(Long id) {
        return this.assetsTypeDao.queryById(id);
    }

    /**
     * 判断资产类型表是否存在数据
     *
     * @return 1:true, null/0false
     */
    @Override
    public Boolean hasData() {
        return this.assetsTypeDao.hasData();
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AssetsType> queryAllByLimit(int offset, int limit) {
        return this.assetsTypeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param assetsType 实例对象
     * @return 实例对象
     */
    @Override
    public AssetsType insert(AssetsType assetsType) {
        this.assetsTypeDao.insert(assetsType);
        return assetsType;
    }

    /**
     * 修改数据
     *
     * @param assetsType 实例对象
     * @return 实例对象
     */
    @Override
    public AssetsType update(AssetsType assetsType) {
        this.assetsTypeDao.update(assetsType);
        return this.queryById(assetsType.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.assetsTypeDao.deleteById(id) > 0;
    }
}
