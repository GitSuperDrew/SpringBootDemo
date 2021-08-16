package edu.study.module.springbootrabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 测试使用：RabbitMQ 简单工作模式（hello world）
 * <pre>
 *     1.创建链接工厂；
 *     2.创建链接Connection
 *     3.通过链接获取通道Channel
 *     4.通过创建交换机，声明队列，绑定关系，路由key，发送消息，和接受消息；
 *     5.准备消息内容
 *     6.发送消息给队列queue
 *     7.关闭链接
 *     8.关闭通道
 * </pre>
 *
 * @author zl
 * @create 2021-08-16 11:06
 */
public class RabbitmqDemoConsumer {
    public static void main(String[] args) {
        // 1. 创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.174.156");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setConnectionTimeout(6000);
        connectionFactory.setVirtualHost("/");
        // 2.创建链接
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("生产者");
            channel = connection.createChannel();
            String queueName = "queue1";
            channel.basicConsume("queue1", true,
                    (s, delivery) -> System.out.println("收到消息：" + new String(delivery.getBody(), "UTF-8")),
                    s -> System.out.println("接受失败了……"));

            System.out.println("消息发送成功。。。");
            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }

            // 关闭链接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
