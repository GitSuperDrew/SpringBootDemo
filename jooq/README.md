## 学习文档

1. 讲解文章：[传送门](https://www.toutiao.com/i6823589416531919374/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1601474390&app=news_article&utm_source=weixin&utm_medium=toutiao_android&use_new_style=1&req_id=202009302159500100260772110371AD5C&group_id=6823589416531919374)
    ```text
   1. 创建好spring boot - maven 构建的项目；
   2. 添加配置文件[JooqConfig.xml]并修改好导出的文件地址，引入相关依赖；
   3. IDEA 工具下，运行 lifecycle →  compile
   4. 执行成功后，会在文件目录下生成相关 `daos` 和 `pojos`;
    ```

2. 相关技术
    - 数据库 ：MySQL
    - ORM框架：JPA
    - 连接池：Druid
    - 实体简化类 ：Lombok
    - 构建方式：maven

3. 运行测试
    - 启动项目；
    - 测试：http://localhost:8080/jooq/user/1
