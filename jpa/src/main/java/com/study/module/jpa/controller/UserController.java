package com.study.module.jpa.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.study.module.jpa.entity.UserEntity;
import com.study.module.jpa.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ===================== 👇👇👇 只提取数据 👇👇👇 =============================

    /**
     * URL: http://localhost:8888/user/findAll
     *
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    // ==================== 👆👆👆 只提取数据 👆👆👆 =============================

    @ResponseBody
    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        List<UserEntity> users = userService.findAll();
        /* 通过工具类创建writer，默认创建xls格式 */
        ExcelWriter writer = ExcelUtil.getWriter();

        /* 自定义标题别名 */
        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("password", "密码");

        /* 合并单元格后的标题行，使用默认标题样式 */
        String excelName = "User Info";
        writer.merge(2, excelName);

        /* 一次性写出内容，使用默认样式，强制输出标题 */
        writer.write(users, true);

        /* out为OutputStream，需要写出到的目标流 */
        /* response为HttpServletResponse对象 */
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        /* test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码 */
        String sheetName = "User Info";
        response.setHeader("Content-Disposition", "attachment;filename=" + sheetName + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /* 关闭writer，释放内存 */
            writer.close();
        }
        /* 此处记得关闭输出Servlet流 */
        IoUtil.close(out);
    }
}
