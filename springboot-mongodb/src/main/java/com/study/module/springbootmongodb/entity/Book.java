package com.study.module.springbootmongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 书籍类
 *
 * @author Administrator
 * @date 2020/11/15 上午 10:52
 */
@Data
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 书名
     */
    private String name;
    /**
     * 简介
     */
    private String info;
    /**
     * 出版社
     */
    private String publish;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
