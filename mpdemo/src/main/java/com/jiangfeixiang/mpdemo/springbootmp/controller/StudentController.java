package com.jiangfeixiang.mpdemo.springbootmp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jiangfeixiang.mpdemo.springbootmp.entity.Student;
import com.jiangfeixiang.mpdemo.springbootmp.service.IStudentService;
import com.jiangfeixiang.mpdemo.springbootmp.util.ExcelUtilPlus;
import com.jiangfeixiang.mpdemo.springbootmp.util.ExcelUtils;
import com.jiangfeixiang.mpdemo.springbootmp.util.PdfFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author zhongl
 * @since 2019-06-16
 */
@RestController
@RequestMapping("/springbootmp/student")
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    /**
     * （逻辑查询）所有的学生信息
     * URL：http://localhost:8888/springbootmp/student/getAllStudent
     *
     * @return 所有的未被逻辑删除的学生信息
     */
    @RequestMapping("/getAllStudent")
    public List<Student> getAllStudent() {
        List<Student> list = iStudentService.list();
        return list;
    }

    /**
     * 分页查询：根据名称模糊匹配
     *
     * @param currentPage 当前页码
     * @param pageCount   当前页显示条目数
     * @param orderBy     排序字段
     * @param isDesc      排序规则（true：降序，false：升序）
     * @return
     */
    @GetMapping(value = "pageAllByNameLike")
    public Map<String, Object> pageAllByNameLike(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                                 @RequestParam(value = "pageCount", defaultValue = "10") int pageCount,
                                                 @RequestParam(value = "orderBy") String orderBy,
                                                 @RequestParam(value = "isDesc", defaultValue = "true") boolean isDesc,
                                                 @RequestParam(value = "search", required = false) String search) {
        Page<Student> page = new Page(currentPage, pageCount);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        if (StringUtils.isNotBlank(search)) {
            Student student = JSONUtil.toBean(search, Student.class);
            if (student.getStuId() != null) {
                queryWrapper.eq("stu_id", student.getStuId());
            }
            if (StringUtils.isNotBlank(student.getStuName())) {
                queryWrapper.like("stu_name", student.getStuName());
            }
            if (StringUtils.isNotBlank(student.getStuSex())) {
                queryWrapper.eq("stu_sex", student.getStuSex());
            }
            if (student.getStuAge() != null) {
                queryWrapper.eq("stu_age", student.getStuAge());
            }
        }
        if (isDesc) {
            page.setDesc(orderBy);
        } else {
            page.setAsc(orderBy);
        }
        IPage<Student> iPage = iStudentService.page(page, queryWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", iPage.getRecords());
        result.put("page", ImmutableMap.builder()
                .put("currentPage", iPage.getCurrent())
                .put("pageCount", iPage.getSize())
                .put("orderBy", orderBy)
                .put("isDesc", isDesc)
                .put("totalPage", iPage.getPages())
                .put("totalCount", iPage.getTotal()).build());
        return result;
    }

    /**
     * (逻辑查询)根据学生ID，得到学生信息详情
     * URL：http://localhost:8888/springbootmp/student/getById?id=3
     *
     * @param id 学生ID
     * @return 学生信息
     */
    @GetMapping(value = "/getById")
    public Student getById(@RequestParam(value = "id", defaultValue = "1") int id) {
        return iStudentService.getById(id);
    }

    /**
     * 导出所有学生信息详情
     * URL：http://localhost:8888/springbootmp/student/exportAllStudent
     *
     * @return 是否导出成功
     */
    @GetMapping(value = "/exportAllStudent")
    public String exportAllStudent() {
        try {
            List<Student> students = iStudentService.list();
            Map<String, String> headerMap = ImmutableMap.of("stuId", "学生编号", "stuName", "学生姓名", "stuAge", "学生年龄",
                    "stuSex", "学生性别", "deleted", "数据有效性：0无效，1有效");
            ExcelUtilPlus.beanExport(UUID.fastUUID().toString(), students, headerMap);
            return "导出成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    /**
     * 基于Controller的导出
     *
     * @param response 回显
     * @return 是否导入成功
     */
    @GetMapping(value = "/exportStudent")
    public String exportStudent(HttpServletResponse response) {
        try {
            List<Student> students = iStudentService.list();
            Map<String, String> headerMap = ImmutableMap.of("stuId", "学生编号", "stuName", "学生姓名", "stuAge", "学生年龄",
                    "stuSex", "学生性别", "deleted", "数据有效性：1无效，0有效");
            String[] columnNames = Lists.newArrayList(headerMap.keySet()).toArray(new String[]{});
            //new String[]{"stuId", "stuName", "stuAge", "stuSex", "deleted"};
            String[] alias = Lists.newArrayList(headerMap.values()).toArray(new String[]{});
            //new String[]{"学生编号", "学生姓名", "学生年龄", "学生性别", "数据有效性：1无效，0有效"};
            ExcelUtils.export(response, UUID.fastUUID().toString(), students, columnNames, alias);
            return "导出成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    @GetMapping(value = "/print")
    public void pdfPrint(@RequestParam(required = false, value = "templateName") String templateName,
                         HttpServletRequest request,
                         HttpServletResponse response
//                         ,AgentPayDetailInfo agentForm
    ) throws Exception {


        //agentPayInfo  这个对象可以根据具体的需求换成你们自己的java对象，Text1-Text13，是pdf模板上空白处的表单的key值，通过该值可以用程序编辑pdf

        //组装模板所需数据HashMap
        HashMap<String, String> mapPDF = new HashMap<String, String>();
        mapPDF.put("Text1", new Date().toString());//交易时间
        mapPDF.put("Text2", "Drew");//付款方全称
        mapPDF.put("Text4", "中国银行-普通用户-King");//账户名称
        mapPDF.put("Text5", String.valueOf(233434987657865654L));//银行卡号
        mapPDF.put("Text6", "中国银行");//开户行
        mapPDF.put("Text3", "壹佰元整");//金额人民币大写  汉字
        mapPDF.put("Text7", "100.00");////金额人民币小写  数字
        mapPDF.put("Text8", "人名币");//账户类型
        mapPDF.put("Text9", "银行卡");//交易类型
        mapPDF.put("Text10", "");//用途
        mapPDF.put("Text11", "备注信息");//备注
        String receiptNumber = UUID.fastUUID().toString();
        mapPDF.put("Text12", "电子回单编号：" + receiptNumber);//电子回单编号
        mapPDF.put("Text13", String.valueOf(System.nanoTime()));//章子时间

//        mapPDF.put("Text1", DateUtil.getDateFormatYH(agentPayInfo.getFinishDate()));//交易时间
//        mapPDF.put("Text2", Constants.PAY_ONESELF_NAME);//付款方全称
//        mapPDF.put("Text3", NumberToCN.number2CNMontrayUnit(agentPayInfo.getAmount()));//金额人民币大写  汉字
//        mapPDF.put("Text4", agentPayInfo.getCardholder());//账户名称
//        mapPDF.put("Text5", agentPayInfo.getBankCardNo());//银行卡号
//        mapPDF.put("Text6", agentPayInfo.getBankName());//开户行
//        mapPDF.put("Text7", agentPayInfo.getAmount().toString());////金额人民币小写  数字
//        mapPDF.put("Text8", Constants.RMB);//账户类型
//        mapPDF.put("Text9", Constants.PAY_CERTIFICATE_TYPE);//交易类型
//        mapPDF.put("Text10", "");//用途
//        mapPDF.put("Text11", Constants.PAY_CERTIFICATE_REMARK);//备注
//        String receiptNumber = DateUtil.getFDate(agentPayInfo.getFinishDate()) + agentPayInfo.getOutOrderId() + agentPayInfo.getSerialNumber();
//        mapPDF.put("Text12", "电子回单编号：" + receiptNumber);//电子回单编号
//        mapPDF.put("Text13", DateUtil.getFormatDate(agentPayInfo.getFinishDate()));//章子时间

        //生成pdf
        InputStream pdfStream = this.print(templateName, mapPDF, request);

        ServletOutputStream op = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\""
                + new String(receiptNumber.getBytes("gb18030"), "ISO8859-1") + ".pdf" + "\"");

        int length = 0;
        byte[] bytes = new byte[1024];
        while ((pdfStream != null) && ((length = pdfStream.read(bytes)) != -1)) {
            op.write(bytes, 0, length);
        }
        op.close();
        response.flushBuffer();
    }

    /**
     * 打印，以PDF为模板
     *
     * @param templateName String 模板名字
     * @param map          模板数据HashMap
     * @return InputStream
     * @throws IOException
     */
    private InputStream print(String templateName, HashMap map, HttpServletRequest request) throws IOException {
        InputStream is = null;
        //服务器端PDF模板文件名
        //String merchId = getCurrentUser().getMerchId();
        String realPath = request.getSession().getServletContext().getRealPath("/");
//        String web_info_URL = PropertyUtils.getValue("WEB_INFO_URL");
//        String agentPayPath = PropertyUtils.getValue("PDF_PATH");
//        String url = realPath + web_info_URL + agentPayPath;// pdf
        String url = realPath;
        String templatePath = url + "/model/";//模板路径
        String serverPath = url + "/template/";//临时文件路径

        PdfFormatter.PdfFormater pdf = new PdfFormatter.PdfFormater(templatePath, serverPath, templateName, map);
        String PdfFilePath = pdf.doTransform();
        is = new FileInputStream(PdfFilePath);
        return is;
    }

    /**
     * 逻辑删除
     * 请使用Postman测试：http://localhost:8888/springbootmp/student/deleteById/4
     *
     * @param id 学生ID
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") int id) {
        return iStudentService.removeById(id) ? "删除成功" : "删除失败";
    }

}
