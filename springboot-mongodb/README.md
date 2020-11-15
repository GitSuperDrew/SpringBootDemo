
## Spring Boot 整合 MongoDB
> 不积硅步何以成千里

### MongoDB 相关知识准备
1. MongoDB 学习官方地址：[MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows-unattended/)
2. MongoDB 的连接地址URI：`mongodb://用户名:密码@IP:PORT/DATABASE`
3. 此项目演示DEMO，需要提前建立好MongoDB数据库 `goods` 以及集合（表） `test_goods`；
    > 如果不存在表，将会以类名自动创建集合；（例如 类为 GoodsEntity.java, 没有配置好@Document(value="test_goods")；则会自动创建goodsEntity为集合。）

博客学习：[今日头条](https://www.toutiao.com/a6657007220812677644/)
示例学习2：[SpringBoot-MongoDB](https://www.toutiao.com/i6894995575263986179/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1605371525&app=news_article&utm_source=weixin&utm_medium=toutiao_android&use_new_style=1&req_id=202011150032040101450260141198536F&group_id=6894995575263986179)

### 一、MongoDB实现商品的CRUD
1.引入MongoDB的依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

2.添加相关配置
```properties
# 应用名称
spring.application.name=springboot-mongodb
# 应用服务 WEB 访问端口
server.port=8080
spring.data.mongodb.uri=mongodb://localhost:27017
```
    
3.实现步骤以及测试
1. 定义和数据库中集合对应的实体类：`GoodsEntity.java`;
    ```java
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
    ```
2. 准备好工具类 `MongoDbUtils.java`;
    ```java
    public class MongoDbUtils {
        private static final String[] FBS_ARR = {"\\", "$", "(", ")", "*", ".", "+", "[", "]", "?", "^", "{", "}", "|"};
    
        /**
         * regex对输入特殊字符转义
         *
         * @param keyWord 输入字符
         * @return 转义后的输入字符
         */
        public static String escapeExprSpecialWord(String keyWord) {
            if (!StringUtils.isEmpty(keyWord)) {
                for (String key : FBS_ARR) {
                    if (keyWord.contains(key)) {
                        keyWord = keyWord.replace(key, "\\" + key);
                    }
                }
            }
            return keyWord;
        }
    }
    ```
3. 编写DAO：`dao/GoodsDao.java`;
    ```java
    import com.mongodb.client.result.DeleteResult;
    import com.mongodb.client.result.UpdateResult;
    import com.study.module.springbootmongodb.entity.GoodsEntity;
    import com.study.module.springbootmongodb.utils.MongoDbUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Sort;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.data.mongodb.core.query.Update;
    import org.springframework.stereotype.Component;
    import org.springframework.util.StringUtils;
    
    import java.util.List;
    import java.util.regex.Pattern;
    
    /**
     * @author Administrator
     * @date 2020/11/14 下午 10:32
     */
    @Component
    public class GoodsDao {
        @Autowired
        private MongoTemplate mongoTemplate;
    
        public GoodsEntity add(GoodsEntity entity) {
            return mongoTemplate.save(entity);
        }
    
        /**
         * 根据ID，修改对应的文档信息
         *
         * @param entity 商品文档信息类
         * @return 修改结果类
         */
        public UpdateResult upd(GoodsEntity entity) {
            Query query = new Query(Criteria.where("id").is(entity.getId()));
            Update update = new Update().set("goodsName", entity.getGoodsName())
                    .set("categoryId", entity.getCategoryId())
                    .set("goodsStatus", entity.getGoodsStatus())
                    .set("labels", entity.getLabels());
            return mongoTemplate.updateFirst(query, update, GoodsEntity.class);
        }
    
        /**
         * 删除指定的文档
         *
         * @param id 商品文档记录ID
         * @return 删除结果类
         */
        public DeleteResult delById(long id) {
            Query query = new Query(Criteria.where("id").is(id));
            return mongoTemplate.remove(query, GoodsEntity.class);
        }
    
        /**
         * 删除指定文档
         *
         * @param id ObjectId MongoDB自动生成的唯一标识ID
         * @return 是否成功 true成功，false失败
         */
        public boolean delByObjectId(String id) {
            Query query = new Query(Criteria.where("id").is(id));
            GoodsEntity entity = mongoTemplate.findOne(query, GoodsEntity.class);
            assert entity != null;
            DeleteResult deleteResult = mongoTemplate.remove(entity);
            if (deleteResult.getDeletedCount() > 0) {
                return true;
            }
            return false;
        }
    
        /**
         * 查看商品详情
         *
         * @param id 商品文档记录ID
         * @return 商品记录信息
         */
        public GoodsEntity getById(long id) {
            Query query = new Query(Criteria.where("id").is(id));
            GoodsEntity entity = mongoTemplate.findOne(query, GoodsEntity.class);
            return entity;
        }
    
        /**
         * 根据ObjectId 得到详情
         *
         * @param id ObjectId记录ID
         * @return 商品记录信息
         */
        public GoodsEntity getByObjectId(String id) {
            Query query = new Query(Criteria.where("id").is(id));
            GoodsEntity entity = mongoTemplate.findOne(query, GoodsEntity.class);
            return entity;
        }
    
        /**
         * 得到所有的商品记录信息
         *
         * @return 所有的商品
         */
        public List<GoodsEntity> listAll() {
            List<GoodsEntity> entities = mongoTemplate.find(new Query(), GoodsEntity.class);
            return entities;
        }
    
        /**
         * 分页某字段使用正则表达式模糊查询，且显示分页，ID倒叙
         *
         * @param label      某个字段
         * @param pageNumber 当前页码
         * @param pageSize   显示条目数
         * @return 分页信息集合
         */
        public List<GoodsEntity> queryPageByLabel(String label, int pageNumber, int pageSize) {
            // 完全匹配
            // Pattern pattern = Pattern.compile("^" + label + "$", Pattern.CASE_INSENSITIVE);
            // 右匹配
            // Pattern pattern = Pattern.compile("^.*" + label + "$", Pattern.CASE_INSENSITIVE);
            // 左匹配
            // Pattern pattern = Pattern.compile("^" + label + ".*$", Pattern.CASE_INSENSITIVE);
            // 模糊匹配
            Pattern pattern = Pattern.compile("^.*" + MongoDbUtils.escapeExprSpecialWord(label) + ".*$", Pattern.CASE_INSENSITIVE);
            Query query = new Query(Criteria.where("labels").regex(pattern));
            // ID倒序
            query.with(Sort.by("id").descending());
            // 分页
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            query.with(pageRequest);
            return mongoTemplate.find(query, GoodsEntity.class);
        }
    
        /**
         * 多查询条件，分页，ID倒序
         *
         * @param entity     商品相关字段都可以输入作为筛选参数
         * @param pageNumber 当前页码
         * @param pageSize   显示条目数
         * @return 分页集合商品信息
         */
        public List<GoodsEntity> queryPage(GoodsEntity entity, int pageNumber, int pageSize) {
            Criteria criteria = new Criteria();
            if (!StringUtils.isEmpty(entity.getGoodsName())) {
                Pattern pattern = Pattern.compile("^.*" + entity.getGoodsName() + ".*$", Pattern.CASE_INSENSITIVE);
                criteria.and("goodsName").regex(pattern);
            }
            if (!StringUtils.isEmpty(entity.getLabels())) {
                Pattern pattern = Pattern.compile("^.*" + entity.getLabels() + ".*$", Pattern.CASE_INSENSITIVE);
                criteria.and("labels").regex(pattern);
            }
            if (entity.getCategoryId() > 0) {
                criteria.and("categoryId").is(entity.getCategoryId());
            }
            if (entity.getGoodsStatus() > 0) {
                criteria.and("goodsStatus").is(entity.getGoodsStatus());
            }
    
            Query query = new Query(criteria);
            // 分页 & ID倒序
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
            query.with(pageRequest);
            return mongoTemplate.find(query, GoodsEntity.class);
        }
    }
    
    ```
4. 添加测试工具类：`SpringbootMongodbApplicationTests.java`;


### 二、MongoDB实现书籍的CRUD
1. 实体类：`Book.java` 和 `Emp.java` ;
    ```java    
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
    
    ```
2. Service层：`BookMongoDbService.java`
    ```java    
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
    
    ```
3. 控制层：`BookController.java` ;
    ```java
    import com.mongodb.client.result.UpdateResult;
    import com.study.module.springbootmongodb.dao.GoodsDao;
    import com.study.module.springbootmongodb.entity.GoodsEntity;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.util.ObjectUtils;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    /**
     * 商品相关控制层
     *
     * @author Administrator
     * @date 2020/11/15 上午 11:58
     */
    @RestController
    @RequestMapping(value = "/goods")
    public class GoodsController {
        @Autowired
        private GoodsDao goodsDao;
    
        /**
         * 查询所有商品
         *
         * @return 商品集合
         */
        @GetMapping(value = "/mongo/findAll")
        public List<GoodsEntity> findAll() {
            return goodsDao.listAll();
        }
    
        /**
         * 根据ObjectId，得到商品详情
         *
         * @param id ObjectId(MongoDB自动生成的ID)
         * @return 商品详情
         */
        @GetMapping(value = "/mongo/findOne")
        public GoodsEntity findOne(@RequestParam String id) {
            return goodsDao.getByObjectId(id);
        }
    
        /**
         * 根据标签模糊分页查询
         *
         * @param label      商品标签
         * @param pageNumber 页码
         * @param pageSize   数目
         * @return 商品集合
         */
        @GetMapping(value = "/mongo/pageLike")
        public List<GoodsEntity> pageLike(@RequestParam(value = "label", required = false) String label,
                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
            return goodsDao.queryPageByLabel(label, pageNumber, pageSize);
        }
    
        /**
         * 添加商品
         *
         * @param goodsEntity 商品信息
         * @return 是否成功
         */
        @PostMapping(value = "/mongo/saveOne")
        public String saveOne(@RequestBody GoodsEntity goodsEntity) {
            GoodsEntity entity = goodsDao.add(goodsEntity);
            if (!ObjectUtils.isEmpty(entity)) {
                return "success";
            } else {
                return "fail";
            }
        }
    
        /**
         * 更新商品
         *
         * @param entity 商品信息
         * @return
         */
        @PutMapping(value = "/mongo/update")
        public String update(@RequestBody GoodsEntity entity) {
            UpdateResult result = goodsDao.upd(entity);
            if (!ObjectUtils.isEmpty(result)) {
                return "success";
            } else {
                return "fail";
            }
        }
    
        /**
         * 删除指定的商品
         *
         * @param objectId 商品ID
         * @return 是否成功
         */
        @DeleteMapping(value = "/mongo/delete")
        public String delete(@RequestParam(value = "id") String objectId) {
            if (goodsDao.delByObjectId(objectId)) {
                return "success";
            } else {
                return "fail";
            }
        }
    
    }
    
    ```
4. 利用 `Postman` 进行CRUD测试
   - 查看所有书籍：[http://localhost:8080/book/mongo/findAll](http://localhost:8080/book/mongo/findAll)
   - 查看书籍详情：[http://localhost:8080/book/mongo/findOne?id=5fb07a0ffdad9434c0e2f48c](http://localhost:8080/book/mongo/findOne?id=5fb07a0ffdad9434c0e2f48c)
   - 等等……
   
### 三、MongoDB类比JPA/MP，优化Service/DAO层实现。
> 我们看到上面的mongoDB的CRUD操作，是使用了 MongoTemplate对象的方法，里面有很多的CRUD方法，但是在使用中
> 会发现一个问题，加入要 **对数据库操作多个对象，那岂不是每个对象的service都需要写一套CRUD方法？**，所以，那
> 能不能像 MySQL操作的 mybatis-plus的操作方式，可以很方便，快捷的操作 MongoDB数据库呢？

知识来源：[老顾聊技术](https://www.toutiao.com/i6894995575263986179/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1605371525&app=news_article&utm_source=weixin&utm_medium=toutiao_android&use_new_style=1&req_id=202011150032040101450260141198536F&group_id=6894995575263986179)
1. 定义MongoDbDao抽象类
    ```java
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
    
    ```
2. 实现层 `MongoDbService.java` 如下
    ```java
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
    
    ```
3. 不同实体类的实现层就大大的简化了代码：如下
    ```java
    import com.study.module.springbootmongodb.entity.Book;
    import org.springframework.stereotype.Service;
    
    @Service
    public class BookMongoDbPlusService extends MongoDbService<Book>{
    }
    ```
4. 升级版控制层：
    ```java
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
    ```

---

### 附录
#### 部分MongoDB的脚本
```shell script
// 切换数据库
> use runoob;
// 显示所有的集合（表）
> show tables;
// 查看集合 demoCol
> db.demoCol.find();
// 查看title为“NoSQL Overview”的文档记录（精确匹配）
> db.demoCol.find({title: "NoSQL Overview"});
// 显示不同作者的文章数量
> db.demoCol.aggregate([{$group: {"_id": "$by_user", num_tutorial: {$sum: 1}}}]);

```
