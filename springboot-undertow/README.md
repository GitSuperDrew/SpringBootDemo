# 工程简介
1. undertow 服务器的官网：[Undertow](https://undertow.io/)
2. undertow 简介
   > Undertow 是一个采用 Java 开发的灵活的高性能 Web 服务器，提供包括阻塞和基于 NIO 的非堵塞机制。Undertow 是红帽公司的开源产品，是 Wildfly 默认的 Web 服务器。Undertow 提供一个基础的架构用来构建 Web 服务器，这是一个完全为嵌入式设计的项目，提供易用的构建器 API，完全向下兼容 Java EE Servlet 3.1 和低级非堵塞的处理器。 
3. Undertow 特点
   > 1. 高性能 在多款同类产品的压测中，在高并发情况下表现出色。
   > 2. Servlet4.0 支持 它提供了对 Servlet4.0 的支持。
   > 3. Web Socket 完全支持，包括JSR-356，用以满足 Web 应用巨大数量的客户端。
   > 4. 内嵌式 它不需要容器，只需通过 API 即可快速搭建 Web 服务器。
   > 5. 灵活性 交由链式Handler配置和处理请求，可以最小化按需加载模块，无须加载多余功能。
   > 6. 轻量级 它是一个 内嵌Web 服务器， 由两个核心 Jar 包组成
4. 本项目的目的：
   > 测试使用 undertow 服务器替换 默认的服务器 tomcat

# 测试流程
1. 修改 springboot-web 中的依赖，移除默认的tomcat，核心的maven依赖如下：
    ```xml
     <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <!-- Exclude the Tomcat dependency -->
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- Use Undertow instead -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>

    </dependencies>
   ```
2. 在 `UndertowApplication.java` 中添加测试控制器；
   ```java
   @RestController
   @SpringBootApplication
   public class UndertowApplication {
       public static void main(String[] args) {
           SpringApplication.run(UndertowApplication.class, args);
       }
   
       @GetMapping(path = "/undertow/test")
       public String testUndertowServer() {
           return "use Undertow server success!";
       }
   }
   ```
2. 启动项目`Undertow started on port(s) 8080 (http)` 则说明替换服务器成功。
3. 浏览器访问：`http://localhost:8080/` ，返回 `use Undertow server success!` 。

# Undertow 简单应用
1. [Undertow 作为简单的web文件服务器使用](https://www.cnblogs.com/ljgeng/p/11239345.html)
2. [Spring Boot使用Undertow做服务器](https://blog.csdn.net/sophia63/article/details/104278710)

# 延伸阅读
1. [为什么很多 SpringBoot 开发者放弃了 Tomcat，选择了 Undertow?](https://www.rongsoft.com/article/2020/03/052231201487/)
