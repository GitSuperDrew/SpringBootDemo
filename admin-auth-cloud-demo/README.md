## 使用 Spring Boot Admin 监控 Spring Cloud 微服务
前期准备：需要构建主工程为纯净的maven工程，再建立三个子工程 :
`eureka-server` 作为服务注册中心，
`admin-client` 服务消费者，
`admin-server` 服务提供者。

1. 三者相关配置如下：

    | 应用名 | 端口 | 说明|
    |:---- |   :----|:----|
    |   eureka-server  |8761   |   服务注册中心|
    |   admin-server   |8769   |   admin服务端，向注册中心注册|
    |   admin-client   |8768   |   admin客户端，向注册中心注册|

### 🔨构建 `eureka-server` 服务
1. 依赖：(添加的部分)
    ```xml
    <!--eureka-server-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

    <!--spring web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    ```
2. 配置内容：(全部内容)
    ```yaml
    spring:
      application:
        name: eureka-server
    server:
      port: 8761
    eureka:
      client:
        fetch-registry: false
        register-with-eureka: false
        service-url:
          defaultZone: http://localhost:${server.port}/eureka/
    
    ```
3. 启动类添加注解：
    ```java
    @EnableEurekaServer
    @SpringBootApplication
    public class EurekaServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }
    
    }
    ```


### 🔨构建 `admin-server` 服务
1. 依赖：(添加部分)
    ```xml
    <!--boot-admin-server-->
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
        <version>2.1.0</version>
    </dependency>
    <!--eureka-client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!--spring web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    ```
2. 配置内容：(全部内容)
    ```yaml
    server:
      port: 8769
    spring:
      application:
        name: admin-server
    eureka:
      client:
        registry-fetch-interval-seconds: 5
        service-url:
          defaultZone: ${EUREKA_SERVICE_URL:http://localhost:8761}/eureka/
      instance:
        lease-renewal-interval-in-seconds: 10
        health-check-url-path: /actuator/health
    
    management:
      endpoints:
        web:
          exposure:
            include: "*"
      endpoint:
        health:
          show-details: always
    ```

3. 启动类添加注解：
    ```java
    @SpringBootApplication
    @EnableAdminServer // 开启admin server 的功能
    @EnableDiscoveryClient   // 开启 Eureka Client 的功能
    public class AdminServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(AdminServerApplication.class, args);
        }
    
    }
    ```

### 🔨构建 `admin-client` 服务
1. 依赖：(添加内容)
    ```xml
    <!--eureka-client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!--spring web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--actuator-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
2. 配置内容：（全部内容）
    ```yaml
    server:
      port: 8768
    spring:
      application:
        name: admin-client
    eureka:
      client:
        registry-fetch-interval-seconds: 5
        service-url:
          defaultZone: ${EUREKA_SERVICE_URL:http://localhost:8761}/eureka/
      instance:
        lease-renewal-interval-in-seconds: 10
        health-check-url-path: /actuator/health
    
    management:
      endpoints:
        web:
          exposure:
            include: "*"
      endpoint:
        health:
          show-details: always
    ```
3. 启动类添加注解：
    ```java
    @SpringBootApplication
    @EnableEurekaClient
    public class AdminClientApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(AdminClientApplication.class, args);
        }
    }
    ```

### ⚙测试
1. 依次启动：`eureka-server`, `admin-client`, `admin-server`
2. 浏览器访问：[http://localhost:8769/](http://localhost:8769/)
3. 结果展示：
    * 初始界面：
        ![src\main\resources\static\images\init-dashboard.png](src\main\resources\static\images\init-dashboard.png)<br/>
    * 详情页面:
        ![src\main\resources\static\images\detail-dashboard1.png](src\main\resources\static\images\detail-dashboard1.png)
        ![src\main\resources\static\images\detail-dashboard2.png](src\main\resources\static\images\detail-dashboard2.png)
    * wallboard:
        ![src\main\resources\static\images\wallboard.png](src\main\resources\static\images\wallboard.png)
        ![src\main\resources\static\images\journal.png](src\main\resources\static\images\journal.png) 

## Spring Boot Admin 集成 Security 组件
> 在生产环境中，不希望通过网址直接访问 Spring Boot Admin Server 的主页面，因为这个界面包含了太多的服务信息，必须对这个界面的访问进行安全验证。
> Spring Boot Admin 提供了登录界面的组件，并且和 Spring Boot Security 相结合，需要用户登录才能访问 Spring Boot Admin Server 的界面。

1. 需要在admin-server 工程进行改造就行了。在 服务 `admin-server` 的 pom 文件中引入 `Security` 的起步依赖即可：
    ```xml
    <!--引入 security-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```
2. 修改 `admin-server` 的配置文件 `application.yaml`:
    ```yaml
    server:
      port: 8769
    spring:
      application:
        name: admin-server
      security:
        user:
          name: admin
          password: admin
    eureka:
      client:
        registry-fetch-interval-seconds: 5
        service-url:
          defaultZone: ${EUREKA_SERVICE_URL:http://localhost:8761}/eureka/
      instance:
        lease-renewal-interval-in-seconds: 10
        health-check-url-path: /actuator/health
        # 这个会导致 admin-server这个服务没有认证权限，从而失败，如果需要正常显示的话，请注释这下面三行。
        metadata-map: 
          user.name: admin
          user.password: 123456
    
    management:
      endpoints:
        web:
          exposure:
            include: "*"
      endpoint:
        health:
          show-details: always
    ```
3. 新建一个 `SecuritySecureConfig.java` ,对请求做认证处理；
4. 测试：
    * 每次启动服务时，确保前一个服务一定是成功启动了，不可前一个服务未启动就去启动下一个服务，那样会产生问题的。
    * 重启 admin-server 工程(或者 依次启动eureka-server,admin-client,admin-server)，在浏览器访问 [http://localhost:8769/](http://localhost:8769/)
    * 结果：浏览器显示界面如下
        ![src\main\resources\static\images\security-login.png](src\main\resources\static\images\security-login.png)
    * 输入用户名和密码（`admin/admin`）, 点击 **登录** 即可进入 `Spring Boot Admin` 管理界面
    
## Spring Boot Admin 集成 Mail 组件
> ⚠说明：由于邮箱为开通发送邮箱服务，即集成 mail 服务为经过自身测试！
1. 当服务不健康或者下线了，都可以给指定邮箱发送邮件。集成非常简单，只需要对 `admin-server`工程引入 mail的起步依赖并对`admin-server`的配置文件`application.yaml`进行设置mail参数即可。
2. 依赖
    ```xml
    <!--引入 Mail组件（邮箱告警）-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    ```
3. 相关配置 `application.yaml`
    ```yaml
    spring:
      mail:
        host: smtp.163.com   # 邮箱服务器
        username: miles02    # 登录邮箱用户名
        password:            # 密码
      boot:
        admin:
          notify:
            mail:
              to: 27462137293@qq.com  # 自动监听，如果某个服务故障或下线了，会自动发送邮箱给此配置号的邮箱。
    ```