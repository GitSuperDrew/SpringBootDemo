package com.study.module.mybatisoracle.controller;

import com.study.module.mybatisoracle.entity.SysLog;
import com.study.module.mybatisoracle.service.SysLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统日志(SysLog)表控制层
 *
 * @author makejava
 * @since 2020-05-10 16:40:19
 */
@RestController
@RequestMapping("sysLog")
public class SysLogController {
    /**
     * 服务对象
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 通过主键查询单条数据
     * URL: http://localhost:8088/sysLog/selectOne?id=609
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysLog selectOne(Double id) {
        return this.sysLogService.queryById(id);
    }

}