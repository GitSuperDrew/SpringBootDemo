## 链路追踪
1. 链路追踪的前期准备：

|服务应用名|端口|作用|
|:--------|:----|:---|
| ① eureka-server           | 8761    | 注册中心|
| ② eureka-client           | 8763    | 服务提供者，链路追踪客户端|
| ③ eureka-client-feign     | 8765    | 服务消费者，链路追踪客户端|
| ④ Zipkin(以jar包的形式启动)  | 9411    | 链路追踪服务端|

2. 搭建 服务注册中心和服务发现所需的服务实例：
   1. 新建一个`主maven`工程，去掉 `src` 目录下的内容；引入依赖：
      ```xml
        <groupId>org.example</groupId>
        <artifactId>zipkin-demo</artifactId>
        <version>1.0-SNAPSHOT</version>
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.1.0.RELEASE</version>
        </parent>
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
            <java.version>1.8</java.version>
            <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
        </properties>
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring-cloud.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
      ```
   2. 新建一个spring boot 的 `eureka-server` 子工程作为注册中心的服务，引入依赖和配置文件：
        * 配置文件 `application.yaml`
        ```yaml
        server:
          port: 8761
        eureka:
          instance:
            prefer-ip-address: true
            hostname: localhost
          client:
            register-with-eureka: false
            fetch-registry: false
            service-url:
              defaultZone:
                http://${eureka.instance.hostname}:${server.port}/eureka/

        ```
        * 引入相关依赖：
        ```xml
         <parent>
                <groupId>org.example</groupId>
                <artifactId>zipkin-demo</artifactId>
                <version>1.0-SNAPSHOT</version>
        <!--        <groupId>org.springframework.boot</groupId>-->
        <!--        <artifactId>spring-boot-starter-parent</artifactId>-->
        <!--        <version>2.3.0.RELEASE</version>-->
                <relativePath/> <!-- lookup parent from repository -->
            </parent>
            <groupId>com.example</groupId>
            <artifactId>eureka-server</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <name>eureka-server</name>
            <description>Demo project for Spring Boot</description>
        
            <properties>
                <java.version>1.8</java.version>
            </properties>
        
            <dependencies>
                <!--eureka-server-->
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
                </dependency>
        
                <!--spring web -->
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-web</artifactId>
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
            </dependencies>
        ```
        * 启动类加入注解：`@EnableEurekaClients`
   3. 新建一个 Spring Boot 项目的 `eureka-client` 子工程，引入依赖和配置文件：
        * 配置文件内容：`application.yaml`
        ```yaml
        server:
          port: 8763
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
        spring:
          application:
            name: eureka-client
        ```
        * 引入依赖：
        ```xml
         <parent>
                <groupId>org.example</groupId>
                <artifactId>zipkin-demo</artifactId>
                <version>1.0-SNAPSHOT</version>
                <relativePath/> <!-- lookup parent from repository -->
            </parent>
            <groupId>com.example</groupId>
            <artifactId>eureka-client</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <name>eureka-client</name>
            <description>Demo project for Spring Boot</description>
        
            <properties>
                <java.version>1.8</java.version>
            </properties>
        
            <dependencies>
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
            </dependencies>
        ```
        * 启动类加入注解：`@EnableEurekaClients`
   4. 新建一个 Spring  Boot 工程 `eureka-client-feign` 子工程
        * 配置文件内容：`application.yaml`
        ```yaml
        server:
          port: 8765
        spring:
          application:
            name: eureka-client-feign
        eureka:
          client:
            serviceUrl:
              defaultZone: http://localhost:8761/eureka/
        ```
        * 引入依赖：
        ```xml
        <parent>
                <groupId>org.example</groupId>
                <artifactId>zipkin-demo</artifactId>
                <version>1.0-SNAPSHOT</version>
                <relativePath/> <!-- lookup parent from repository -->
            </parent>
            <groupId>com.example</groupId>
            <artifactId>eureka-client-feign</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <name>eureka-client-feign</name>
            <description>Demo project for Spring Boot</description>
        
            <properties>
                <java.version>1.8</java.version>
            </properties>
        
            <dependencies>
                <!--eureka-client : feign -->
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-openfeign</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                </dependency>
        
                <!--spring web-->
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-web</artifactId>
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
            </dependencies>
        ```
        * 书写相关接口 `HiService.java`,`HiController.java`,`EurekaClientFeign.java`,`FeignConfig.java`
        * 启动类加入注解：`@EnableEurekaClient` 和`@EnableFeignClients`
3. Zipkin 的 JAR 包 利用 `Git Bash` 命令窗口从 执行 `https://zipkin.io/quickstart.sh | bash -s` 得到Jar包。
4. 在 `eureka-client` 服务中，添加 Zipkin 的相关依赖 `spring-cloud-starter-zipkin` 和 `sleuth` 的起步依赖。  

