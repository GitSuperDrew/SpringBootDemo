## Spring Cloud OAuth2


⚠ `service-hi` **启动报错**：`The bean 'scopedTarget.oauth2ClientContext', defined in class path resource [org
/springframework
/boot/autoconfigure/security/oauth2/client/OAuth2RestOperationsConfiguration$SessionScopedConfiguration$ClientContextConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [org/springframework/security/oauth2/config/annotation/web/configuration/OAuth2ClientConfiguration$OAuth2ClientContextConfiguration.class] and overriding is disabled.`
未解决？

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

### 构建 `service-hi` 资源服务子工程
1. 项目依赖：
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security.oauth.boot</groupId>
            <artifactId>spring-security-oauth2-autoconfigure</artifactId>
            <version>2.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
    </dependencies>
    ```
2. 配置文件 `application.yaml`
    ```yaml
    server:
      port: 8762
    eureka:
      client:
        service-url:
          defaultZone: http://localhost:8761/eureka/
    spring:
      application:
        name: service-hi
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/spring-cloud-auth?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&serverTimezone=GMT%2B8
        username: root
        password: root123456
    
    jpa:
      hibernate:
        ddl-auto: update
      show-sql: true
    
    security:
      oauth2:
        resource:
          user-info-uri: http://localhost:5000/uaa/users/current
        client:
          client-id: service-hi
          client-secret: 123456
          access-token-uri: http://localhost:5000/uaa/oauth/token
          grant-type: client_credentials,passwordp
          scope: server
    ```
3. 配置 `Resource Server`
    ```java
    @Configuration
    @EnableResourceServer
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/user/registry").permitAll()
                    .anyRequest().authenticated();
        }
    }
    ```
4. 配置 `OAuth2 Client`
    ```java
    @Configuration
    @EnableConfigurationProperties
    @EnableOAuth2Client
    public class OAuth2ClientConfig {
    
        @Bean
        @ConfigurationProperties(prefix = "security.oauth2.client")
        public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
            return new ClientCredentialsResourceDetails();
        }
    
        @Bean
        public RequestInterceptor oauth2FeignRequestInterceptor() {
            return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
        }
    
        @Bean
        public OAuth2RestTemplate clientCredentialsRestTemplate() {
            return new OAuth2RestTemplate(clientCredentialsResourceDetails());
        }
    
    }
    ```
5. 编写用户注册接口: 实体类：`User.java`、DAO：`UserDao.java`、Service：`UserService.java`、Controller：`UserController.java`
6. 测试：
    1. 通过 `curl` 命令模拟请求，调用注册的 `API` 接口，注册一个用户，`Curl` 命令如下：
        ```shell script
        curl -d "username=miya&password=123456" "localhost:8762/user/registry"
        ```
       返回注册成功，得到一串`JSON`字符串。
    2. 通过 `curl` 命令模拟请求，调用获取 `Token` 的 `API` 接口，`Curl` 命令如下：
        ```shell script
        curl -i -X POST -d "username=miya&password=123456&grant_type=password&client_id=service-hi&client_secret=123456" http://localhost:5000/uaa/oauth/token
        ```
       获取 `token` 成功，返回一串 `JSON` 字符串，可以得到 `access_token` 等数据。
    3. 通过 `curl` 命令模拟请求，访问不需要权限点的接口 “/hi”, Curl 命令如下：
        ```shell script
        curl -l -H "Authorization:Bearer bare9ed0-1970-4fd4-b616-fde2a75a41ee" -X GET "localhost:8762/hi"
        ```
       返回结果：`HELLO: , I am from port : 8762`
    4. 通过 `curl` 命令模拟请求，访问需要有 “ROLE_ADMIN” 权限点的 API 接口 “/hello”， Curl 命令如下：
        ```shell script
        curl -l -H "Authorization:Bearer bare9ed0-1970-4fd4-b616-fde2a75a41ee" -X GET "localhost:8762/hello"
        ```
        由于该用户没有“ROLE_ADMIN” 权限点，所以没有权限访问该 API 接口，返回结果为：`{"error":"access_denied", "error_description":"不允许访问"}`
    5. 在数据库中给予该用户 “ROLE_ADMIN” 权限，在数据库中执行以下脚本：
        ```shell script
        INSERT INTO 'role' VALUES('1', 'ROLE_USER'),('2','ROLE_ADMIN');
        INSERT INTO 'user_role' VALUES('4','2');
        ```
    6. 给予该用户 “ROLE_ADMIN” 权限后，重新访问`API`接口 “/hello”, `Curl` 命令如下：
        ```shell script
        curl -l -H "Authorization:Bearer bare9ed0-1970-4fd4-b616-fde2a75a41ee" -X GET "localhost:8762/hello"
        ```
       获取的返回结果：`hello you!`
7. 总结：
> 该框架存在的缺陷：每次请求都需要资源服务内部远程调度 auth-service 服务来验证 token 的正确性，以及该 token对应的用户所具有的权限，额外多了一次内部请求。如果在高并发的情况下，auth-service
> 需要集群部署，并且需要做缓存处理。   
