package edu.study.module.springbootsecurity.controller;

import edu.study.module.springbootsecurity.entity.SysRolePermission;
import edu.study.module.springbootsecurity.service.SysRolePermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (SysRolePermission)表控制层
 *
 * @author drew
 * @since 2021-01-23 11:12:56
 */
@RestController
@RequestMapping("sysRolePermission")
public class SysRolePermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRolePermission selectOne(Integer id) {
        return this.sysRolePermissionService.queryById(id);
    }

}