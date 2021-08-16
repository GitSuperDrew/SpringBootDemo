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
