package com.study.module.springbootmongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户类
 *
 * @author Administrator
 * @date 2020/11/15 上午 10:55
 */
@Data
@Document(collection = "emp")
public class Emp {
    /**
     * 主键ObjectId
     */
    @Id
    private String id;
    /**
     * 雇佣名
     */
    private String name;
}
