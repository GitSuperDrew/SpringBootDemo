# SpringBoot基于Mybatis学习笔记
## 一、 SpringBoot 整合 Mybatis 框架
> 引入：学无止尽，精益求精。
### 1，引入相关依赖
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
```

### 2，配置application.properties文件
```properties
# 应用名称
spring.application.name=springboot-mybatis
# 应用服务 WEB 访问端口
server.port=8080
# spring 静态资源扫描路径
spring.resources.static-locations=classpath:/static/
# 数据库驱动：
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据源名称
spring.datasource.name=defaultDataSource
# 数据库连接地址
spring.datasource.url=jdbc:mysql://localhost:3306/zero?serverTimezone=UTC
# 数据库用户名&密码：
spring.datasource.username=root
spring.datasource.password=root123456
# 访问template下的html文件需要配置模板
spring.thymeleaf.prefix.classpath=classpath:/templates/
# 是否启用缓存
spring.thymeleaf.cache=false
# 模板文件后缀
spring.thymeleaf.suffix=.html
# 模板文件编码
spring.thymeleaf.encoding=UTF-8

## 重点配置 SpringBoot Mybatis
mybatis.type-aliases-package=com.student.module.springbootmybatis.entity
mybatis.mapper-locations=classpath:/mapper/*Dao.xml
#开启 mybatis 查询日志
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

### 3，利用IDEA插件EasyCode，生成mybatis相关接口文档
> 安利一个mybatis快捷生成插件：easycode  （idea）
使用教程请 😀[百度搜索](http://www.baidu.com)

注意：
1. 需要注意配置 SpringBootApplication启动类；
    ```java
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
     //...
    }
    ```
2. 需要在生成的 `*Dao.xml` 上加入注解 `@Repository` 或者 `@Mapper` ；
3. 配置spingboot-mybatis相关的属性
    ```properties
    ## 重点配置 SpringBoot Mybatis
    mybatis.type-aliases-package=com.student.module.springbootmybatis.entity
    mybatis.mapper-locations=classpath:/mapper/*Dao.xml
    #开启 mybatis 查询日志
    mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
    ```
4. 由于初始化项目的时候引入一些很多的依赖包（lombok、rest docs、thymeleaf、validation等），
例如 `Lombok` 工具，你可以优化下 `entity` 包下的DO;利用`Lombok`提供的注解，简化实体类DO相关的文件的内容。

### 4，Mybatis类型处理器
> 下面以处理 性别的类型处理器为例子
1. 新建一个枚举类 `SexEnum.java`;
2. 为了实力类与之前的不冲突，新建一个新的DO：`Teacher.java`;
3. `application.properties` 添加相关配置；
    ```properties
    #配置 typeHandler 的扫描包
    mybatis.type-handlers-package=com.study.module.springbootmybatis.handler
    ```
4. 新建类型处理器 `SexTypeHandler.java` ；
5. 对实体类`TeacherDO.java`可以添加注解`@Alias` 方便 `*Mapper.xml` 中使用（可选）；
6. `TeacherController.java`提供一个新的测试接口`getByIdLogic(...)`。

### 5. 参数验证
1. 添加依赖；
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    ``` 
2. 安利：IDEA`插件AnyRule`；（用于快速得到正则表达式的插件）
3. 对 DTO 或 VO 进行中的每个需要验证的属性加入对应的验证注解（spring-validation），例如：`TeacherDO.java`
4. controller 层需要在保存或者修改的操作时，需要写成 `@Valid Teacher teacher` 
> 相关博客学习推荐：[验证注解@Pattern](https://blog.csdn.net/qq_36927265/article/details/87864026)
> 


## 附件
