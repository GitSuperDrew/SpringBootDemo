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