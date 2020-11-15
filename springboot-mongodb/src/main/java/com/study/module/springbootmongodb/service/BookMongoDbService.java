package com.study.module.springbootmongodb.service;

import com.study.module.springbootmongodb.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 书本业务
 *
 * @author Administrator
 * @date 2020/11/15 上午 10:57
 */
@Slf4j
@Service
public class BookMongoDbService {
    /**
     * MongoDB 的修改操作大致有 3 中：
     * mongoTemplate.updateFirst 操作： 修改第一条；
     * mongoTemplate.updateMulti 操作： 修改符合条件的所有记录；
     * this.mongoTemplate.upsert 操作： 修改时如果不存在则添加。
     */

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     *
     * @param book 书籍类
     * @return 是否成功
     */
    public String saveObj(Book book) {
        log.info("--------->[MongoDB save start]");
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(book);
        return "添加成功";
    }

    /**
     * 查询所有
     *
     * @return 书籍集合
     */
    public List<Book> findAll() {
        log.info("--------->[MongoDB find start]");
        return mongoTemplate.findAll(Book.class);
    }

    /**
     * 根据ID 查询
     *
     * @param id ObjectId 查询书籍详情
     * @return 书籍信息
     */
    public Book getBookById(String id) {
        log.info("-------->[MongoDB getBookById start]");
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     *
     * @param name 书名
     * @return 书本信息
     */
    public Book getBookByName(String name) {
        log.info("-------->[MongoDB getBookByName start]");
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 更新对象
     *
     * @param book 书籍
     * @return 是否成功
     */
    public String updateBook(Book book) {
        log.info("-------->[MongoDB updateBook start]");
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update()
                .set("price", book.getPrice())
                .set("publish", book.getPublish())
                .set("info", book.getInfo())
                .set("updateTime", new Date());
        // 策略一：更新查询结果，并返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        // 策略二：更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query, update, Book.class);
        // 策略三：更新对象不存在则去添加
        // mongoTemplate.upsert(query, update, Book.class);
        return "success";
    }

    /**
     * 删除对象
     *
     * @param book 书籍对象
     * @return 是否成功
     */
    public String deleteBook(Book book) {
        log.info("-------->[MongoDB deleteBook start]");
        mongoTemplate.remove(book);
        return "success";
    }

    /**
     * 根据id删除
     *
     * @param id 书籍唯一标识
     * @return 是否成功
     */
    public String deleteBookById(String id) {
        log.info("-------->[MongoDB delete start]");
        // findOne
        Book book = getBookById(id);
        // delete
        if (!ObjectUtils.isEmpty(book)){
            deleteBook(book);
            return "success";
        } else {
            return "fail";
        }
    }
}
