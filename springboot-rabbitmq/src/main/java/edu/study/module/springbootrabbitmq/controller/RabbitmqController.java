package edu.study.module.springbootrabbitmq.controller;

import edu.study.module.springbootrabbitmq.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类入口
 * <pre>
 *     1. 工作模型（work）
 *     2. 发布-订阅模型（publisher/subscribe）
 *     3. 主题-订阅模型（Topic）
 *     4. confirm 模型（confirm）
 *     5. return 模型（return）
 * </pre>
 * @author zl
 * @create 2021-08-16 8:13
 */
@RestController
public class RabbitmqController {

    @Autowired
    private RabbitmqService rabbitmqService;

    // 工作模型（work）
    @RequestMapping("/sendWork")
    public Object sendWork() {
        rabbitmqService.sendWork();
        return "[work]发送成功……";
    }


    // 发布订阅模型（publish-subscribe）
    @RequestMapping("/sendPublish")
    public String sendPublish() {
        rabbitmqService.sendPublish();
        return "[publis-subscribe]发送成功……";
    }

    // 主题模型（Topic）
    @RequestMapping("/sendTopic")
    public String sendTopic(){
        rabbitmqService.sendTopic();
        return "[topic]发送成功……";
    }

    // 消息确认模型（confirm机制）
    @RequestMapping("/sendConfirm")
    public String sendConfirm() {
        rabbitmqService.sendConfirm();
        return "[confirm机制]发送成功……";
    }

    // return机制模型（return机制）
    @RequestMapping("/sendReturn")
    public String sendReturn(){
        rabbitmqService.sendReturn();
        return "[return机制]发送成功……";
    }

}
