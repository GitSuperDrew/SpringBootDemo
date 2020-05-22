## Spring Cloud OAuth2

### 构建 `Uaa` 授权服务
1. 引入依赖
    ```xml
    <parent>
            <groupId>org.example</groupId>
            <artifactId>oauth2</artifactId>
            <version>1.0-SNAPSHOT</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
        <groupId>com.example</groupId>
        <artifactId>auth-service</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <name>auth-service</name>
        <description>Uaa 授权服务</description>
    
        <properties>
            <java.version>1.8</java.version>
        </properties>
    
        <dependencies>
            <!--spring data jpa-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-jpa</artifactId>
            </dependency>
            <!--spring security-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-security</artifactId>
                <version>2.0.0.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.security.oauth.boot</groupId>
                <artifactId>spring-security-oauth2-autoconfigure</artifactId>
                <version>2.0.0.RELEASE</version>
            </dependency>
            <!---->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-eureka</artifactId>
                <version>1.4.7.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <!--mysql connector-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <scope>runtime</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
                <exclusions>
                    <exclusion>
                        <groupId>org.junit.vintage</groupId>
                        <artifactId>junit-vintage-engine</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>org.springframework.security</groupId>
                <artifactId>spring-security-test</artifactId>
                <scope>test</scope>
            </dependency>
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    ```
2. 配置文件 `application.yaml`
    ```yaml
    server:
      port: 5000
      servlet:
        context-path: /uaa
    spring:
      application:
        name: service-auth
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/spring-cloud-auth?useUnicode=true?characterEncoding=utf8&characterSetResults=utf8&serverTimeZone=GMT%2B8
        username: root
        password: root123456
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
    eureka:
      client:
        service-url:
          defaultZone: http://localhost:8761/eureka/
    
    ```
3. 配置 `Spring Security`
4. 配置 `Authorization Server`
5. 暴露 `Remote Token Services` 接口
6. 获取 `Token`
    启动 `eureka-server` 和 `auth-service` 服务，在终端上用 `curl` 命令模拟请求获取`Token`， `Curl` 命令如下：
    ```shell script
    curl -i -X POST -d "username=drew&password=123456&grant_type=password&client_id=service-hi&client_secret=123456" http://localhost:5000/uaa/oauth/token
    ```
7. 测试：
    * 如果出现错误信息，请检查数据库中，user 表和role 表是否存在对应的信息。如果存在，请执行下面初始化脚本
        ```shell script
        insert into `user` (id,username ,password ) values(1, 'drew', '123456');
        insert into `user` (id,username ,password ) values(2,'admin', '123456');
        insert into `role` (id, name ) values(1, 'ROLE_USER');
        insert into `role` (id, name ) values(2, 'ROLE_ADMIN');
        insert into user_role (user_id , role_id ) values (1, 1);
        insert into user_role (user_id , role_id ) values (2, 1);
        insert into user_role (user_id , role_id ) values (2, 2);
        ```
     * 查看注册中心是否成功启动：请访问[http://localhost:8761/](http://localhost:8761/)
### 构建