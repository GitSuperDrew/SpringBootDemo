package com.study.module.util.watermark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 添加水印工具类（针对已经添加了水印的文件再次加水印，将出现异常）
 *
 * @author drew
 * @date 2021/1/30 11:38
 **/
@Slf4j
public class ExcelWaterMarkUtil {

    public static void main(String[] args) throws Exception {
        String srcFilePath = "D:\\file55.xlsx";
        String targetFilePath = "D:\\file5.xlsx";
         setWaterMark(srcFilePath, targetFilePath, WaterMarkLogo.builder().text("保密资料").dateFormat(DATETIME).color("#C5CBCF").enable(true).build());
//        setWaterMark(srcFilePath, targetFilePath, WaterMarkLogo.builder().build());
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WaterMarkLogo {
        // 水印内容
        private String text;
        // 水印时间
        private Date date;
        // 水印时间格式（如常见的有：yyyy-MM-dd, yyy-MM-dd HH:mm）
        private String dateFormat;
        // 水印颜色（十六进颜色值，例如：#C5CBCF）
        private String color;
        // 是否开启水印
        private Boolean enable;
    }

    public static final String DATETIME = "yyyy-MM-dd HH:mm";
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 对源文件进行水印，并输出加水印文件
     *
     * @param srcFilePath    目标文件存放地址（例如："D:\\file_src.xlsx"）
     * @param targetFilePath 输出加水印后的文件存放地址（例如："D:\\file_out.xlsx"）
     * @param logo           水印logo（例如：WaterMarkLogo.builder().text("内部资料").dateFormat("yyyy-MM-dd").color("#C5CBCF").build()）
     * @return 同targetFilePath
     */
    public static String setWaterMark(String srcFilePath, String targetFilePath, WaterMarkLogo logo) {
        log.info("[>>> 开始对 excel 文件添加水印……");
        File inFile = new File(srcFilePath);
        File outFile = new File(targetFilePath);
        try (FileInputStream is = new FileInputStream(inFile); FileOutputStream out = new FileOutputStream(outFile)) {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            BufferedImage image = ExcelWaterMarkUtil.createWatermarkImage(logo);
            // 导出到字节流B
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            int pictureIdx = workbook.addPicture(os.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            POIXMLDocumentPart poixmlDocumentPart = workbook.getAllPictures().get(pictureIdx);
            // 对每一个sheet添加水印
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //获取每个Sheet表
                XSSFSheet sheet = workbook.getSheetAt(i);
                PackagePartName ppn = poixmlDocumentPart.getPackagePart().getPartName();
                String relType = XSSFRelation.IMAGES.getRelation();
                //add relation from sheet to the picture data
                PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
                //set background picture to sheet
                sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
            }
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加水印发生异常：\t", e);
        }
        log.info("[>>> 结束对 excel 文件添加水印……");
        return targetFilePath;
    }


    /**
     * 创建水印logo图片
     *
     * @param watermark 如果传入一个 new WaterMarkLogo() 空对象，则存在默认值。
     * @return 图片字节流
     */
    public static BufferedImage createWatermarkImage(WaterMarkLogo watermark) {
        if (watermark == null) {
            watermark = WaterMarkLogo.builder().enable(true).text("内部资料").color("#C5CBCF").dateFormat(DATETIME).build();
        } else {
            if (ObjectUtils.isEmpty(watermark.getDateFormat())) {
                watermark.setDateFormat(DATETIME);
            } else if (watermark.getDateFormat().length() == 16) {
                watermark.setDateFormat(DATETIME);
            } else if (watermark.getDateFormat().length() == 10) {
                watermark.setDateFormat(DATE);
            }
            if (ObjectUtils.isEmpty(watermark.getText())) {
                watermark.setText("内部资料");
            }
            if (ObjectUtils.isEmpty(watermark.getColor())) {
                watermark.setColor("#C5CBCF");
            }
        }
        String[] textArray = watermark.getText().split("\n");
        Font font = new Font("microsoft-yahei", Font.PLAIN, 20);
        int width = 300, height = 100;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 背景透明 开始
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        // 背景透明 结束
        g = image.createGraphics();
        // 设定画笔颜色
        g.setColor(new Color(Integer.parseInt(watermark.getColor().substring(1), 16)));
        // 设置画笔字体
        g.setFont(font);
        // 设定倾斜度
        g.shear(0.1, -0.26);

        // 设置字体平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = 50;
        for (String s : textArray) {
            // 画出字符串
            g.drawString(s, 0, y);
            y = y + font.getSize();
        }
        // 画出字符串(获取客户端的当前时间，可以前端传递当前时间给到此处)
        // g.drawString(DateUtils.getNowDateFormatCustom(watermark.getDateFormat()), 0, y);
        g.drawString(new SimpleDateFormat(watermark.getDateFormat()).format(new Date()), 0, y);
        // 释放画笔
        g.dispose();
        return image;
    }

}
