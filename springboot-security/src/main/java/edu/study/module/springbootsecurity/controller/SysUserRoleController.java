package edu.study.module.springbootsecurity.controller;

import edu.study.module.springbootsecurity.entity.SysUserRole;
import edu.study.module.springbootsecurity.service.SysUserRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (SysUserRole)表控制层
 *
 * @author drew
 * @since 2021-01-23 11:12:55
 */
@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUserRole selectOne(Integer id) {
        return this.sysUserRoleService.queryById(id);
    }

}