package com.study.module.util.watermark;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlObject;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * docx文档加水印（docx文档的水印必须实在最底层，所以可能有些图片遮住部分水印内容）
 *
 * @author drew
 * @date 2021/1/30 22:20
 **/
public class DocWaterMarkUtil {

    public static void main(String[] args) throws Exception {
        String docFilePath = "D:\\doc-0.docx";
        String outputFilePath = "D:\\doc-0-" + UUID.randomUUID().toString().replace("-", "") + ".docx";

        // 文字水印：doc每页单条水印 (POI 实现多条docx文档水印比较繁琐：见：https://blog.csdn.net/z172989496/article/details/110492356)
         addSingleWaterMark(docFilePath, outputFilePath, "机密文档,请勿分享", "#C5CBCF", 100D);

    }

    /**
     * word文字水印(多条水印)
     *
     * @param inputPath 源文件路径
     * @param outPath   水印后的文件路径
     * @param markStr   水印内容
     */
    public static void setWordWaterMark(String inputPath, String outPath, String markStr) {
        File inputFile = new File(inputPath);
        //2003doc 用HWPFDocument  ； 2007doc 用 XWPFDocument
        XWPFDocument doc = null;
        try {
            // 延迟解析比率
            // ZipSecureFile.setMinInflateRatio(-1.0d);
            doc = new XWPFDocument(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
//            throw new MyException(ResultCode.FAILURE, "源文件不存在");
        } catch (IOException e) {
//            throw new MyException(ResultCode.FAILURE, "读取源文件IO异常");
        } catch (Exception e) {
//            throw new MyException(ResultCode.FAILURE, "不支持的文档格式");
        }
        assert doc != null;
        XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
        if (headerFooterPolicy == null) {
            headerFooterPolicy = doc.createHeaderFooterPolicy();
        }
        //添加水印
        headerFooterPolicy.createWatermark(markStr);
        File file = new File(outPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
//                throw new MyException(ResultCode.FAILURE, "创建输出文件失败");
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(outPath);
            doc.write(os);
        } catch (FileNotFoundException e) {
//            throw new MyException(ResultCode.FAILURE, "创建输出文件失败");
        } catch (Exception e) {
//            throw new MyException(ResultCode.FAILURE, "添加文档水印失败");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 添加水印（每页单条水印）
     *
     * @param docFilePath 需要添加水印的word文档
     * @param watermark   水印文字内容
     * @param color       水印的颜色
     * @param height      水印文字的高度
     * @throws Exception
     */
    public static void addSingleWaterMark(String docFilePath, String outputFilePath, String watermark,
                                          String color, double height) throws Exception {
        System.out.println("[>>> [开始]对 docx 文档添加文字水印……");
        //2003doc 用HWPFDocument  ； 2007doc 用 XWPFDocument
        XWPFDocument doc = new XWPFDocument(new FileInputStream(docFilePath));
        XWPFHeaderFooterPolicy headerFooterPolicy = doc.createHeaderFooterPolicy();
        //添加文字水印
        headerFooterPolicy.createWatermark(watermark);
        XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.getCTP().newCursor();
        XmlObject[] xmlObjects = paragraph.getCTP().getRArray(0).getPictArray(0)
                .selectChildren(new QName("urn:schemas-microsoft-com:vml", "shape"));
        if (xmlObjects.length > 0) {
            com.microsoft.schemas.vml.CTShape ctShape = (com.microsoft.schemas.vml.CTShape) xmlObjects[0];
            //设置水印颜色
            ctShape.setFillcolor(color);
            //修改水印样式
            ctShape.setStyle(getWaterMarkStyle(ctShape.getStyle(), height) + ";rotation:315");
        }
        doc.write(new FileOutputStream(outputFilePath));
        doc.close();
        System.out.println("[>>> [结束]对 docx 文档添加文字水印……");
    }

    /**
     * 修改水印样式（水印的字体大小）
     *
     * @param styleStr 水印的原样式
     * @param height   水印文字高度
     * @return
     */
    public static String getWaterMarkStyle(String styleStr, double height) {
        Pattern p = Pattern.compile(";");
        String[] strs = p.split(styleStr);
        for (String str : strs) {
            if (str.startsWith("height:")) {
                String heightStr = "height:" + height + "pt";
                styleStr = styleStr.replace(str, heightStr);
                break;
            }
        }
        return styleStr;
    }

}
