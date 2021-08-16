# 工程简介
> 本项目使用 `SpringBoot`整合`RabbitMQ` 实现了：
> 1. `work` 模型
> 2. `发布订阅`模型
> 3. `topic` 模型
> 4. `confirm` 消息确认模型
> 5. `return` 消息机制
> 6. `TTL队列` 和 `死信队列` 机制需要手动验证（未实现，见附录）

## 基础环境
> 1. JDK `1.8`;
> 2. spring-boot-starter-amqp `2.3.7.RELEASE`;
> 3. lombok `1.18.16`;
> 4. RabbitMQ `3.9.3`;
> 5. Erlang `24.0.5`;
> 6. RabbitMQ-server 部署在 CentOS8;

# 项目搭建步骤
> 前期准备：CentOS8搭建RabbitMQ-server服务（见本文章的附录）
## (1) 引入核心maven依赖
```xml
    <!--rabbit client 客户端依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
```

## (2) 配置 RabbitMQ （`application.properties`）
```properties
# 应用名称
spring.application.name=springboot-rabbitmq
# 应用服务 WEB 访问端口
server.port=8080
# rabbitmq配置信息
# ip
spring.rabbitmq.host=192.168.174.156
# 端口
spring.rabbitmq.port=5672
# 用户名
spring.rabbitmq.username=admin
# 密码
spring.rabbitmq.password=admin
# 配置虚拟机
spring.rabbitmq.virtual-host=/
# 消息开启手动确认(兼容confirm和return两种机制模型)
spring.rabbitmq.listener.direct.acknowledge-mode=manual
# 【confirm机制模型需要开启】开启消息确认机制 confirm 异步
spring.rabbitmq.publisher-confirm-type=correlated
# 【return机制模型需要开启】开启return机制
spring.rabbitmq.publisher-returns=true
```

## (3) 准备测试用的实体类
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
}
```

## (4) RabbitMQ 配置
```java
package edu.study.module.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;


/**
 * RabbitMQ 工作模式
 *
 * @author zl
 * @create 2021-08-16 8:10
 */
@SpringBootConfiguration
public class RabbitmqConfig {

    // ==================================== 工作模式
    @Bean
    public Queue queueWork1() {
        return new Queue("queue_work");
    }

    // ==================================== 发布/订阅模式
    // 1.发布订阅模式 2.声明两个队列
    @Bean
    public Queue queueFanout1() {
        return new Queue("queue_fanout1");
    }

    @Bean
    public Queue queueFanout2() {
        return new Queue("queue_fanout2");
    }

    // 准备一个交换机
    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange("exchange_fanout");
    }

    // 将交换机和队列进行绑定
    @Bean
    public Binding bindingExchange1() {
        return BindingBuilder.bind(queueFanout1()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchange2() {
        return BindingBuilder.bind(queueFanout2()).to(exchangeFanout());
    }


    // ==================================== topic 模型
    // topic 模型
    @Bean
    public Queue queueTopic1() {
        return new Queue("queue_topic1");
    }

    @Bean
    public Queue queueTopic2() {
        return new Queue("queue_topic2");
    }

    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange("exchange_topic");
    }

    @Bean
    public Binding bindingTopic1() {
        return BindingBuilder.bind(queueTopic1()).to(exchangeTopic()).with("topic.#");
    }

    @Bean
    public Binding bindingTopic2() {
        return BindingBuilder.bind(queueTopic2()).to(exchangeTopic()).with("topic.*");
    }


    // ==================================== confirm 模型
    // 测试confirm 机制，专门创建了一个队列
    @Bean
    public Queue queueConfirm() {
        return new Queue("queue_confirm");
    }

    // ==================================== return 模型
    // 测试return 机制，专门创建了一个队列
    @Bean
    public Queue queueReturn() {
        return new Queue("queue_return");
    }

    @Bean
    public TopicExchange exchangeReturn() {
        return new TopicExchange("exchange_return");
    }

    @Bean
    public Binding bindingReturn() {
        return BindingBuilder.bind(queueReturn()).to(exchangeReturn()).with("return.*");
    }
}
```

## (5) 编写 RabbitMQ-Listener
### 1. work 模型
```java
package edu.study.module.springbootrabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author zl
 * @create 2021-08-16 8:19
 */
@Component
public class WorkReceiveListener {
    /**
     * 接受信息接口
     *
     * @param msg     消息
     * @param channel 通道信息
     * @param message 附加的参数信息
     */
    @RabbitListener(queues = {"queue_work"})
    public void receiveMessage(String msg, Channel channel, Message message) {
        // 只包含发送的消息
        System.out.println("1接受到的信息：" + msg);
    }

    @RabbitListener(queues = {"queue_work"})
    public void receiveMessage2(Object obj, Channel channel, Message message) {
        // 包含所有信息
        System.out.println("2接收到消息：" + obj);
    }

}
```
### 2. publish-subscribe 模型
```java
package edu.study.module.springbootrabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 发布-订阅模型（监听类）
 *
 * @author zl
 * @create 2021-08-16 8:57
 */
@Component
public class PublishReceiveListener {
    @RabbitListener(queues = {"queue_fanout1"})
    public void receiveMsg1(String msg) {
        System.out.println("队列1：接受到消息：" + msg);
    }

    @RabbitListener(queues = {"queue_fanout2"})
    public void receiveMsg2(String msg) {
        System.out.println("队列2：接收到消息：" + msg);
    }
}
```

### 3. topic 模型
```java
package edu.study.module.springbootrabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 主题模型（Topic）
 *
 * @author zl
 * @create 2021-08-16 9:07
 */
@Component
public class TopicReceiveListener {
    @RabbitListener(queues = {"queue_topic1"})
    public void receiveMsg1(String msg) {
        System.out.println("消费者1接收到：" + msg);
    }

    @RabbitListener(queues = {"queue_topic2"})
    public void receiveMsg2(String msg) {
        System.out.println("消费者2接收到：" + msg);
    }
}
```

### 4. confirm 模型
```java
package edu.study.module.springbootrabbitmq.listener;

import edu.study.module.springbootrabbitmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息确认机制模型（confirm）
 *
 * @author zl
 * @create 2021-08-16 9:19
 */
@Component
public class ConfirmReceiveListener {
    @RabbitListener(queues = {"queue_confirm"})
    public void receiveMsg(User user) {
        System.out.println("接收到的消息为：" + user);
    }
}

```

### 5. return 模型
```java
package edu.study.module.springbootrabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * return机制，监听器
 *
 * @author zl
 * @create 2021-08-16 9:30
 */
@Component
public class ReturnReceiveListener {
    @RabbitListener(queues = "queue_return")
    public void receiveMsg(String msg) {
        System.out.println("接收的消息为：" + msg);
    }
}
```

## (6) 编写测试流程
### controller层
```java
package edu.study.module.springbootrabbitmq.controller;

import edu.study.module.springbootrabbitmq.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类入口
 * <pre>
 *     1. 工作模型（work）
 *     2. 发布-订阅模型（publisher/subscribe）
 *     3. 主题-订阅模型（Topic）
 *     4. confirm 模型（confirm）
 *     5. return 模型（return）
 * </pre>
 * @author zl
 * @create 2021-08-16 8:13
 */
@RestController
public class RabbitmqController {

    @Autowired
    private RabbitmqService rabbitmqService;

    // 工作模型（work）
    @RequestMapping("/sendWork")
    public Object sendWork() {
        rabbitmqService.sendWork();
        return "[work]发送成功……";
    }


    // 发布订阅模型（publish-subscribe）
    @RequestMapping("/sendPublish")
    public String sendPublish() {
        rabbitmqService.sendPublish();
        return "[publis-subscribe]发送成功……";
    }

    // 主题模型（Topic）
    @RequestMapping("/sendTopic")
    public String sendTopic(){
        rabbitmqService.sendTopic();
        return "[topic]发送成功……";
    }

    // 消息确认模型（confirm机制）
    @RequestMapping("/sendConfirm")
    public String sendConfirm() {
        rabbitmqService.sendConfirm();
        return "[confirm机制]发送成功……";
    }

    // return机制模型（return机制）
    @RequestMapping("/sendReturn")
    public String sendReturn(){
        rabbitmqService.sendReturn();
        return "[return机制]发送成功……";
    }

}
```

### service 层
#### ① RabbitmqService.java
```java
package edu.study.module.springbootrabbitmq.service;

public interface RabbitmqService {

    // 工作模型（worK）
    void sendWork();

    // 发布订阅模式（publish-subscribe）
    void sendPublish();

    // 主题模式（Topic）
    void sendTopic();

    // confirm 机制
    void sendConfirm();

    // return 机制
    void sendReturn();
}
```
#### ② RabbitmqServiceImpl.java
```java
package edu.study.module.springbootrabbitmq.service.impl;

import edu.study.module.springbootrabbitmq.mapper.RabbitmqMapper;
import edu.study.module.springbootrabbitmq.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zl
 * @create 2021-08-16 8:17
 */
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitmqMapper rabbitmqMapper;

    @Override
    public void sendWork() {
        rabbitmqMapper.sendWork();
    }

    @Override
    public void sendPublish() {
        rabbitmqMapper.sendPublish();
    }

    @Override
    public void sendTopic() {
        rabbitmqMapper.sendTopic();
    }

    @Override
    public void sendConfirm() {
        rabbitmqMapper.sendConfirm();
    }

    @Override
    public void sendReturn() {
        rabbitmqMapper.sendReturn();
    }
}
```

### mapper 层
```java
package edu.study.module.springbootrabbitmq.mapper;

import edu.study.module.springbootrabbitmq.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ DAO层，发送数据
 * @author zl
 * @create 2021-08-16 8:15
 */
@Component
public class RabbitmqMapper {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // =========================================== 模型一：工作队列模型
    public void sendWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("queue_work", "测试work模型：" + i);
        }
    }

    // =========================================== 模型二：发布订阅模型
    public void sendPublish() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("exchange_fanout", "", "测试发布-订阅模型" + i);
        }
    }

    // =========================================== 模型三：向 topic 模型发送数据
    public void sendTopic() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                rabbitTemplate.convertSendAndReceive("exchange_topic", "topic.km.topic", "测试发布订阅模型：" + i);
            } else {
                rabbitTemplate.convertSendAndReceive("exchange_topic", "topic.km", "测试发布订阅模型：" + i);
            }
        }
    }

    // =========================================== 模型四：测试 confirm 机制（confirm-part-one）
    public void sendConfirm() {
        rabbitTemplate.convertAndSend("queue_confirm",
                new User(1, "km", "km1123"),
                new CorrelationData("" + System.currentTimeMillis()));
        // 注意: 使用 confirm机制时，发送消息时最好把 CorrelationData 加上，因为如果出错了，使用 CorrelationData 可以更快的定位到消息错误信息。
        rabbitTemplate.setConfirmCallback(confirmCallback);
    }

    // 配置 confirm 机制（confirm-part-two）
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 消息相关的数据，一般用于获取 唯一标识 id
         * @param b true 消息确认成功，false 失败
         * @param s 确认失败的原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            if (b) {
                System.out.println("confirm 消息确认成功..." + correlationData.getId());
            } else {
                System.out.println("confirm 消息确认失败..." + correlationData.getId() + " cause: " + s);
            }
        }
    };


    // =========================================== 模型五：return 机制
    public void sendReturn() {
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("exchange_return", "return.km.km", "测试 return 机制");
        // rabbitTemplate.convertAndSend("exchange_return", "return.km", "测试 return 机制");
    }

    // 配置 return 消息机制
    private final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        /**
         *  return 的回调方法（找不到路由才会触发）
         * @param message 消息的相关信息
         * @param i 错误状态码
         * @param s 错误状态码对应的文本信息
         * @param s1 交换机的名字
         * @param s2 路由的key
         */
        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {
            System.out.println(message);
            System.out.println(new String(message.getBody()));
            System.out.println(i);
            System.out.println(s);
            System.out.println(s1);
            System.out.println(s2);
        }
    };

}
```



# 延伸阅读


# 附录
1. 官网地址：[RabbitMQ](https://www.rabbitmq.com/getstarted.html)
2. 搭建RabbitMQ服务端教程：[CentOS8搭建RabbitMQ服务](https://www.cnblogs.com/andreamwu/p/14723420.html)
3. 知识来源：[SpringBoot2.x整合RabbitMQ（完整版）](https://blog.csdn.net/fan521dan/article/details/104912794)
