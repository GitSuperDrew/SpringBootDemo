## 搭建一个服务注册中心和发现服务
1. 新建一个父工程，取名`eureka`，用作框架
2. 新建一个子工程，取名`eureka-server`，用作服务提供者
    * 右击父工程，新建一个 module工程，取名`eureka-server`使用 `Spring Initialar` 得到一个Spring Boot工程；
    * 修改此工程的父工程指向为：
    ```xml
    <parent>
        <groupId>org.example</groupId>
        <artifactId>eureka</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    ```
   * 引入 eureka 的 server 服务依赖
   ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        <version>2.1.0.RELEASE</version>
    </dependency>
    ```
   * 配置 `eureka-server` 的 `application.yaml` 文件：
   ```yaml
   server:
     port: 8761
   eureka:
     instance:
       prefer-ip-address: true
       hostname: localhost
     client:
       registerWithEureka: false
       fetchRegistry: false
       serverUrl:
         defaultZone:
           http:/${eureka.instance.hostname}:${server.port}/eureka/
   ```
3. 新建一个子工程，取名`eureka-client`，用作服务提供者
    * 右击父工程，新建一个 module工程，取名`eureka-client`使用 `Spring Initialar` 得到一个Spring Boot工程；
    * 引入父依赖，同 `eureka-server` 一样，替换成同样的父工程。
    * 引入 `eureka-client` 相关依赖：
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <version>2.1.0.RELEASE</version>
    </dependency>
    ```
   * 编写配置文件 eureka-client 的 application.yaml 文件：
   ```yaml
    server:
      port: 8762  # eureka-client 客户端内容
    eureka:
      client:
        service-url:
          defaultZone: http://localhost:8761/eureka/  # 将eureka的客户端服务提交到服务中心注册
    spring:
      application:
        name: eureka-client # 给服务注册中心提交自己服务的名字
    ```
4. 测试：
    * 第一步：启动 `eureka-server`
    * 第二步：启动 `eureka-client`
    * 第三步：浏览器访问 [http://localhost:8761/](http://localhost:8761/)
    * 第四步：验证是否在 栏目：`Instances currently registered with Eureka` 下存在 `eureka-client` 名称的服务。如果存在，即注册成功；否则，失败。
    * 第五步：在 `eureka-client` 模块中新建一个controller类 `HiController.java` ,测试。

5. 提高：
    * 构建高可用的 `Eureka Server` 集群，**该如何实现?**