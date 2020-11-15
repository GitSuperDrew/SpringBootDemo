package com.study.module.springbootmongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 集合对应实体类（类似于关系型数据库的表对应于实体类）
 * <pre>
 *     注意：@Document(collection = "test_goods")一定需要明显标注，如果不标注，则会默认对应集合为 goodsEntity
 * </pre>
 *
 * @author Administrator
 * @date 2020/11/14 下午 10:24
 */
@Data
@Document(collection = "test_goods")  // 注意：此处是 collection 而不是 collation
public class GoodsEntity implements Serializable {
    /**
     * MongoDB 的 _id 字段由 12个字节的BSON类型字符串组成（4字节：UNIX时间戳，3字节：MongoDB服务器，2字节是生成ID的进程，3字节是随机数）
     * 这么做的好处就是，对分布式友好，id中包含时间戳，带上了创建时间。可以通过
     * <pre>
     *     ObjectId id = new ObjectId(entity.getId());
     *     System.out.println(id.getDate()); // 得到时间戳
     * </pre>
     */
    @Id
    private String id;
    /**
     * 商品名称
     */
    @Field(value = "goodsName")
    private String goodsName;
    /**
     * 仓库ID
     */
    @Field(value = "categoryId")
    private long categoryId;
    /**
     * 商品状态
     */
    @Field(value = "goodsStatus")
    private int goodsStatus;
    /**
     * 商品标签
     */
    @Field(value = "labels")
    private String labels;
}
