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


## 声明式调用 Feign
1. 作用： 远程调用其他的服务
2. 模块：FeignClient的各项详细配置信息
    Feign 受到 Retrofit、JAXRS-2.0 和 websocket的影响，采用了声明式 API 接口的风格，将 Java Http 客户端绑定到它的内部。
3. [Feign](https://github.com/OpenFeign/feign) 的目标：
    将 Java Http 客户端的书写过程变得简单。
4. 编写一个 Feign 客户端：
    * 新建一个工程 `eureka-feign-client` 子工程；
    * 引入相关的依赖包；
    * 书写 `FeignConfig.java` 和 `HiService.java` 以及 `HiController.java` 类
5. 总结：
    * Feign 源码实现过程：
        1. 首先通过 @EnableFeignClients 注解开启 FeignClient 的功能。只有这个注解存在会在程序启动对 @FeignClient 注解的包扫描。
        2. 根据 Feign 的规则实现接口，并在接口上面加上 @FeignClient 的注解。
        3. 程序启动后，会进行包扫描，扫描所有的 @FeignClient 的注解的类，并将这些信息注入 IoC 容器中。
        4. 当接口的方法被调用时，通过 JDK 的代理来生成具体的 RequestTemplate 模板对象。
        5. 根据 RestTemplate 在生成 Http 请求的 Request 对象。
        6. Request 对象交给 Client 去处理，其中 Client 的网络请求框架可以是 HttpURLConnection、HttpClient 和 OkHttp。
        7. 最后， Client 被封装到 LoadBalanceClient 类，这个类结合类 Ribbon 做到了负载均衡。
        

## 熔断器 Hystrix
1. 什么是 Hystrix？
    * 避免服务和服务之间出现远程调度时的线程阻塞。阻止分布式系统中出现联动故障。
    * Hystrix 通过隔离服务服务的访问点阻止联动故障的，并提供了故障的解决方案，从而提高整个分布式系统的弹性。
2. Hystrix 的涉及原则？
    * 防止单个服务的故障耗尽整个服务的 Servlet 容器（例如 Tomcat）的线程资源。
    * 快速失败机制，如果某个服务出现了故障，则调用该服务的请求快速失败，而不是线程等待。
    * 提供回退（fallback）方案，在请求发生故障时，提供设定好的回退方案。
    * 使用熔断机制，防止故障扩散到其他服务。
    * 提供熔断器的监控组件 Hystrix Dashboard， 可以实时监控熔断器的状态。
3. 在 RestTemplate 和 Ribbon 上使用熔断器
    * 在 `eureka-ribbon-client` 工程中，引入相关依赖：
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    ```
   * 在启动类 `EurekaRibbonClientApplication.java` 上 添加 `@EnableHystrix` 注解开启熔断器功能。
   * 修改 RibbonService 的代码：
   ```java
    @HystrixCommand(fallbackMethod = "hiError")
    public String hi(String name) {
        return restTemplate.getForObject("http://eureka-client/hi/hi?name=" + name, String.class);
    }
    public String hiError(String name) {
        return "hi, " + name + ", sorry,error!";
    }
   ```
   * 测试:
        * 关闭：`eureka-client` 服务；
        * 浏览器访问：[http://localhost:8764/ribbon/hi?name=drew](http://localhost:8764/ribbon/hi?name=drew)

4. 在 Feign 上使用熔断器
    * 在 `eureka-feign-client` 的 `application.yaml` 添加：
    ```yaml
    feign:
      hystrix:
        enabled: true
    ```
   * 修改 `eureka-feign-client` 工程中的 `EurekaClientFeign` 代码：
   ```java
    @FeignClient(value = "eureka-client", configuration = FeignConfig.class, fallback = HiHystrix.class)
    ```
   * 新建一个类：`HiHystrix.java`
   ```java
    @Component
    public class HiHystrix implements EurekaClientFeign{
    
        @Override
        public String sayHiFormClientEureka(String name) {
            return "hi, " + name + ", sorry, error! =====> eureka-feign-client > Hystrix。";
        }
    }
    ```
   * 测试：
        1. 浏览器访问：[http://localhost:8765/feign/hi](http://localhost:8765/feign/hi) 是正常的。
        2. 关闭 `eureka-client` 服务，即此时 `eureka-feign-client` 无法调用 `eureka-client` 的 “/hi/hi” 接口，
        此时，浏览器上访问 [`http://localhost:8765/feign/hi`](http://localhost:8765/feign/hi) ,会被熔断器接收响应，
        浏览器返回：`hi, " + name + ", sorry, error! =====> eureka-feign-client > Hystrix。` 。
        

## 使用 Hystrix Dashboardd 监控熔断器的状态
> 熔断器的状况反映了一个程序的可用性和健壮性，是一个重要的指标。
> Hystrix Dashboard 是监控 Hystrix 的熔断器状况的一个组件，提供了数据监控和友好的图形化展示界面。
1. 在 Feign 中使用 Hystrix Dashboard
2. 引入依赖文件：
    ```xml
    <!--Hystrix Dashboard 依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    ```
3. 在启动类 `EurekaFeignClientApplication.java` 上添加注解 `@EnableHystrixDashboard` 开启 `HystrixDashboard` 功能。
4. 浏览器访问：
    * 先访问：[http://localhost:8765/feign/hi](http://localhost:8765/feign/hi)
    * 再访问：[http://localhost:8765/hystrix](http://localhost:8765/hystrix
    * 得到一个页面后，有三个输入框：以此自上从下输入：
        ① http://localhost:8765/hystrix.stream
        ② 【Delay】2000
        ③ 【Title】 任意内容


