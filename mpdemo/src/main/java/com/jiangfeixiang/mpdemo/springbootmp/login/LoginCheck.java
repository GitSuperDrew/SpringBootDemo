package com.jiangfeixiang.mpdemo.springbootmp.login;

import com.jiangfeixiang.mpdemo.springbootmp.util.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * 登录控制层
 *
 * @author Administrator
 * @date 2020/6/20 上午 10:30
 */
@RestController
@RequestMapping("/springbootmp/check")
public class LoginCheck {

    /**
     * 获取验证码
     * URL: http://localhost:8888/springbootmp/check/images/imageCode
     *
     * @param request  请求URL
     * @param response 返回结果
     * @return 验证码图片
     */
    @GetMapping("/images/imageCode")
    public String imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream os = response.getOutputStream();
        Map<String, Object> map = ImageCode.getImageCode(85, -1, 6, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", System.currentTimeMillis());
        try {
            boolean flag = ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
            if (flag) {
                System.out.println("生成验证码成功");
            }
        } catch (IOException e) {
            System.out.println("生成验证码出现异常");
            return "";
        }
        System.out.println("生成验证码成功，验证码为：" + map.get("strEnsure"));
        return null;
    }

    /**
     * 根据用户的输入（验证码有效期5分钟内），在与上面的session保存的数据进行比较，如果数据相同即可确定验证通过。
     * URL: http://localhost:8888/springbootmp/check/checkCode?checkCode=1594【通过上面/images/imageCode接口获取验证码的接口得到的，在页面上查看】
     *
     * @param request URL请求
     * @param session 服务器session域
     * @return 是否验证成功
     */
    @RequestMapping(value = "/checkCode")
    public String checkCode(HttpServletRequest request, HttpSession session) {
        String checkCode = request.getParameter("checkCode");
        // 验证码对象
        Object simple = session.getAttribute("simpleCaptcha");
        if (simple == null) {
            request.setAttribute("errorMsg", "验证码已经失效，请重新输入！");
            return "验证码已经失效，请重新输入!";
        }
        String captcha = simple.toString();
        Date now = new Date();
        long codeTime = Long.parseLong(String.valueOf(session.getAttribute("codeTime")));
        if (StringUtils.isEmpty(checkCode) || !(checkCode.equalsIgnoreCase(captcha))) {
            request.setAttribute("errorMsg", "验证码错误！");
            return "验证码错误！";
        } else if ((now.getTime() - codeTime) / 1000 / 60 > 5) {
            // 验证码有效时间为5分钟
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return "验证码已失效，请重新输入！";
        } else {
            session.removeAttribute("simpleCaptcha");
            return "1";
        }
    }


}
