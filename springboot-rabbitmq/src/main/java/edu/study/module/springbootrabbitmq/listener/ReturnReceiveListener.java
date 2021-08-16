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
