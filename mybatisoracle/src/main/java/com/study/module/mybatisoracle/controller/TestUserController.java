package com.study.module.mybatisoracle.controller;

import com.study.module.mybatisoracle.entity.TestUser;
import com.study.module.mybatisoracle.service.TestUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TestUser)表控制层
 *
 * @author makejava
 * @since 2020-05-11 12:02:15
 */
@RestController
@RequestMapping("testUser")
public class TestUserController {
    /**
     * 服务对象
     */
    @Resource
    private TestUserService testUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TestUser selectOne(Double id) {
        return this.testUserService.queryById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public TestUser save() {
        TestUser testUser = new TestUser();
        testUser.setName("JD");
        testUser.setPhone("18797730122");
        return testUserService.insert(testUser);
    }


}