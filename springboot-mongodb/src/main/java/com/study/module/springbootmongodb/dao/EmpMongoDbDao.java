package com.study.module.springbootmongodb.dao;

import com.study.module.springbootmongodb.entity.Emp;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @date 2020/11/15 下午 3:00
 */
@Repository
public class EmpMongoDbDao extends MongoDbDao<Emp> {
    /**
     * 反射获取泛型类型
     *
     * @return
     */
    @Override
    protected Class<Emp> getEntityClass() {
        return Emp.class;
    }
}
