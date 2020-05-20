#### 1. 第一代网关 Zuul 和 第二代网关 Gateway 比较
* `Spring Clound Gateway` 服务网关是建立在 Spring Framework 5 之上的，使用非阻塞模式，并且支持长连接 Websocket。
* `Spring Cloud Zuul` 是基于 `Servlet` 的，采用HttpClient 进行请求转发，使用阻塞模式。
* 在性能上，服务网关  由于 `Zuul`,并且服务网关几乎实现了 Netflix Zuul 的全部功能。
* 在使用和功能上，用服务网关替换掉 Netflix Zuul的成本非常低的，几乎可以实现无缝切换。

#### 2. 服务网关作为整个分布式系统的流量入口，有着举足轻重的作用：
1. 协议转换，路由转发；
2. 流量聚合，对流浪进行监控，日志输出；
3. 作为整个系统的前端工程，对流量进行控制，有限流的作用；
4. 作为系统的前端边界，外部流量只能通过网关才能访问系统；
5. 可以在网关层做权限判断；
6. 可以在网关层做缓存。

#### 3. 服务网关的实现原理
1. 第一代网关 Zuul 有两大核心组件，分别是 路由（Router） 和 过滤器（Filter）。
2. 第二代服务网关 Geteway 多了一个断言（Predicate），用来判断请求到底交给哪一个 Gateway Web Handler 处理。
3. 如果 请求和路由匹配（这时就会用到断言），则将其发送到相应的 Gateway Web Handler处理。在处理请求时会经过一系列的过滤器链。
    * 在执行所有的 “pre” 过滤器逻辑时，一般可以实现**鉴权**、**限流**、**日志输出**、**更改请求头**、**转换协议**等功能；
    * 在收到请求之后，会执行所有的“post” 过滤器的逻辑，这里可以对响应数据进行修改，比如更改响应头和转换协议等。
    * **重点：** 将请求和路由进行匹配，这是需要用到断言来决定请求应该走哪一个路由。

#### 4. 断言工厂
> 断言（predicate）来自于Java 8 的接口。该接口接受一个输入参数，返回一个布尔值结果，包含多种默认方法将断言组合成其他复杂的逻辑（比如：与，非，或）。
>
1. After 路由断言工厂；
    * 新建一个Spring Boot工程 `spring-cloud-gateway` ；
    * 引入相关依赖包：见（spring-cloud-gateway/pom.xml）文件
    * 重启服务，浏览器访问：[http://localhost:8081/get](http://localhost:8081/get)
    * 如果访问失败，将出现 `404` 相关报错信息；如果成功，则将出现 `JSON` 字符串信息。
    * 详解：在设置 `application.yaml` 文件中，如果设置的 `-After=` 后的值值得注意：
        * 如果请求的时间在这个配置的时间之后，请求会被路由转发到 [http://httpbin.org:80]
        * 如果我们将配置的时间设置在当前时间之后，浏览器会显示错误信息 `404`，此时请求没有路由到配置的 URL。
    * 提醒：如果修改了 ‘uri: http://httpbin.org:80’ 为其他的后，出现了错误页面，尝试将浏览器的缓存情况，或者重启浏览器，再试试。

2. Header 路由断言工厂
    * Header路由断言工厂：需要两个参数，分别是 `Header键` 和 `Header值`, **Header值可以是正则表达式**
    * 当请求头匹配了断言的 Header键 和 Header值 时，断言通过，进入路由的逻辑中去；否则返回错误信息；
    * 工程配置文件的信息如下：
    ```yaml
    server:
      port: 8081
    spring:
      profiles:
        active: header_route
    #    active: after_route
    ---  # 表示在application.yaml文件中再建一个配置文件，语法是 3个横线
    spring:
      cloud:
        gateway:
          routes:
          - id: header_route
    #      - id: after_route
            uri: http://httpbin.org:80
            predicates:
            - Header=id,\d+
    #        - After=2017-01-20T17:42:47.789-07:00[America/Denver]
    #  profiles: after_route
      profiles: header_route

    ```
    * 重启服务，使用Windows 的 curl 命令，输入：`curl -H id:12 localhost:8081/get`.
    * 结果：如果在请求中没有带上 id 或 id的值非数字时，路由请求不会被执行，并报 `404` 错误信息.

3. Cookie 路由断言工厂
    * Cookie 路由断言工厂，需要两个参数，分别是 cookie 和 cookie 的值，Cookie 值可以是正则表达式。
    * 当请求头带有 Cookie, 并且 请求的cookie 和断言配置的 cookie 相匹配时，请求能够被正确路由，否则报 `404` 错误信息。
    * 修改 `application.yaml` 配置文件中对应的断言规则的信息为cookie相关设置。
    * 在 Windows中使用 CMD 命令，输入：`curl -H Cookie:name=Drew-Cookie-Route localhost:8081/get`
    * 结果：在请求中带上 Cookie，并且和断言中配置的Cookie相匹配，会返回正确结果，否则请求会报 `404` 错误。

4. Host 路由断言工厂
    * 同 Header和 Cookie 类似，需要修改对应的位置的配置
    * Host 路由断言工厂需要一个参数 —— Hostname, 它可以使用 `.*` 等去匹配 Host。这个参数会匹配请求头中的Host的值，如果匹配成功，则请求正确转发；否则，报 `404`错误；
    * `application.yaml` 的配置信息如下：
        ```yaml
        server:
          port: 8081
        spring:
          profiles:
            active: host_route
        ---  # 表示在application.yaml文件中再建一个配置文件，语法是 3个横线
        spring:
          cloud:
            gateway:
              routes:
              - id: host_route
                uri: http://httpbin.org:80
                predicates:
                - Host=**.example.com
          profiles: host_route
        ```
      * 重启服务，使用curl命令：`curl -H Host:www.example.com localhost:8081/get` 进行CMD命令行访问。
      * 结果：请求中含有 Host 后缀为 example.com 的请求都会被路由转发到配置的Url。如果成功返回请求的JSON数据信息，否则返回 `404` 错误信息。

5. Method 路由断言工厂
    * Method 指的是请求的类型（使用 curl localhost:8081/get ,默认请求类型被视为 GET类型的请求）
    * 请求类型：GET/POST/PUT/DELETE等
    * 修改 `application.yaml` 配置文件，得到如下配置信息：
    ```yaml
         server:
           port: 8081
         spring:
           profiles:
             active: method_route
         ---  # 表示在application.yaml文件中再建一个配置文件，语法是 3个横线
         spring:
           cloud:
             gateway:
               routes:
               - id: method_route
                 uri: http://httpbin.org:80
                 predicates:
                 - Method=GET
           profiles: method_route
    ```
   * 重启服务，使用 curl 命令 `curl localhost:8081/get` 或 `curl -XGET localhost:8081/get` 发送请求；
   * 结果：请求失败会出现 `404` 错误，成功则得到一串JSON格式的请求信息。如果使用 `curl -XPOST localhost:8081/get`, 将会得到错误信息。
   
6. Path 路由断言工厂
   * 修改配置文件为：
   ```yaml
        server:
          port: 8081
        spring:
          profiles:
            active: path_route
        ---  # 表示在application.yaml文件中再建一个配置文件，语法是 3个横线
        spring:
          cloud:
            gateway:
              routes:
              - id: path_route
                uri: http://www.spring4all.com
                predicates:
                - Path=/article/{segment}   # 320
          profiles: path_route
    ```
   * 重启服务，curl 命令访问 `curl http://www.spring4all.com/article/320` ;
   * 结果：如果成功，将返回网页的页面信息；否则返回 `404` 页面找不到的信息。

7. Query 路由断言工厂
   * Query路由断言工厂，即请求参数断言工厂，当请求携带的参数和配置的参数匹配时，路由被正确转发；否则，报 `404` 错误。该路由断言工厂需要配置2个参数，分别是参数名和参数值。
   * 配置文件`application.yaml` 信息修改为：
   ```yaml
     server:
       port: 8081
     spring:
       profiles:
         active: query_route
     ---  # 表示在application.yaml文件中再建一个配置文件，语法是 3个横线
     spring:
       cloud:
         gateway:
           routes:
           - id: query_route
             uri: http://httpbin.org:80
             predicates:
             - Query=foo, ba.
       profiles: query_route
   ```
   * 重启服务，Windows安装并配置好curl的Path变量，打开CMD窗口，访问：**`curl localhost:8081/get?foo=ba.`**
   * 结果：如果成功，则返回一串请求JSON的信息；否则返回404错误信息。

#### 5. 过滤器
> 过滤器的作用：
> <br/> pre 类型的过滤器：可以实现参数校验、权限校验、流量监控、日志输出、协议转换等功能
> <br/> post 类型的过滤器：做响应内容、响应头的修改、日志输出、流量监控等功能。
> <br/> 要弄清楚 网关这一层，就不得不 提到过滤器的作用了。
##### (1) 自定义过滤器

##### (2) 全局过滤器

#### 6. 限流
##### (1) 常见的限流方式:
> 高并发系统中，往往需要做限流，① 是为了防止流量突发是服务器过载，② 防止流量攻击。
> <br/> 常见的限流方式：①Hystrix使用线程池隔离，当个超过线程池的负载时，走熔断的逻辑；在一般引用服务器中，比如Tomcat容器是通过限制它的线程数来控制并发的；
> <br/> 也可以通过时间窗口的平均速度来控制流量。
##### (2) 常见的限流维度：
    * IP限流
    * 通过请求的URL限流
    * 通过用户访问的频次限流
##### (3) 限流发生的位置：
> 一般限流发生在网关层，比如 Nginx、Openresty、Kong、Zuul 和 服务网关（Spring Cloud Gateway） 等，也可以在引用层通过AOP方式去限流。
>

##### (4) 限流的算法：
###### 1. 计数器算法：
> 限流策略：1秒钟内只允许有100个请求通过，算法的实现思路是第一个请求进来时技术为1，后面每通过一个请求计数加1。当计数满100
> 后，后面的请求全部拒绝。这种技术算法非常简单，当流量突发时，它只允许前面的请求通过，一旦计数满了，拒绝所有的后续请求，这种现象称为 “突刺现象”。
###### 2. 漏桶算法：
> 漏桶算法，可以消除 “突刺现象”。其内部有一个容器，类似漏斗，当请求进来时，相当于水倒入了漏斗，然后从容器中均匀地取出请求进行处理，处理速率是固定地。不管上面流量多大，都全部装进容器，下面流出的速率始终保持不变。当容器中地请求数装满了，就直接拒绝请求。
###### 3. 令牌桶算法
> 令牌算法：对漏斗算法的改进。搜同算法只能均匀的处理请求。令牌算法 能够在均匀处理请求的情况下，应对一定程度上的突发流量。
> <br/> 令牌桶算法，需要一个容器来存储令牌，令牌以一定的速率均匀地向桶中存放，当超过桶地容量时，桶会丢弃多余的令牌。
> <br/> 当一个请求进来时，需要从令牌桶获取令牌，获取令牌成功，则请求通过；如果令牌桶中的令牌消耗完了，则获取令牌失败，拒绝请求。 