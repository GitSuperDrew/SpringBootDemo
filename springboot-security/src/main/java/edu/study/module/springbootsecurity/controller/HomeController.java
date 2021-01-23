package edu.study.module.springbootsecurity.controller;

import edu.study.module.springbootsecurity.dao.SysUserDao;
import edu.study.module.springbootsecurity.entity.SysUser;
import edu.study.module.springbootsecurity.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zl
 * @date 2021/1/23 12:04
 **/
@Controller
@RequestMapping(value = "/")
public class HomeController {

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;
    @Resource
    SysUserDao sysUserDao;


    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reg.html");
        return modelAndView;
    }

    @PostMapping(value = "/logining")
    public ModelAndView logining(SysUser sysUser) {
        ModelAndView modelAndView = new ModelAndView();

        SysUser queryUser = SysUser.builder()
                .username(sysUser.getUsername())
                .accountNonExpired(false)
                .accountNonLocked(false)
                .credentialsNonExpired(false)
                .enabled(true)
                .build();
        List<SysUser> exitsUsers = sysUserDao.queryAll(queryUser);

        queryUser.setLastLoginTime(new Date());
        int num = sysUserDao.update(queryUser);
        if (num > 0) {
            System.out.println("更新最近登录时间成功！");
        }
        if (ObjectUtils.isEmpty(exitsUsers)) {
            modelAndView.setViewName("redirect:/login?error=true");
            return modelAndView;
        }

        String password = sysUser.getPassword();
        boolean flag = new BCryptPasswordEncoder().matches(password, exitsUsers.get(0).getPassword());
        if (flag) {
            modelAndView.setViewName("/main");
        } else {
            modelAndView.setViewName("redirect:/login?error=true");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index.html");
        return modelAndView;
    }

    /**
     * 用户注册 (注册用户测试URL示例：http://localhost:8080/reg?username=Drew22&password=Drew22&realName=Drew22)
     *
     * @param user 用户信息
     * @return 成功与否
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    public String reg(SysUser user) {
        System.out.println("用户信息：" + user);

        // 1. 处理输入的密码信息
        String pwd = user.getPassword();
        //加密，springSecurity现在默认要强制加密
        pwd = new BCryptPasswordEncoder().encode(pwd);
        user.setPassword(pwd);
        user.setCreateBy("admin");
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setEnabled(true);

        // 2.保存用户其他信息
        SysUser newUser = this.sysUserService.add(user);

        // 3.返回是否成功
        if (newUser.getId() != null) {
            return "注册成功";
        }
        return "注册失败";
    }
}
