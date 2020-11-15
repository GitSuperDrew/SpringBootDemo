package com.study.module.springbootmongodb.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/11/15 下午 1:53
 */
@Slf4j
public abstract class MongoDbDao<T> {
    /**
     * 反射获取泛型类型
     *
     * @return
     */
    protected abstract Class<T> getEntityClass();

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个对象
     *
     * @param t
     */
    public void save(T t) {
        this.mongoTemplate.save(t);
    }

    /**
     * 根据id从集合中查询对象
     *
     * @param id MongoDB自动生成的ObjectId
     * @return 详情
     */
    public T queryById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     *
     * @param object 实体对象
     * @return 文档集
     */
    public List<T> queryList(T object) {
        Query query = getQueryByObject(object);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param object 实体
     * @return 文档对象
     */
    public T queryOne(T object) {
        Query query = getQueryByObject(object);
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件分页查询
     *
     * @param object 实体对象
     * @param start  查询起始值
     * @param size   查询大小
     * @return 文档对象集合
     */
    public List<T> getPage(T object, int start, int size) {
        Query query = getQueryByObject(object);
        query.skip(start);
        query.limit(size);
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询库中符合条件的记录数量
     *
     * @param object 实体对象
     * @return 数量
     */
    public Long getCount(T object) {
        Query query = getQueryByObject(object);
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 删除对象
     *
     * @param t 对象
     * @return 删除的数量
     */
    public int delete(T t) {
        return (int) this.mongoTemplate.remove(t).getDeletedCount();
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        Criteria criteria = Criteria.where("_id").is(id);
        if (criteria != null) {
            Query query = new Query(criteria);
            T obj = this.mongoTemplate.findOne(query, this.getEntityClass());
            if (obj != null) {
                this.delete(obj);
            }
        }
    }

    /**
     * MongoDB 中的更新操作分为三种：
     * 1. updateFirst   修改第一条
     * 2. updateMulti   修改所有匹配的记录
     * 3. upsert        修改时如果不存在则进行添加操作
     */

    /**
     * 修改匹配到的第一条几里路
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateFirst(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的所有记录
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateMulti(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateInsert(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 将查询条件对象转换为 update
     *
     * @param object
     * @return
     */
    private Update getUpdateByObject(T object) {
        Update update = new Update();
        String[] fields = getFieldName(object);
        for (int i = 0; i < fields.length; i++) {
            String fieldName = (String) fields[i];
            Object fieldValue = getFieldValueByName(fieldName, object);
            if (fieldValue != null) {
                update.set(fieldName, fieldValue);
            }
        }
        return update;
    }

    /**
     * 将查询条件对象转换为 query
     *
     * @param object
     * @return
     */
    private Query getQueryByObject(T object) {
        Query query = new Query();
        String[] fields = getFieldName(object);
        Criteria criteria = new Criteria();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = (String) fields[i];
            Object fieldValue = getFieldValueByName(fieldName, object);
            if (fieldValue != null) {
                criteria.and(fieldName).is(fieldValue);
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 获取对象属性返回字符串数组
     *
     * @param o
     * @return
     */
    private static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[0]);
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
