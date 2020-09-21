package com.study.module.mybatis.controller;

import com.study.module.mybatis.entity.WebLog;
import com.study.module.mybatis.service.WebLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 系统日志表(WebLog)表控制层
 *
 * @author makejava
 * @since 2020-09-21 21:31:03
 */
@Api(tags = "日志管理", value = "系统管理员查看日志相关内容")
@RestController
@RequestMapping("webLog")
public class WebLogController {
    /**
     * 服务对象
     */
    @Resource
    private WebLogService webLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiIgnore
    @GetMapping("selectOne")
    public WebLog selectOne(Long id) {
        return this.webLogService.queryById(id);
    }

    /**
     * 获取日志详情
     * 实例：http://localhost:8089/webLog/findById?id=5
     *
     * @param id 日志ID
     * @return 日志详情
     */
    @ApiOperation(value = "查看日志详情")
    @ApiImplicitParam(value = "id", name = "id", dataType = "Long", defaultValue = "1")
    @GetMapping(value = "/findById")
    public WebLog findById(@RequestParam(value = "id") Long id) {
        return this.webLogService.queryById(id);
    }

}
