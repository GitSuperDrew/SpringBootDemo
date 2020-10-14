package com.study.module.springbooteasyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.module.springbooteasyexcel.entity.AssetsType;
import com.study.module.springbooteasyexcel.model.AssetsTypeExcelModel;
import com.study.module.springbooteasyexcel.service.AssetsTypeService;
import com.study.module.springbooteasyexcel.util.ValidationUtils;
import com.study.module.springbooteasyexcel.vo.AssetsTypeExcelVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 固定资产分类代码（国标 GB/T 14885-2010）(AssetsType)表控制层
 *
 * @author makejava
 * @since 2020-10-13 11:00:52
 */
@Slf4j
@RestController
@Transactional(rollbackFor = {Exception.class})
@RequestMapping("assetsType")
public class AssetsTypeController {
    /**
     * 服务对象
     */
    @Resource
    private AssetsTypeService assetsTypeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("getById")
    public AssetsType getById(@RequestParam(value = "id") Long id) {
        return this.assetsTypeService.queryById(id);
    }

    /**
     * 导入固定资产数据
     *
     * @param file excel文件数据
     * @return
     */
    @PostMapping(value = "/v2/importExcel")
    @Transactional(rollbackFor = {Exception.class})
    public AssetsTypeExcelVO importExcelV2(@RequestParam("file") MultipartFile file) {
        List<AssetsTypeExcelModel> list = null;
        List<AssetsTypeExcelModel> fail = new ArrayList<>();
        AssetsTypeExcelVO assetsTypeExcelVO = new AssetsTypeExcelVO();
        try {
            list = EasyExcel
                    .read(file.getInputStream(), AssetsTypeExcelModel.class, new ModelExcelListener())
                    .sheet()
                    .doReadSync();
            list.forEach(data -> {
                Set<ConstraintViolation<AssetsTypeExcelModel>> violations =
                        ValidationUtils.getValidator().validate(data);
                if (violations.size() > 0) {
                    fail.add(data);
                }
            });
            assetsTypeExcelVO.setFail(fail);
            list.removeAll(fail);
            assetsTypeExcelVO.setSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("导入的数据如下：\n" + list);
        // 判断资产数据表中是否存在数据，如果无数据，则将读取到的数据按照数据库中表结构存入数据库表（assets_type）中
        if (assetsTypeService.hasData() != null && !assetsTypeService.hasData()) {
            this.saveAssetsType(list);
        }
        return assetsTypeExcelVO;
    }

    public static class ModelExcelListener extends AnalysisEventListener<AssetsTypeExcelModel> {
        private List<AssetsTypeExcelModel> datas = new ArrayList<>();

        /**
         * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
         */
        @Override
        public void invoke(AssetsTypeExcelModel data, AnalysisContext context) {
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            log.info("读取到数据{}", data);
            datas.add(data);
            //根据业务自行处理，可以写入数据库等等
        }

        /**
         * 所以的数据解析完了调用
         *
         * @param context
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            log.info("所有数据解析完成");
        }
    }

    /**
     * 将数据（固定资产）保存到数据库
     *
     * @param list 固定资产集合
     */
    private void saveAssetsType(List<AssetsTypeExcelModel> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        try {
            // 将所有的数据先全部插入到数据库
            for (AssetsTypeExcelModel model : list) {
                String code = model.getAssetsCode();
                String name = model.getAssetsName();
                AssetsType assetsType = new AssetsType();
                assetsType.setAssetsCode(code);
                assetsType.setAssetsName(name);
                if (code.endsWith("0000")) {
                    assetsType.setParentCode("0");
                }
                if (code.endsWith("00") && !code.endsWith("0000")) {
                    assetsType.setParentCode(code.substring(0, 3).concat("0000"));
                } else {
                    assetsType.setParentCode(code.substring(0, 5).concat("00"));
                }
                assetsType.setRevision(1);
                assetsType.setCreatedBy("Drew");
                assetsType.setCreatedTime(new Date());
                // 插入到数据库
                assetsTypeService.insert(assetsType);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 手动回滚（Spring）
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
