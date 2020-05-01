# 项目架构

## 后端部分
1. 整体架构：`Spring Boot`
2. ORM框架：`Mybatis`
3. 数据库：`MySQL`
## 前端部分
1. 模板引擎：`Freemarker`
2. DataTable：AJAX像后端发送请求的时候，会产生的参数见连接[DataTables：Sent Paramters](https://datatables.net/manual/server-side)
## 接口文档
1. REST ful 接口文档：`SwaggerUI2`
> 需要符合rest ful请求规范：POST、GET、DELETE、PUT
## 其他插件
1. 实体类代码简化JAR：`Lombok`
2. 连接池：`Druid`
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