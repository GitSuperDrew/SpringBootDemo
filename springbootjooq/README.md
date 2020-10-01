## 搭建DEMO步骤
1. 初始化 `spring boot - maven` 项目；核心引入依赖
    ```xml
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-jooq</artifactId>
            </dependency>
            <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <scope>runtime</scope>
            </dependency>
    ```
2. 添加 `jooq` 代码生成器相关的插件
    ```xml
        <!--Jooq maven plugin-->
        <plugin>
            <groupId>org.jooq</groupId>
            <artifactId>jooq-codegen-maven</artifactId>
            <version>3.13.4</version>
            <executions>
                <execution>
                    <id>default</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        <jdbc>
                            <driver>com.mysql.jdbc.Driver</driver>
                            <url>jdbc:mysql://127.0.0.1:3306/zero?serverTimezone=UTC</url>
                            <user>root</user>
                            <password>root123456</password>
                        </jdbc>
                        <!-- 代码生成器 -->
                        <generator>
                            <name>org.jooq.codegen.JavaGenerator</name>
                            <generate>
                                <instanceFields>true</instanceFields>
                                <!--生成dao和pojo-->
                                <pojos>true</pojos>
                                <daos>true</daos>
                                <!--把数据库时间类型映射到java 8时间类型-->
                                <javaTimeTypes>true</javaTimeTypes>
                                <!--<interfaces>true</interfaces>-->
                                <!--不在生成的代码中添加spring注释，比如@Repository-->
                                <springAnnotations>false</springAnnotations>
                            </generate>
                            <database>
                                <!--数据库名称-->
                                <inputSchema>zero</inputSchema>
                                <name>org.jooq.meta.mysql.MySQLDatabase</name>
                                <!--include和exclude用于控制为数据库中哪些表生成代码-->
                                <includes>.*</includes>
                                <!--<excludes></excludes> 排除指定的数据库表-->
                                <excludes>schema_version</excludes>
                                <!--主键生成策略-->
                                <syntheticPrimaryKeys>public\..*\.id</syntheticPrimaryKeys>
                                <overridePrimaryKeys>override_primmary_key</overridePrimaryKeys>
                            </database>
                            <target>
                                <!--生成代码文件的包名及放置目录-->
                                <packageName>com.study.module.springbootjooq.generate</packageName>
                                <directory>src/main/java</directory>
                                <!--<directory>target/generated-sources/jooq</directory>-->
                            </target>
                        </generator>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    ```
3. 在 IDEA 的 MAVEN → Lifecycle:compile 命令，得到如下结果，将自动生成 `pojos` 和 `daos`成功。
    ```text
    ...
    [INFO] Skipping empty indexes   
    [INFO] Generating table records 
    [INFO] Generating record        : PdmanDbVersionRecord.java
    [INFO] Generating record        : PoetRecord.java
    [INFO] Generating record        : PoetryRecord.java
    [INFO] Generating record        : StudentRecord.java
    [INFO] Generating record        : TSalaryRecord.java
    [INFO] Generating record        : UserRecord.java
    [INFO] Generating record        : WebLogRecord.java
    [INFO] Table records generated  : Total: 1.436s, +23.348ms
    [INFO] Domains fetched          : 0 (0 included, 0 excluded)
    [INFO] Generating routines and table-valued functions
    [INFO] Generating routine       : ShowStuName.java
    [INFO] Generating routine       : ShowStudent.java
    [INFO] Routines generated       : Total: 2.138s, +701.064ms
    [INFO] Generation finished: zero: Total: 2.138s, +0.136ms
    [INFO]                          
    [INFO] Removing excess files    
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ springbootjooq ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 1 resource
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ springbootjooq ---
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 35 source files to E:\GitHub-Local-Repo\SpringBootDemo\springbootjooq\target\classes
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 8.875 s
    [INFO] Finished at: 2020-10-01T07:46:05+08:00
    [INFO] ------------------------------------------------------------------------
    ```
4. 配置`jooq`的内容到此结束，*如果想自己测试下，请继续往下看看
5. 配置下 `application.properties`（你如果想使用 .yml 格式的文件，可以自行转换）
    ```yaml
    # 应用名称
    spring.application.name=springbootjooq
    
    # 应用服务 WEB 访问端口
    server.port=8080
    # spring 静态资源扫描路径
    spring.resources.static_locations=classpath:/static/
    
    # 数据库驱动：
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    # 数据源名称
    spring.datasource.name=zero
    # 数据库连接地址
    spring.datasource.url=jdbc:mysql://localhost:3306/zero?serverTimezone=UTC
    # 数据库用户名&密码：
    spring.datasource.username=root
    spring.datasource.password=root123456
    ```
6. 新建 `service` 和 `controller` 对应的文件;（如示例文件 `service/UserService` 和 `controller/UserController`）
7. 测试
    - 开启服务成功标识：`Started SpringbootjooqApplication in 2.617 seconds (JVM running for 5.399)`
    - 浏览器或postman测试：
        + [获取单个用户详情](http://localhost:8080/springbootjooq/user/1)
        + [获取用户列表](http://localhost:8080/springbootjooq/user/list)
    - 对应结果：
        + 单个用户详情：`{"id":null,"name":"Jone","age":18,"email":"test1@baomidou.com","removeTag":null}`
        + 用户列表：`[{"id":null,"name":"Eagle","age":24,"email":"test22@baomidou.com","removeTag":null},{"id":null,"name":"Shark","age":24,"email":"test21@baomidou.com","removeTag":null}]`


## `Jooq` 介绍
1. 一种新的 ORM框架（运用了 DSL 技术），更像是一种介于 ORM 和 JDBC 的中间层；
2. `Jooq` 代码层面要比 `Mybatis` 简洁，性能也不错；
3. 和 `Mybatis Plus` 有的一比。^_^

### jooq 和 jdbc 比较
> *官方手册中，将其和 JPA、 LINGQ、JDBC 做了比较；
1. JOOQ 写 DML/DDL 语句就像是直接写 Java 代码，非常方便；
2. 不需要拼接各种字符串来拼接SQL语句，且同样支持字符串SQL拼接；
3. 不用去写各种 ResultSet、PrepareStatement 有关的代码；
4. 支持插件自动生产实体类代码；（本实例就是通过配置jooq-codegen插件自动生成的 pojos 和 daos）
5. 支持事务，支持常用的数据库（例如 MySQL，Oracle，H2 等）





## 遇到的问题
1. 没有指定<intputSchema> （即知道哪个数据库），将出现内存溢出。
    ```text
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 7738 source files to E:\GitHub-Local-Repo\SpringBootDemo\springbootjooq\target\classes
    系统资源不足。
    有关详细信息, 请参阅以下堆栈跟踪。
    java.lang.OutOfMemoryError: Java heap space
        at com.sun.tools.javac.util.Position$LineMapImpl.build(Position.java:153)
    ```
2. 在 `pom.xml` 插件 `plugin` 配置中，尽量详细。
    - 指明数据库、主键生成策略、排除哪些数据表不需要生成、指定哪些数据表需要生成。
    - 考虑好 `jooq` 不同版本的不同生成器规则；
