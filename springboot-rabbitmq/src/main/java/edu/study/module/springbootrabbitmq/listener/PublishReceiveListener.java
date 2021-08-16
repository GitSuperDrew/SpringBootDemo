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
