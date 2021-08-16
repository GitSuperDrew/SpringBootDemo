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
