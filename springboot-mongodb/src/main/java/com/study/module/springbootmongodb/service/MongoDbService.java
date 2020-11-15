package com.study.module.springbootmongodb.service;

import com.study.module.springbootmongodb.dao.MongoDbDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/11/15 下午 2:21
 */
public abstract class MongoDbService<T> {
    @Autowired
    private MongoDbDao<T> mongoDbDao;

    /**
     * 保存一个对象
     *
     * @param t
     */
    public void save(T t) {
        this.mongoDbDao.save(t);
    }

    /**
     * 根据id从集合中查询对象
     *
     * @param id ObjectId标识
     * @return 详细
     */
    public T queryById(String id) {
        return this.mongoDbDao.queryById(id);
    }

    /**
     * 根据条件查询集合
     *
     * @param object 文档对象
     * @return 文档集合
     */
    public List<T> queryList(T object) {
        return this.mongoDbDao.queryList(object);
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param object 文档对象
     * @return 文档详情
     */
    public T queryOne(T object) {
        return this.mongoDbDao.queryOne(object);
    }

    /**
     * 根据条件分页查询
     *
     * @param object 对象
     * @param start  查询起始值
     * @param size   查询大小
     * @return 数据集合
     */
    public List<T> getPage(T object, int start, int size) {
        return this.mongoDbDao.getPage(object, start, size);
    }

    /**
     * 根据条件查询库中符合条件的记录数量
     *
     * @param object 对象
     * @return
     */
    public Long getCount(T object) {
        return this.mongoDbDao.getCount(object);
    }

    /**
     * 删除对象
     *
     * @param t 对象
     * @return
     */
    public int delete(T t) {
        return this.mongoDbDao.delete(t);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        this.mongoDbDao.deleteById(id);
    }

    /**
     * 修改匹配到的第一条记录
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateFirst(T srcObj, T targetObj) {
        this.mongoDbDao.updateFirst(srcObj, targetObj);
    }

    /**
     * 修改匹配到的所有记录
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateMulti(T srcObj, T targetObj) {
        this.mongoDbDao.updateMulti(srcObj, targetObj);
    }

    /**
     * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateInsert(T srcObj, T targetObj) {
        this.mongoDbDao.updateInsert(srcObj, targetObj);
    }
}
