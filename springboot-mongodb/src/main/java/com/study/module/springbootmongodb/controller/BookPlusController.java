package com.study.module.springbootmongodb.controller;

import com.study.module.springbootmongodb.entity.Book;
import com.study.module.springbootmongodb.service.BookMongoDbPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/11/15 下午 3:02
 */
@RestController
@RequestMapping(value = "/book-plus")
public class BookPlusController {
    @Autowired
    private BookMongoDbPlusService bookMongoDbPlusService;

    /**
     * 添加书籍
     *
     * @param book 书籍信息
     */
    @PostMapping(value = "/plus/mongo/save")
    public String saveObj(@RequestBody Book book) {
        try {
            bookMongoDbPlusService.save(book);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    // 查询单个，查询所有，修改所有，修改单个，修改第一个，删除单个，删除匹配的，分页查询……请自行动手实践
}
