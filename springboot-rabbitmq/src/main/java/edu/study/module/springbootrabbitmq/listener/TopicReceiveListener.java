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
