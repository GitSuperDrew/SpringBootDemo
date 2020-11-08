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

### 6. 配置 API接口文档 `Swagger3.0`
> 相关博客学习文档：[知乎](https://zhuanlan.zhihu.com/p/161947638)
1. 引入 swagger3.0 的依赖, 并移除 `Swagger2.x`相关依赖；
    ```xml
    <dependency>
         <groupId>io.springfox</groupId>
          <artifactId>springfox-boot-starter</artifactId>
          <version>3.0.0</version>
    </dependency>
    ```
2. Application上面加入`@EnableOpenApi`注解；
    ```java
    @EnableOpenApi // 重点注解
    @SpringBootApplication
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
        //...
    }
    ```
3. Swagger3Config的配置；(将 swagger2.x 的配置文件调整为 swagger3.0 的相关配置)
    ```java
    @Configuration
    public class Swagger3Config {
        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build();
        }
    
        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Swagger3接口文档")
                    .description("更多请咨询服务开发者Ray。")
                    .contact(new Contact("Ray。", "http://www.ruiyeclub.cn", "ruiyeclub@foxmail.com"))
                    .version("1.0")
                    .build();
        }
    }
    ```
4. 注解使用说明：见[附件](#附件)
5. 访问接口文档的地址从 swagger2.x的`port/swagger-ui.html` 变成了 swagger3.0的`port/swagger-ui/index.html`或者`port/swagger-ui/`;

### 7. Swagger3.0整合 Knife4j 美化接口文档
> 官方地址：[Knife4j](https://gitee.com/xiaoym/knife4j)
1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <!-- 由于我这里时swagger3.0，所以引入了3.0.X的版本-->
        <version>3.0.1</version>
    </dependency>
    ```
2. Application添加注解 `@EnableKnife4j`
    ```java
    @EnableOpenApi
    @EnableKnife4j  // 关键注解
    @SpringBootApplication
    @MapperScan(value = {"com.study.module.springbootmybatis.dao"})
    public class SpringbootMybatisApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringbootMybatisApplication.class, args);
        }
    
    }
    ```
3. 访问地址：
    - swagger3访问地址依赖保留了，（`port/swagger-ui/`）
    - Knife4j-swagger3 访问地址：`http://host:port/doc.html#/plus`;

### 8. `mybatis-pageHelper` 分页插件
1. 引入依赖：
    ```xml
    <!--mybatis-pagehelper 分页插件
        官方地址：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/en/HowToUse.md
    -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.3.0</version>
    </dependency>
    ```
2. 新建接口用于测试（`TeacherController.page`）；
3. 提供了两个分页实体类（`PageVO.java`, `PageForm.java`）；
4. 引入了 `Fastjson` 依赖包；
    ```xml
    <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.73</version>
    </dependency>
    ```
5. 引入了分页插件 `PageHelper` 依赖包；
    ```xml
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.3.0</version>
    </dependency>
    ```


## 附件
### 1. Swagger2.x 的相关注解说明：
```text
 常用注解：
 - @Api()用于类；
        表示标识这个类是swagger的资源
 - @ApiOperation()用于方法；
        表示一个http请求的操作
 - @ApiParam()用于方法，参数，字段说明；
        表示对参数的添加元数据（说明或是否必填等）
 - @ApiModel()用于类
        表示对类进行说明，用于参数用实体类接收
 - @ApiModelProperty()用于方法，字段
        表示对model属性的说明或者数据操作更改
 - @ApiIgnore()用于类，方法，方法参数
        表示这个方法或者类被忽略
 - @ApiImplicitParam() 用于方法
        表示单独的请求参数
 - @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
 <p>
```

### 2. Swagger3.0 的相关注解说明：
```text
@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"

@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · div（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值

@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类

@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
```

### 3. Kinfe4j 使用官方文档：
1. 官方文档地址: [https://doc.xiaominfo.com/knife4j/autoEnableKnife4j.html](https://doc.xiaominfo.com/knife4j/autoEnableKnife4j.html)
2. Giteed地址: [https://gitee.com/xiaoym/knife4j](https://gitee.com/xiaoym/knife4j)
