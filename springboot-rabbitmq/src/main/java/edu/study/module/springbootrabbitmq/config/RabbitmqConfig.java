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
