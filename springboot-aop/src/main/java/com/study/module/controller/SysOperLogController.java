package com.study.module.controller;

import com.study.module.entity.SysOperLog;
import com.study.module.service.SysOperLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 操作日志记录(SysOperLog)表控制层
 *
 * @author drew
 * @since 2021-01-19 18:38:06
 */
@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController {
    /**
     * 服务对象
     */
    @Resource
    private SysOperLogService sysOperLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public SysOperLog selectOne(Long id) {
        return this.sysOperLogService.queryById(id);
    }

}