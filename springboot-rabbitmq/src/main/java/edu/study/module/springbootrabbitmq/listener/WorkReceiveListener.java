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
