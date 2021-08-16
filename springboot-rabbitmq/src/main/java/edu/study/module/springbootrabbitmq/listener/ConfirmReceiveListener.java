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
