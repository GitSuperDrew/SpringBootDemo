
## Spring Boot 整合 MongoDB
> 不积硅步何以成千里

### MongoDB 相关知识准备
1. MongoDB 学习官方地址：[MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows-unattended/)
2. MongoDB 的连接地址URI：`mongodb://用户名:密码@IP:PORT/DATABASE`
3. 此项目演示DEMO，需要提前建立好MongoDB数据库 `goods` 以及集合（表） `test_goods`；
    > 如果不存在表，将会以类名自动创建集合；（例如 类为 GoodsEntity.java, 没有配置好@Document(value="test_goods")；则会自动创建goodsEntity为集合。）

博客学习：[今日头条](https://www.toutiao.com/a6657007220812677644/)
### 1.引入MongoDB的依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### 2.添加相关配置
```properties
# 应用名称
spring.application.name=springboot-mongodb
# 应用服务 WEB 访问端口
server.port=8080

spring.data.mongodb.uri=mongodb://localhost:27017
```

### 3.实现步骤以及测试
1. 定义和数据库中集合对应的实体类：`GoodsEntity。java`；
2. 准备好工具类 `MongoDbUtils.java`;
3. 编写DAO：`dao/GoodsDao.java`
4. 添加测试工具类：`SpringbootMongodbApplication.java`

---

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
