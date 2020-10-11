package com.study.module.springbooteasyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.module.springbooteasyexcel.entity.Poet;
import com.study.module.springbooteasyexcel.model.PoetExcelModel;
import com.study.module.springbooteasyexcel.service.PoetService;
import com.study.module.springbooteasyexcel.util.ValidationUtils;
import com.study.module.springbooteasyexcel.vo.PoetExcelVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * (Poet)表控制层
 *
 * @author makejava
 * @since 2020-10-11 14:17:35
 */
@Slf4j
@RestController
@RequestMapping("/poet")
public class PoetController {
    /**
     * 服务对象
     */
    @Resource
    private PoetService poetService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Poet selectOne(Integer id) {
        return this.poetService.queryById(id);
    }


    /**
     * 下载模板
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/easyexcel.xls");
        InputStream inputStream = classPathResource.getInputStream();
        Workbook workbook = new HSSFWorkbook(inputStream);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("easyexcel.xls", "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 下载模板
     */
    @PostMapping("/test")
    public String test(@RequestBody List<String> listString) {

        return listString.toString();
    }

    /**
     * 导出数据
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        String[] columnNames = {"名称", "年龄", "手机号", "性别"};

        Sheet sheet = workbook.createSheet();
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(0);

        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }
        //模拟构造数据
        List<PoetExcelModel> dataList = new ArrayList<>();
        dataList.add(new PoetExcelModel("张三", 12, "13867098765", "男"));
        dataList.add(new PoetExcelModel("张三1", 12, "13867098765", "男"));
        dataList.add(new PoetExcelModel("张三2", 12, "13867098765", "男"));
        dataList.add(new PoetExcelModel("张三3", 12, "13867098765", "男"));

        //创建数据行并写入值
        for (int j = 0; j < dataList.size(); j++) {
            PoetExcelModel userExcelModel = dataList.get(j);
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(userExcelModel.getName());
            dataRow.createCell(1).setCellValue(userExcelModel.getAge());
            dataRow.createCell(2).setCellValue(userExcelModel.getMobile());
            dataRow.createCell(3).setCellValue(userExcelModel.getSex());
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("easyexcel.xls", "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/readExcel")
    public List<PoetExcelModel> readExcel(@RequestParam("file") MultipartFile file) {
        List<PoetExcelModel> list = new ArrayList<>();
        try {
            list = EasyExcel.read(file.getInputStream(), PoetExcelModel.class, new ModelExcelListener()).sheet().doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping("/importExcel")
    public PoetExcelVO importExcel(@RequestParam("file") MultipartFile file) {
        List<PoetExcelModel> list = null;
        List<PoetExcelModel> fail = new ArrayList<>();
        PoetExcelVO poetExcelVO = new PoetExcelVO();
        String mobieReg = "^[1][3,4,5,7,8][0-9]{9}$$";
        try {
            list = EasyExcel.read(file.getInputStream(), PoetExcelModel.class, new ModelExcelListener()).sheet().doReadSync();

            list.forEach(data -> {
                //处理姓名的校验
                if (StringUtils.isEmpty(data.getName()) || data.getName().length() > 4) {
                    fail.add(data);
                    return;
                }
                //处理手机号的校验
                if (StringUtils.isEmpty(data.getMobile()) || !data.getMobile().matches(mobieReg)) {
                    fail.add(data);
                    return;
                }
                //以下根据字段多少可能有n个if
            });
            poetExcelVO.setFail(fail);
            list.removeAll(fail);
            poetExcelVO.setSuccess(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return poetExcelVO;
    }


    @PostMapping("/v2/importExcel")
    public PoetExcelVO importExcelV2(@RequestParam("file") MultipartFile file) {
        List<PoetExcelModel> list = null;
        List<PoetExcelModel> fail = new ArrayList<>();
        PoetExcelVO poetExcelVO = new PoetExcelVO();
        try {
            list = EasyExcel.read(file.getInputStream(), PoetExcelModel.class, new ModelExcelListener()).sheet().doReadSync();
            list.forEach(data -> {
                Set<ConstraintViolation<PoetExcelModel>> violations = ValidationUtils.getValidator().validate(data);
                if (violations.size() > 0) {
                    fail.add(data);
                }
            });
            poetExcelVO.setFail(fail);
            list.removeAll(fail);
            poetExcelVO.setSuccess(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return poetExcelVO;
    }


    public static class ModelExcelListener extends AnalysisEventListener<PoetExcelModel> {
        private List<PoetExcelModel> datas = new ArrayList<>();

        /**
         * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
         */
        @Override
        public void invoke(PoetExcelModel data, AnalysisContext context) {
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            log.info("读取到数据{}", data);
            datas.add(data);
            //根据业务自行处理，可以写入数据库等等
        }

        //所以的数据解析完了调用
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            log.info("所有数据解析完成");
        }
    }

}
