package com.study.module.springbootmongodb.dao;

import com.study.module.springbootmongodb.entity.Book;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @date 2020/11/15 下午 2:59
 */
@Repository
public class BookMongoDbDao extends MongoDbDao<Book>{
    /**
     * 反射获取泛型类型
     *
     * @return
     */
    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }
}
