package com.study.module.controller;

import com.study.module.annotation.Log;
import com.study.module.entity.Stu;
import com.study.module.enums.BusinessType;
import com.study.module.service.StuService;
import com.study.module.util.office.CsvUtil;
import com.study.module.util.office.ExcelUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * (Stu)表控制层
 *
 * @author drew
 * @since 2021-01-04 16:26:22
 */
@RestController
@RequestMapping("/stu")
public class StuController {
    /**
     * 服务对象
     */
    @Resource
    private StuService stuService;

    /**
     * 读取CSV文件
     *
     * @param multipartFile 文件
     * @return JSON Data
     */
    @PostMapping("/readCSV")
    public Map<String, Object> readCSV(@RequestParam(value = "multipartFile") MultipartFile multipartFile) {
        return CsvUtil.readCsv(multipartFile, true);
    }

    @GetMapping("exportDbToExcel")
    @ResponseBody
    public ModelAndView downloadFile(HttpServletResponse response) {
        ExcelUtil.testExportExcel(response);
        return null;
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    @Log(title = "system-student-selectOne", businessType = BusinessType.OTHER)
    public Stu selectOne(@RequestParam(value = "id") Long id) {
        return this.stuService.queryById(id);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/getById/{id}")
    @Log(title = "system-student-getById", businessType = BusinessType.OTHER)
    public Stu getById(@PathVariable(value = "id") Long id) {
        return this.stuService.queryById(id);
    }


    @PostMapping(value = "/add")
    @Log(title = "system-student-addStu", businessType = BusinessType.INSERT)
    public int addStu(@RequestBody Stu stu) {
        if (ObjectUtils.isEmpty(stu)) {
            return -1;
        }
        return 1;
    }

    @PutMapping("/update")
    @Log(title = "system-student-updateStu", businessType = BusinessType.UPDATE)
    public int updateStu(@RequestBody Stu stu) {
        if (ObjectUtils.isEmpty(stu)) {
            return -1;
        }
        return 1;
    }

    @DeleteMapping("/{id}")
    @Log(title = "system-student-deleteStu", businessType = BusinessType.DELETE)
    public int deleteStu(@PathVariable(name = "id") Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return -1;
        }
        return 1;
    }

}