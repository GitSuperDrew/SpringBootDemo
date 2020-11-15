package com.study.module.springbootmongodb.controller;

import com.study.module.springbootmongodb.entity.Book;
import com.study.module.springbootmongodb.service.BookMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍相关控制器
 *
 * @author Administrator
 * @date 2020/11/15 上午 11:15
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookMongoDbService bookMongoDbService;

    /**
     * 保存书籍
     *
     * @param book
     * @return
     */
    @PostMapping("/mongo/save")
    public String saveObj(@RequestBody Book book) {
        return bookMongoDbService.saveObj(book);
    }

    /**
     * 查询所有
     *
     * @return 所有书籍集合
     */
    @GetMapping("/mongo/findAll")
    public List<Book> findAll() {
        return bookMongoDbService.findAll();
    }

    /**
     * 根据ID得到书籍
     *
     * @param id ObjectId 唯一标识
     * @return 单个书籍信息
     */
    @GetMapping("/mongo/findOne")
    public Book findOne(@RequestParam String id) {
        return bookMongoDbService.getBookById(id);
    }

    @GetMapping("/mongo/findOneByName")
    public Book findOneByName(@RequestParam String name) {
        return bookMongoDbService.getBookByName(name);
    }

    /**
     * 更新书籍
     *
     * @param book 书籍内容
     * @return
     */
    @PutMapping("/mongo/update")
    public String update(@RequestBody Book book) {
        return bookMongoDbService.updateBook(book);
    }

    @PostMapping("/mongo/delOne")
    public String delOne(@RequestBody Book book) {
        return bookMongoDbService.deleteBook(book);
    }

    /**
     * 根据ID删除书籍
     *
     * @param id ObjectId
     * @return 是否成功
     */
    @DeleteMapping("/mongo/delById")
    public String delById(@RequestParam String id) {
        return bookMongoDbService.deleteBookById(id);
    }
}
