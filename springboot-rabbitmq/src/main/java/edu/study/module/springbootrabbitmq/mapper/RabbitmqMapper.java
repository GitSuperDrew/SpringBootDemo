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
