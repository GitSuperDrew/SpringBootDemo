# 工程简介
1. websocket 原理图<br/>
![websocket实时聊天原理](https://img2020.cnblogs.com/blog/1721320/202003/1721320-20200319084859071-1530751486.png)


# 搭建步骤
## 一、maven 依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- 核心依赖 springboot-websocket -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.46</version>
    </dependency>
</dependencies>
```

## 二、application.properties 配置
```properties
# 应用名称
spring.application.name=springboot-websocket
# 应用服务 WEB 访问端口
server.port=8080
# THYMELEAF (ThymeleafAutoConfiguration)
# 开启模板缓存（默认值： true ）
spring.thymeleaf.cache=true
# 检查模板是否存在，然后再呈现
spring.thymeleaf.check-template=true
# 检查模板位置是否正确（默认值 :true ）
spring.thymeleaf.check-template-location=true
#Content-Type 的值（默认值： text/html ）
spring.thymeleaf.content-type=text/html
# 开启 MVC Thymeleaf 视图解析（默认值： true ）
spring.thymeleaf.enabled=true
# 模板编码
spring.thymeleaf.encoding=UTF-8
# 要被排除在解析之外的视图名称列表，⽤逗号分隔
spring.thymeleaf.excluded-view-names=
# 要运⽤于模板之上的模板模式。另⻅ StandardTemplate-ModeHandlers( 默认值： HTML5)
spring.thymeleaf.mode=HTML5
# 在构建 URL 时添加到视图名称前的前缀（默认值： classpath:/templates/ ）
spring.thymeleaf.prefix=classpath:/templates/
# 在构建 URL 时添加到视图名称后的后缀（默认值： .html ）
spring.thymeleaf.suffix=.html
```

## 三、websocket 配置（`config/WebSocketConfig.java`）
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用 <p>
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return 服务轮询
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

## 四、核心：`WebSocketServer.java`
```java
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocketServer {

    private static AtomicInteger onlineNum = new AtomicInteger();

    private static ConcurrentMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息主题
     *
     * @param session 会话对象
     * @param message 消息内容
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 给指定用户发送信息
     *
     * @param userName 用户名
     * @param message  信息内容
     */
    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接成功调用
     *
     * @param session  会话对象
     * @param userName 消息内容
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName) {
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     *
     * @param userName 用户名
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName) {
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 收到客户端信息
     *
     * @param message 消息内容
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 错误时调用
     *
     * @param session   会话对象
     * @param throwable 异常信息
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
}
```

## 五、实时聊天页面：`webSocket.html`
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebSocket</title>
</head>
<body>
    <h3>hello socket</h3>
    <p>【userId】：<input id="userId" name="userId" type="text" value="10">
    <p>【toUserId】：<input id="toUserId" name="toUserId" type="text" value="20">
    <p>【toUserId】：<input id="contentText" name="contentText" type="text" value="hello websocket">
    <p>【操作】：<button onclick="openSocket()">开启socket</button><br><button onclick="sendMessage()">发送消息</button>
</body>
<script>
    var socket;

    /**
     * 打开 socket 来接
     */
    function openSocket() {
        if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            var userId = document.getElementById('userId').value;
            var socketUrl = "ws://localhost:8080/webSocket/" + userId;
            console.log(socketUrl);
            if (socket != null) {
                socket.close();
                socket = null;
            }
            socket = new WebSocket(socketUrl);
            //打开事件
            socket.onopen = function () {
                console.log("websocket已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            socket.onmessage = function (msg) {
                var serverMsg = "收到服务端信息：" + msg.data;
                console.log(serverMsg);
                //发现消息进入    开始处理前端触发逻辑
            };
            //关闭事件
            socket.onclose = function () {
                console.log("websocket已关闭");
            };
            //发生了错误事件
            socket.onerror = function () {
                console.log("websocket发生了错误");
            }
        }
    }

    /**
     *  发送消息主题
     */
    function sendMessage() {
        if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            // console.log("您的浏览器支持WebSocket");
            var toUserId = document.getElementById('toUserId').value;
            var contentText = document.getElementById('contentText').value;
            var msg = '{"toUserId":"' + toUserId + '","contentText":"' + contentText + '"}';
            console.log(msg);
            socket.send(msg);
        }
    }
</script>
</html>
```

## 六、测试
1. 打开浏览器，输入地址：`http://localhost:8080/webSocket`;
2. 点击 `开启socket`；控制台得到如下信息：
    ```text
    10加入webSocket！当前人数为1
    发送数据：欢迎10加入连接！
    ```
3. 点击 `发送消息`，返回到服务控制台，得到用户输入的信息；
    ```text
    客户端：{"toUserId":"20","contentText":"hello websocket"},已收到
    发送数据：客户端：{"toUserId":"20","contentText":"hello websocket"},已收到
    ```
4. 再次打开一个浏览器标签页，`重复前三步`操作，即可再添加一个用户进入到 `websocket` 并独立与`websocket服务器`消息互动；
   ```text
    10加入webSocket！当前人数为2
    发送数据：欢迎10加入连接！
    ```
5. 关闭连接（即 关闭浏览器当前标签页）；服务器将得到如下信息：
    ```text
    10断开webSocket连接！当前人数为1
    ```
6. 关闭服务器；（即到此整个所有的实时聊天窗口将会结束。）

# 延伸阅读

