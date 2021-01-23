package edu.study.module.springbootsecurity.controller;

import edu.study.module.springbootsecurity.service.SysPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysPermissionControllerTest {

    @Autowired
    SysPermissionService sysPermissionService;

    @Test
    void selectOne() {
        System.out.println("权限系统：\n" + sysPermissionService.queryById(1));
    }
}