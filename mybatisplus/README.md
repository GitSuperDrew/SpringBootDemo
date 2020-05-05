## 🐅 项目简介
    这个项目是我学习Spring Boot系列的有一个Demo。目的当然也是为了实践。了解并逐渐深入学习相关技术。

## ⚙ 技术架构
1. 编程环境：[`Java8`](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)，`Windows10`，
[`maven`](https://mvnrepository.com/)，
[`InterlliJ IDEA`](https://www.jetbrains.com/)，
2. 数据库：`MySQL5.7+`  【后期准备加入 [`Redis`](https://redis.io/) 】
3. ORM框架：[`Mybatis Plus`](https://mp.baomidou.com/)
4. 模板引擎：[`Velocity`](http://velocity.apache.org/)
5. 安全框架：[`Security`](https://spring.io/projects/spring-security)
6. 其他工具：[`Lombok`](https://projectlombok.org/)，`SpringBoot热部署`

## 🦁 详细说明
1. 新建 `config/CodeGenerator.java` 自动代码生成工具：注意修改数据的相关连接信息
    * 数据库的连接驱动`Driver`
    * 数据库的连接信息`url`
    * 数据库的连接用户和密码（`username` / `password`）
    * 配置生成的文件存放的位置。
2. 直接运行 `config/CodeGenerator.java`，输入提示所需信息即可。
3. 在 `controller/UserController.java` 中书写一个方法 `findAll()` 得到数 没有`用户ID` 的数据集合；所以需要手动在`User.java`中手动添加
```java
private Integer id; // 如果没有，则出现 有关于 @TableId 相关的错误信息.
```

## ⛏ 注意事项：
1. `备注:` 文章参考[来源](https://www.toutiao.com/i6691534609064133132/)。