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
    
## 负载均衡 Ribbon
1. RestTemplate 使用来REST服务的，所以 RestTemplate 的主要方法都与REST的 HTTP协议的一些方法紧密相连，例如 HEAD、GET、POST、PUT、DELETE和OPTIONS等方法，
其对应的方法为：headForHeaders()、getForObject()、postForObject()、put() 和 delete() 等。

2. Ribbon介绍
    * Ribbon 由很多子模块，但很多模块没有用于生产环境，目前 Netflix 公司用于生产环境的 Ribbon 子模块如下：
        * ribbon-loadbalancer：可以独立使用或与其他模块一起使用的负载均衡器 API。
        * ribbon-eureka: Ribbon 结合 Eureka 客户端的 API，为负载均衡器提供动态服务注册列表信息。
        * ribbon-core：Ribbon 的核心 API。
3. 使用 RestTemplate 和 Ribbon 来消费服务
    * 创建一个子工程`eureka-ribbon-client`服务
    * 引入依赖：