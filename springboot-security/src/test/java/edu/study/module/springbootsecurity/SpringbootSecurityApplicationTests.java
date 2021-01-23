package edu.study.module.springbootsecurity;

import edu.study.module.springbootsecurity.dao.SysUserDao;
import edu.study.module.springbootsecurity.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootSecurityApplicationTests {

    @Resource
    SysUserDao sysUserDao;

    @Autowired
    SysUserService sysUserService;

    @Test
    void contextLoads() {
        System.out.println("用户ID=1为：\n" + sysUserDao.queryById(1));
        System.out.println("用户ID=3为：\n" + sysUserService.queryById(3));
    }

}
