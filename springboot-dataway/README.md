# 工程简介
> Spring Boot 整合 Dataway（DataQL/Hasor）的示例工程。


# 搭建步骤
## 前言：
> 让 SpringBoot 不在需要 Controller, service, dao、mapper 层的书写。（言外之意，这些个性化的接口编写的权限交给了hasor 前端控制面的 DataQL 去自定义和修改 接口的服务逻辑）

## 一、认识 Dataway
1. Dataway 是基于 DataQL 服务聚合能力，为应用提供的一个接口配置工具。 
2. 使得使用者无需开发任何代码就配置一个满足需求的接口。整个接口配置、测试、冒烟、发布。 
3. 一站式都通过 Dataway 提供的 UI 界面完成。
4. UI 会以 Jar 包方式提供并集成到应用中并和应用共享同一个 http 端口，应用无需单独为 Dataway 开辟新的管理端口。
这种内嵌集成方式模式的优点是，可以使得大部分老项目都可以在无侵入的情况下直接应用 Dataway。进而改进老项目的迭代效率，大大减少企业项目研发成本。
5. Dataway 工具化的提供 DataQL 配置能力。这种研发模式的变革使得，相当多的需求开发场景只需要配置即可完成交付。从而避免了从数据存取到前端接口之间的一系列开发任务，例如：Mapper、BO、VO、DO、DAO、Service、Controller 统统不在需要。
6. Dataway 是 Hasor 生态中的一员，因此在  Spring 中使用 Dataway 首先要做的就是打通两个生态。根据官方文档中推荐的方式我们将 Hasor 和 Spring Boot 整合起来。
> 文章地址： https://www.hasor.net/web/extends/spring/for_boot.html


## 二、初步体验
###（1）引入核心依赖
```xml
<!-- hasor-spring 负责 Spring 和 Hasor 框架之间的整合。 -->
<dependency>
    <groupId>net.hasor</groupId>
    <artifactId>hasor-spring</artifactId>
    <version>4.1.6</version>
</dependency>

<!-- hasor-dataway 是工作在 Hasor 之上，利用 hasor-spring 我们就可以使用 dataway了。 -->
<dependency>
    <groupId>net.hasor</groupId>
    <artifactId>hasor-dataway</artifactId>
    <version>4.1.6</version>
</dependency>
```


###（2）配置 Dataway, 初始化数据表

#### ① 配置 application.properties
> 说明：dataway 会提供一个界面让我们配置接口，这一点类似 Swagger 只要jar包集成就可以实现接口配置。找到我们 springboot 项目的配置文件 application.properties
```properties
# 是否启用 Dataway 功能（必选：默认false）
HASOR_DATAQL_DATAWAY=true

# 是否开启 Dataway 后台管理界面（必选：默认false）
HASOR_DATAQL_DATAWAY_ADMIN=true

# dataway  API工作路径（可选，默认：/api/）
HASOR_DATAQL_DATAWAY_API_URL=/api/

# dataway-ui 的工作路径（可选，默认：/interface-ui/）
HASOR_DATAQL_DATAWAY_UI_URL=/interface-ui/

# SQL执行器方言设置（可选，建议设置）
HASOR_DATAQL_FX_PAGE_DIALECT=mysql
```


#### ② 创建数据表
> 说明：Dataway 需要两个数据表才能工作，下面是这两个数据表的简表语句。下面这个 SQL 可以在 dataway的依赖 jar 包中 “META-INF/hasor-framework/mysql” 目录下面找到，建表语句是用 mysql 语法写的。
```sql
CREATE TABLE `interface_info` (
`api_id`          int(11)      NOT NULL AUTO_INCREMENT   COMMENT 'ID',
`api_method`      varchar(12)  NOT NULL                  COMMENT 'HttpMethod：GET、PUT、POST',
`api_path`        varchar(512) NOT NULL                  COMMENT '拦截路径',
`api_status`      int(2)       NOT NULL                  COMMENT '状态：0草稿，1发布，2有变更，3禁用',
`api_comment`     varchar(255)     NULL                  COMMENT '注释',
`api_type`        varchar(24)  NOT NULL                  COMMENT '脚本类型：SQL、DataQL',
`api_script`      mediumtext   NOT NULL                  COMMENT '查询脚本：xxxxxxx',
`api_schema`      mediumtext       NULL                  COMMENT '接口的请求/响应数据结构',
`api_sample`      mediumtext       NULL                  COMMENT '请求/响应/请求头样本数据',
`api_option`      mediumtext       NULL                  COMMENT '扩展配置信息',
`api_create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`api_gmt_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
PRIMARY KEY (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='Dataway 中的API';

CREATE TABLE `interface_release` (
`pub_id`          int(11)      NOT NULL AUTO_INCREMENT   COMMENT 'Publish ID',
`pub_api_id`      int(11)      NOT NULL                  COMMENT '所属API ID',
`pub_method`      varchar(12)  NOT NULL                  COMMENT 'HttpMethod：GET、PUT、POST',
`pub_path`        varchar(512) NOT NULL                  COMMENT '拦截路径',
`pub_status`      int(2)       NOT NULL                  COMMENT '状态：0有效，1无效（可能被下线）',
`pub_type`        varchar(24)  NOT NULL                  COMMENT '脚本类型：SQL、DataQL',
`pub_script`      mediumtext   NOT NULL                  COMMENT '查询脚本：xxxxxxx',
`pub_script_ori`  mediumtext   NOT NULL                  COMMENT '原始查询脚本，仅当类型为SQL时不同',
`pub_schema`      mediumtext       NULL                  COMMENT '接口的请求/响应数据结构',
`pub_sample`      mediumtext       NULL                  COMMENT '请求/响应/请求头样本数据',
`pub_option`      mediumtext       NULL                  COMMENT '扩展配置信息',
`pub_release_time`datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间（下线不更新）',
PRIMARY KEY (`pub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='Dataway API 发布历史。';

create index idx_interface_release on interface_release (pub_api_id);

```

###（3）配置数据源
> 说明：
如果项目已经集成了自己的数据源，那么可以忽略这一步骤。
作为 Spring Boot 项目有着自己完善的数据库方面工具支持。我们这次采用 druid + mysql + spring-boot-starter-jdbc 的方式
#### ① 引入其他相关依赖
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.30</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### ② 增加数据源配置
```properties
# db
spring.datasource.url=jdbc:mysql://localhost:3306/example
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.type:com.alibaba.druid.pool.DruidDataSource
# druid
spring.datasource.druid.initial-size=3
spring.datasource.druid.min-idle=3
spring.datasource.druid.max-active=10
spring.datasource.druid.max-wait=60000
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=admin
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=1
```


###（4）把数据源设置到 Hasor 中
> 说明：
Spring Boot 和 Hasor 本是两个独立的容器框架，我们做整合之后为了使用 Dataway 的能力需要把 Spring 中的数据源设置到 Hasor 中。
首先新建一个 Hasor 的 模块，并且将其交给 Spring 管理。然后把数据源通过 Spring 注入进来。
Hasor 启动的时候会调用 loadModule 方法，在这里再把 DataSource 设置到 Hasor 中。

```java
@DimModule
@Component
public class ExampleModule implements SpringModule {
@Autowired
private DataSource dataSource = null;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        // .DataSource form Spring boot into Hasor
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }
}
```

###（5）在 SpringBoot 中启用 Hasor
> 说明：这一步非常简单，只需要在 Spring 启动类上增加 两个注解 即可。
```java
@EnableHasor()
@EnableHasorWeb()
@SpringBootApplication(scanBasePackages = {"net.example.hasor"})
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
```


###（6）启动应用
> 说明：应用在启动过程中会看到 Hasor Boot 的欢迎信息。
```text
 _    _                        ____              _
| |  | |                      |  _             | |
| |__| | __ _ ___  ___  _ __  | |_) | ___   ___ | |_
|  __  |/ _` / __|/ _ | '__| |  _ < / _  / _ | __|
| |  | | (_| __  (_) | |    | |_) | (_) | (_) | |_
|_|  |_|__,_|___/___/|_|    |____/ ___/ ___/ __|
```
当后面的日志中可以看到：dataway api workAt /api/ 、 dataway admin workAt /interface-ui/ 信息时，就可以确定 Dataway 的配置已经生效了。
```text
 _    _                        ____              _
| |  | |                      |  _ \            | |
| |__| | __ _ ___  ___  _ __  | |_) | ___   ___ | |_
|  __  |/ _` / __|/ _ \| '__| |  _ < / _ \ / _ \| __|
| |  | | (_| \__ \ (_) | |    | |_) | (_) | (_) | |_
|_|  |_|\__,_|___/\___/|_|    |____/ \___/ \___/ \__|

2021-10-11 12:28:35.702  INFO 1552 --- [           main] net.hasor.core.Hasor                     : runMode at Full ,runPath at D:\Workspace\GitProjects\SpringBootDemo\springboot-dataway
2021-10-11 12:28:35.705  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/core-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-core/4.1.6/hasor-core-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.706  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/dataway-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-dataway/4.1.6/hasor-dataway-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.707  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/web-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-web/4.1.6/hasor-web-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.707  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/db-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-db/4.1.6/hasor-db-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.708  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/dataql-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-dataql/4.1.6/hasor-dataql-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.708  INFO 1552 --- [           main] n.h.c.setting.StandardContextSettings    : addConfig '/META-INF/hasor-framework/dataql-fx-hconfig.xml' in 'jar:file:/D:/maven_repository/net/hasor/hasor-dataql-fx/4.1.6/hasor-dataql-fx-4.1.6.jar!/META-INF/hasor.schemas'
2021-10-11 12:28:35.737  INFO 1552 --- [           main] n.h.c.environment.AbstractEnvironment    : loadPackages = com.*, net.*, net.hasor.*, net.hasor.dataql.*, net.hasor.dataql.fx.*, net.hasor.dataway.*, net.hasor.db.*, net.hasor.web.*, org.*
2021-10-11 12:28:35.780  INFO 1552 --- [           main] n.hasor.core.context.TemplateAppContext  : loadModule class net.hasor.dataway.config.DatawayModule
2021-10-11 12:28:35.780  INFO 1552 --- [           main] net.hasor.dataway.config.DatawayModule   : dataway api workAt /api/
2021-10-11 12:28:35.780  INFO 1552 --- [           main] n.h.c.environment.AbstractEnvironment    : var -> HASOR_DATAQL_DATAWAY_API_URL = /api/.
2021-10-11 12:28:35.785  INFO 1552 --- [           main] net.hasor.dataway.config.DatawayModule   : dataway self isolation ->net.hasor.dataway.config.DatawayModule
2021-10-11 12:28:35.787  INFO 1552 --- [           main] net.hasor.dataway.config.DatawayModule   : dataway admin workAt /interface-ui/
2021-10-11 12:28:35.794  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[92d9cd1c966e451080db3f66045edc88] -> bindType 'class net.hasor.dataway.web.ApiDetailController' mappingTo: '[/interface-ui/api/api-detail]'.
2021-10-11 12:28:35.795  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[aa5555875e2044c99581d8f6386af769] -> bindType 'class net.hasor.dataway.web.ApiHistoryListController' mappingTo: '[/interface-ui/api/api-history]'.
2021-10-11 12:28:35.795  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[f6e085739ca94f608138ce08508c9628] -> bindType 'class net.hasor.dataway.web.ApiInfoController' mappingTo: '[/interface-ui/api/api-info]'.
2021-10-11 12:28:35.795  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[499cef05bd7f4ec592133d176d919089] -> bindType 'class net.hasor.dataway.web.ApiListController' mappingTo: '[/interface-ui/api/api-list]'.
2021-10-11 12:28:35.796  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[94d2a6c388074cabb44c3fd5dddfae59] -> bindType 'class net.hasor.dataway.web.ApiHistoryGetController' mappingTo: '[/interface-ui/api/get-history]'.
2021-10-11 12:28:35.798  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[6b701bc97888477da0492e1aa6889777] -> bindType 'class net.hasor.dataway.web.DisableController' mappingTo: '[/interface-ui/api/disable]'.
2021-10-11 12:28:35.798  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[53285ad24f6f4648b2f2e573fe3a0adc] -> bindType 'class net.hasor.dataway.web.SmokeController' mappingTo: '[/interface-ui/api/smoke]'.
2021-10-11 12:28:35.799  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[628678b132784efc8160c46a8665b455] -> bindType 'class net.hasor.dataway.web.SaveApiController' mappingTo: '[/interface-ui/api/save-api]'.
2021-10-11 12:28:35.799  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[5938a7dfc07f48f288ae607fc8a66074] -> bindType 'class net.hasor.dataway.web.PublishController' mappingTo: '[/interface-ui/api/publish]'.
2021-10-11 12:28:35.800  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[7c277730d1d24d1e9ef28f41cf50d00b] -> bindType 'class net.hasor.dataway.web.PerformController' mappingTo: '[/interface-ui/api/perform]'.
2021-10-11 12:28:35.800  INFO 1552 --- [           main] net.hasor.core.binder.ApiBinderWrap      : mapingTo[8c35e7df02cc45cd91b80f77f069d853] -> bindType 'class net.hasor.dataway.web.DeleteController' mappingTo: '[/interface-ui/api/delete]'.
```
### （7）访问接口管理页面进行接口配置（♥）
> 说明：在浏览器中输入 http://127.0.0.1:8080/interface-ui/ 就可以看到期待已久的界面了。


# 延伸阅读
- 知识借鉴：[让SpringBoot不再需要Controller、Service、Mapper，这款开源工具绝了！！！](https://mp.weixin.qq.com/s/UCbhmivLK2LtQY1z88KKPA)
- 官方手册：[Hasor 介绍 - Hasor - ClouGence@Hasor](https://www.hasor.net/doc/)
- Dataway 官方手册：[https://www.hasor.net/web/dataway/about.html](https://www.hasor.net/web/dataway/about.html)
- Dataway 在 OSC 上的项目地址，欢迎收藏：[https://www.oschina.net/p/dataway](https://www.oschina.net/p/dataway)
- DataQL 手册地址：[https://www.hasor.net/web/dataql/what_is_dataql.html](https://www.hasor.net/web/dataql/what_is_dataql.html)
- Hasor 项目的首页：[https://www.hasor.net/web/index.html](https://www.hasor.net/web/index.html)
