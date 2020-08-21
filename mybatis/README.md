# 项目架构

## 后端部分
1. 整体架构：`Spring Boot`
2. ORM框架：`Mybatis`
3. 数据库：`MySQL`
4. 插件：
    * 分页插件：`PageHelper` 
        - [官方使用教程](https://github.com/pagehelper/pagehelper-spring-boot) ;
    * 实体类代码简化JAR：`Lombok`
5. 数据库连接池：`Druid`

## 前端部分
1. 模板引擎：`Freemarker`
2. DataTable：AJAX像后端发送请求的时候，会产生的参数见连接[DataTables：Sent Paramters](https://datatables.net/manual/server-side)

## 接口文档
1. REST ful 接口文档：`SwaggerUI2`
> 需要符合rest ful请求规范：POST、GET、DELETE、PUT

2. 美化♣ `SwaggerUI2` 接口文档: `Knife4j` 
第一步：引入依赖;
```xml
<!--整合Knife4j-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>2.0.4</version>
</dependency>
```
第二步： 在SwaggerConfiguation上添加注解 `@EnableKnife4j`
第三步： 浏览器访问 [http://localhost:8089/doc.html#/home](http://localhost:8089/doc.html#/home)
说明：这样就可以将原来swaggerui2的文档美化了，并且提供了更方便的测试环境（如：有权限验证的情况下，不需要每个接口重复添加token到每个请求上。）

## 开发工具
1.`IDE`：`IntelliJ IDEA`

<hr>

## HTTP Client API
### 用户模块
- 查询所有用户
- 根据用户ID，查询
- 根据用户ID，更新用户信息
- 删除指定用户


### 相关问题解决记录：
- 项目的热部署方案？[IDEA方案](https://blog.csdn.net/qq_16148137/article/details/99694566)
