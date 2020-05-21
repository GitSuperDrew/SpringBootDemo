## 使用Spring Boot Admin 监控 Spring Boot 应用程序
1. 创建一个主maven工程（取名为:admin-auth-demo），删除src目录自身以及下所有文件目录。
2. 在 `pom.xml` 文件更新为下面依赖：
    ```xml
      <groupId>org.example</groupId>
        <artifactId>admin-auth-demo</artifactId>
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

### 创建 `admin-server` 服务
1. 右键 主maven工程，new → Module ，创建`Spring Boot`子工程。
2. 引入依赖：
    ```xml
    <parent>
            <groupId>org.example</groupId>
            <artifactId>admin-auth-demo</artifactId>
            <version>1.0-SNAPSHOT</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
        <groupId>com.example</groupId>
        <artifactId>admin-server</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <name>admin-server</name>
        <description>Demo project for Spring Boot</description>
    
        <properties>
            <java.version>1.8</java.version>
        </properties>
    
        <dependencies>
            <!--spring boot admin 起步依赖-->
            <dependency>
                <groupId>de.codecentric</groupId>
                <artifactId>spring-boot-admin-starter-server</artifactId>
                <version>2.1.0</version>
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
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    ```
3. 书写配置文件：`application.yaml`
    ```yaml
    server:
      port: 8769
    spring:
      application:
        name: admin-server
    
    ```
4. 启动类上，加入启动注解：`@EnableAdminServer`

### 创建 `admin-client` 服务
1. 右键 主maven工程，new → Module ，创建`Spring Boot`子工程。
2. 引入依赖：
    ```xml
    <parent>
        <groupId>org.example</groupId>
        <artifactId>admin-auth-demo</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>admin-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>admin-client</name>
    <description>Demo project for Spring Boot</description>
    
    <properties>
        <java.version>1.8</java.version>
    </properties>
    
    <dependencies>
        <!--spring boot admin 起步依赖【客户端】-->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.1.0</version>
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
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    ```
3. 编写配置文件：`application.yaml`
    ```yaml
    server:
      port: 8768
    spring:
      application:
        name: admin-client
      boot:
        admin:
          client:
            url: http://localhost:8769
    management:
      endpoints:
        web:
          exposure:
            include: '*'
      endpoint:
        health:
          show-details: always
    ```

### 测试
1. 依次开启服务：`admin-server` 和 `admin-client` (注意：可以**无需等待** `admin-server`完全启动后再去开启`admin-client`)
2. 浏览器访问：[http://localhost:8769/](http://localhost:8769/) 进入到admin-server服务控制面板中。
    ![admin-server-dashboard](images\admin-server-dashboard.png)<br/>
    ![admin-server-wallboard](images\admin-server-wallboard.png)