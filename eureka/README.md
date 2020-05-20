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

<hr/>

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
        

<hr/>

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
        

<hr/>

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

<hr/>

## 路由网关 Spring Cloud Zuul
1. 为什么需要Zuul？
    1. Zuul 、Ribbon 和 Eureka 相结合，可以实现智能路由和负载均衡的功能，Zuul能够将请求榴莲干某种策略分发到集群状态的多个服务。
    2. 网关将所有服务的API接口统一聚合，并统一对外暴露。
    3. 网关服务可以做用户身份认证和权限认证。
    4. 网关可以实现监控功能，实时日志输出，对请求进行记录。
    5. 网关可以用来实现流量监控，在高流量的情况下，对服务进行降级。
    6. API 接口从内部服务分离出来，方便做测试。

2. Zuul 的工作原理？
    1. Zuul 是通过 Servlet 来实现的，Zuul 通过自定义的 ZuulServlet （类似于 Spring MVC 的DispathServlet）来对请求进行控制。
    2. Zuul 的核心是一系列过滤器，可以再 Http 请求的发起和响应返回期间执行一系列的过滤器。
    3. Zuul 的四种过滤器：
        * PRE 过滤器： 它是在请求路由到具体的服务之前执行的，这种类型的过滤器可以做安全验证，例如身份验证、参数验证等。
        * ROUTING 过滤器：它用于将请求路由到具体的微服务实例。在默认情况下，它使用 Http Client 进行网络请求。
        * POST 过滤器：它是在请求以被路由到微服务后执行的。一般情况下，用作收集统计信息、指标，以及将响应传输到客户端。
        * ERROR 过滤器：它是在其他过滤器发生错误时执行的。
    4. Zuul 采取了动态读取、编译和运行这些过滤器。过滤器之间不能直接相互通信，而是通过 RequestContext 对象来共享数据，每个请求都会创建一个 RequestContext 对象。Zuul过滤器具有以下几个关键特性：
        * Type（类型）：Zuul过滤器的类型，这个类型决定了过滤器在请求的那个阶段起作用，例如  Pre\Post阶段等。
        * Execution Order(执行顺序)：规定了过滤器的执行顺序，Order 的值越小，越先执行。
        * Criteria(标准)：过滤器执行所需的条件。
        * Action（行动）：如果如何执行条件，则执行Action （即逻辑代码）。
3. 实践：
    * 新建一个 spring boot 工程的子工程 `eureka-zuul-client`;
    * 引入依赖：
    ```xml
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
            </dependency>
    ```
    * 启动类 `EurekaZuulClientApplication.java` 添加注解 `@EnableEurekaClient` 注解，开启 `EurekaClient`的功能；
    * 启动类 ~ 添加 zuul 注解 `@EnableZuulProxy` ； 
    * 编写配置文件：`application.yaml`
        ```yaml
        server:
          port: 5000
        spring:
          application:
            name: service-zuul
        eureka:
          client:
            service-url: 
              defaultZone: http://localhost:8761/eureka/  # 像服务注册中心注册服务
        zuul:
          routes: 
            hiapi:
              path: /hiapi/**
              serviceId: eureka-client
            ribbonapi:
              path: /ribbonapi/**
              serviceId: eureka-ribbon-client
            feignapi:
              path: /feignapi/**
              serviceId: eureka-feign-client
        ```
    * 以此开启相关服务： eureka-server, eureka-client, eureka-ribbon-client, eureka-feign-client, eureka-zuul-client.
    * 测试：
        + 浏览器访问：[http://localhost:5000/hiapi/hi/hi?name=zuul-drew](http://localhost:5000/hiapi/hi/hi?name=zuul-drew)
        + 浏览器访问：[http://localhost:5000/feignapi/feign/hi?name=zuul-drew-feign](http://localhost:5000/feignapi/feign/hi?name=zuul-drew-feign)
        + 浏览器访问：[http://localhost:5000/ribbonapi/ribbon2/testRibbon?name=zuul-drew-feign](http://localhost:5000/ribbonapi/ribbon2/testRibbon?name=zuul-drew-feign)
4. Zuul 默认情况下在路由转发时做了负载均衡。
    1. 如果不需要用 Ribbon 做负载均衡，可以指定服务的实例的URL：
        * 可以修改配置如下：
        ```yaml
        zuul:
          routes:
            hiapi:
              path: /hiapi/**
              url: http://localhost:8762
        ```
       * 重启服务 `eureka-zuul-client` 服务，此时请求 [http://localhost:5000/hiapi/hi/hi?name=zuul-drew](http://localhost:5000/hiapi/hi/hi?name=zuul-drew)
       , 浏览器只会显示指定URL的响应内容。
    2. 如果你既想指定 URL， 并且想做负载均衡：
       * 修改配置文件：
       ```yaml
       zuul:
         routes:
           hiapi:
             path: /hiapi/**
             serviceId: hiapi-v1
       ribbon:
         eureka:
           enabled: flase
       hiapi-v1:
         ribbon:
           listOfServers: http://localhost:8762, http://localhost:8763 
       ```
       * 重启服务 `eureka-zuul-client`，浏览器访问：[http://localhost:5000/hiapi/hi/hi?name=zuul-drew](http://localhost:5000/hiapi/hi/hi?name=zuul-drew)
       * 结果：此时的浏览器会显示给定URL服务返回来的信息。
5. 在 Zuul 上配置 API 接口的版本号
> 原来的访问：[http://localhost:5000/hiapi/hi/hi?name=zuul-drew](http://localhost:5000/hiapi/hi/hi?name=zuul-drew) <br/>
> 最终的访问：[http://localhost:5000/v1/hiapi/hi/hi?name=zuul-drew](http://localhost:5000/v1/hiapi/hi/hi?name=zuul-drew)<br/>

    1. 很简单：需要用到 zuul.prefix 这个配置
    2. 修改 application.yaml 配置文件，成为：
    zuul:
      routes:
        hiapi:
          path: /hiapi/**
          serviceId: eureka-client
        ribbonapi:
          path: /ribbonapi/**
          serviceId: eureka-ribbon-client
        feignapi:
          path: /feignapi/**
          serviceId: eureka-feign-client
      prefix: /v1   # 👉👉👉添加此配置信息
    3. 浏览器访问【最终的访问↑】 http://localhost:5000/v1/ribbonapi/ribbon2/testRibbon?name=zuul-drew-feign  ；>>> 得到成功响应。
    
    
    
    
<hr/>

## Zuul 再实践再深入
1. 在 Zuul 上配置熔断器
> Zuul 作为 Netflix 组件，可以于 Ribbon、Eureka 和 Hystrix 等组件相结合，实现负载平衡、熔断器功能。<br/>
> 默认情况下，Zuul和Ribbon相结合，实现了负载均衡的功能。
> 实现步骤如下：
>   1. 实现 `ZuulFallbackProvider.java` 的接口。（实现两个方法：① getRoute()：用于指定熔断功能应用于哪些路由的服务；② fallbackResponse() 为进入熔断功能时执行的逻辑。）
>   操作: ① 新建一个接口：`ZuulFallbackProvider.java`；② 在项目 `eureka-zuul-client` 服务中添加类`MyFallbackProvider.java`实现 ZuulFallbackProvider 的接口。
>   2. 以此开启相关服务： eureka-server, eureka-client, eureka-ribbon-client, eureka-feign-client, eureka-zuul-client.
>   3. 重启`eureka-zuul-client` 服务，并且关闭 `eureka-client` 所有实例；
>   4. 注意检查，是否在服务 `eureka-zuul-client` 的配置文件中加入版本控制，导致 URL中需要在请求对应的接口时，需要添加 `/v1` 版本信息。
>   5. 浏览器访问： [http://localhost:5000/hiapi/hi/hi?name=Drew-Zuul-Hystrix](http://localhost:5000/hiapi/hi/hi?name=Drew-Zuul-Hystrix)
>   
> 扩展：
>   1. 如果需要所有的路由服务都加**熔断功能**，只需要修改 `getRoute()` 为如下所示即可：
   ```java
    @Override
    public String getRoute() {
        // 如果需要将所有的路由服务都加 熔断功能，只需要 写成 `return  "*";` 即可。
        return "eureka-client";
    }
   ```

2. 在 Zuul 中使用过滤器
    1. 编写过滤器 `MyFilter.java` ；
    2. 重新服务 `eureka-zuul-client` ，打开浏览器访问：[http://localhost:5000/hiapi/hi/hi?name=Zuul-MyFilter](http://localhost:5000/hiapi/hi/hi?name=Zuul-MyFilter)
    3. 返回的结果为：`token is empty`;
    4. 修改URL，令 URL携带token参数，浏览器访问URL: [http://localhost:5000/hiapi/hi/hi?name=Zuul-MyFilter&token=1122122](http://localhost:5000/hiapi/hi/hi?name=Zuul-MyFilter&token=1122122)
    5. 启示：可以用来做安全验证，参数检验等。

3. Zuul的常用使用方式
    * 对不同的渠道使用不同的 Zuul 来进行路由；
    > 例如，移动端共用一个 Zuul 网关实例，Web 端用另外一个 Zuul 网关实例，其他的客户端用另一个 Zuul 实例进行路由。
    * （集群）通过 Nginx 和 Zuul 相互结合来做负载均衡。
    > 暴露在最外面的时 Nginx 主从双热备进行 Keepalive，Nginx 经过某种路由策略，将请求路由转发到 Zuul 集群上，Zuul最终将请求分发到具体服务上。
                                                                                                                                                              >
  
<hr/>                                                                                                                                    >

<hr/>

## 服务网关：新一代网关 `Gateway`，见下一个项目：`spring-cloud-gateway`

<hr/>

## 配置中心 Spring Cloud Config
### 构建 config-server 工程
1. 新建一个子工程：`config-server`；
2. 引入依赖：`spring-cloud-config-server`
3. 启动类添加注解：`@EnableConfigServer  // 开启 config server 地功能`
4. 在 resource/shared 目录下添加文件 `config-client-dev.yml`

### 构建 config-client 工程
1. 新建一个子工程：`config-client`;
2. 添加配置文件 `bootstrap.yml` 文件，写入配置信息；
3. 在启动类 `ConfigClientApplication.java` 写入 注解 和 方法hi()；
4. 启动 `eureka-server` 服务，启动 `config-server`服务，启动 `config-client` 服务；
5. 浏览器访问：[http://localhost:8762/foo](http://localhost:8762/foo)
6. 结果展示：`foo version 1`

### Config Server 从远程 Git 仓库读取配置文件
> Spring Cloud Config 支持从远程 Git 仓库读取配置文件，即 Config Server 可以不从本地的仓库读取，而是从远程的Git仓库读取。
> 这样做的好处就是 将配置统一管理，并且可以通过 Spring Cloud Bus 在不人工启动程序的情况下对 Config Client 的配置进行刷新。
>
示例： 使用 GitHub 作为远程 Git 仓库
1. 修改 Config Server 的配置文件 `application.yaml`：
```yaml
server:
  port: 8769
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/GitSuperDrew/SpringBootDemo  # 远程的clone的地址
          search-paths: tree/master/eureka/config-server
          username: GitSuperDrew  # Git 仓库的登录用户名
          password:   # Git 仓库的登录密码（公开的仓库，所以无需要密码）
        native:
          search-locations: classpath:/shared
      label: master # label为git仓库的分支名，此处从主干分支
  profiles:
    active: native
  application:
    name: config-server
```
2. 以此启动服务 `eureka-server`,`config-server`,`config-client` 三个服务；
3. 浏览器访问：[http://localhost:8762/foo](http://localhost:8762/foo)
4. 结果显示：`foo version 1`, 可见`config-server`从远程仓库 Git 仓库读取了配置文件，`config-client` 从 `config-server` 中读取了配置文件。

### 构建高可用的 Config Server
> 当服务实例很多时，所有的服务实例需要同时从配置中心 Config Server 读取配置文件，这时可以考虑将配置中心 Config Server 做成一个微服务，并且将其集群化，从而达到高可用。
>
1. 构建 `Eureka Server 2` 中心服务
    * 新建一个 `eureka-server-2` 子工程；（Spring Boot 项目）
    * 添加依赖：
        ```xml
        <!--eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        ```
   * 启动类添加注解 `@EnableEurekaServer` , 开启 Eureka Server 的功能。
2. 改造 `Config Server` 服务  
> Config Server 作为一个 Eureka Client ，需要在工程中的pom 文件中引入相关依赖
   1. 引入依赖
      ```xml
        <!--引入 eureka-client 依赖，开启 eureka client 的功能-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
      ```
   2. `config-server` 服务工程启动类，加入注解 `@EnableEurekaClient` , 开启 EurekaClient 的功能。
   3. 在 `config-server` 服务的配置文件 `application.yaml` 中制定 服务注册的地址；
      ```yaml
      eureka:
        client:
          service-url: 
            defaultZone: http://localhost:8761/eureka/
     
      ```
3. 改造 `Config Client` 服务
>  Config Server 一样作为 Eureka Client，在 pom 文件加上 Eureka Client 的依赖。
   1. 添加依赖
       ```xml
        <!-- Eureka Client 依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
       ``` 
   2. 启动类上添加注解`@EnableEurekaClient`，开启 Eureka Client 的功能;
   3. 配置文件 `application.yaml` 中，添加相关配置:
       ```yaml
        spring:
          application:
            name: config-client
          cloud:
            config:
              fail-fast: true
              discovery:
                enabled: true
                service-id: config-client
          profiles:
            active: dev
        server:
          port: 8762
        eureka:
          client:
            service-url: 
              defautlZone: http://localhost:8761/eureka/
      ```
   4. 以此启动服务 `eureka-server-2`, `config-server`, `config-client` 工程，**注意这里需要`config-server`启动成功并且向`eureka-server-2`
   注册完成后，才能启动`config-client`; 否则，`config-client` 找不到 `config-server`**;【可以访问：http://localhost:8761/ 来查看是否config
   -server已经注册了】
   5. 测试：浏览器访问 [http://localhost:8762/foo](http://localhost:8762/foo), 即可得到结果：`foo version 1`
   6. **[那如何搭建高可用的 `Config Server` 呢？]()👉 只需要将 `Config Server` 多实例部署，用`IDEA`开启多个 `Config Sever` 实例，端口分别为 `8769` 和 `8768`
   . 在浏览器上访问`Eureka Server`的主页：`http://localhost:8761/` ，多次启动`config-client`, 可以看到它从 `8769`和`8768`这两个端口切换读取 `Config Server`
    的配置文件，并且做了负载均衡。**