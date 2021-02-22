package edu.study.module.springbootquartz.controller;

import edu.study.module.springbootquartz.service.TaskSchedulerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zl
 * @date 2021/2/22 19:53
 **/
@RestController
@RequestMapping("/quartz")
public class QuartzController {
    @Resource
    private TaskSchedulerService taskService;

    @GetMapping("/pause")
    public Object pauseJob(String name, String group) {
        taskService.pauseTask(name, group);
        return "success";
    }

    @GetMapping("/resume")
    public Object resumeJob(String name, String group) {
        taskService.resumeTask(name, group);
        return "success";
    }
}
