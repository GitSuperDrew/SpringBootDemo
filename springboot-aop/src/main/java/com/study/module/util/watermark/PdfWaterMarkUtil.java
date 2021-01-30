package com.study.module.util.watermark;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 基本满足对PDF做背景水印的要求(文字水印 / 图片水印)
 *
 * @author drew
 * @date 2021/1/30 14:28
 **/
public class PdfWaterMarkUtil {

    public static void main(String[] args) throws IOException, DocumentException {
        String inputFile = "D:\\src.pdf";
        String outputFile = "D:\\watermark-src-" + UUID.randomUUID().toString().replace("-", "") + ".pdf";

        // 背景单个文字水印
        // waterMark(inputFile, outputFile, "保密资料");

        // 文字---方法一：背景多个文字水印(1)
        // waterMark(outputFile, inputFile, "D:\\logo.png", "保密资料", 1);
        // 文字---方法二：背景多个文字水印(plus)
        // waterMarkPlus(outputFile, inputFile, "保密资料");
        // 文字---方法三：多文字水印
        // textWatermark(inputFile, outputFile, "内部资料，请勿向外分享");

        // 图片---方法一：多图片水印
         imageWatermark(inputFile, outputFile, "D:\\logo.png");

        // 页脚水印
        // setPageFootWaterMarkInfo(outputFile, inputFile, "制作:DREW");

    }

    /**
     * 知识来源：https://my.oschina.net/u/4353493/blog/3901174  <br/>
     * 对PDF添加多个水印
     *
     * @param outputFilePath 输出文件的位置（例如：”D:\\demo-no-paging-watermark.pdf“）
     * @param inputFilePath  原PDF位置（例如：”D:\\demo-no-paging.pdf“）
     * @param text           水印内容(例如："公司内部文件，请注意保密！")
     * @param permission     权限码
     * @param logoPath       水印图片logo地址（例如：”D:\\logo.png“）
     * @throws DocumentException
     * @throws IOException
     */
    public static void waterMark(String outputFilePath, String inputFilePath, String logoPath,
                                 String text, int permission) throws DocumentException, IOException {
        System.out.println("[>>> [开始]给 PDF 文件添加水印……");
        PdfReader reader = new PdfReader(inputFilePath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath));

        PdfContentByte content;
        Rectangle pageSizeWithRotation;
        // 基础字体
        BaseFont base = PdfWaterMarkUtil.setBaseFont();
        // 文字间隔
        int interval = -5;
        // 获取水印文字的高度和宽度
        int textH = 0, textW = 0;
        JLabel label = new JLabel();
        label.setText(text);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        textH = metrics.getHeight();
        textW = metrics.stringWidth(label.getText());
        System.out.println("textH: " + textH + "\t textW: " + textW);

        PdfGState gs = new PdfGState();
        int total = reader.getNumberOfPages() + 1;
        for (int i = 1; i < total; i++) {
            // 设置水印透明度
            gs.setFillOpacity(0.3F);
            gs.setStrokeOpacity(0.3F);

            // 在内容上方加水印 (content = stamper.getUnderContent(i);//在内容下方加水印)
            content = stamper.getOverContent(i);
            content.saveState();
            content.setGState(gs);

            content.beginText();
            // 设置水印文字：颜色、大小、位置
            content.setColorFill(BaseColor.GRAY);
            content.setFontAndSize(base, 10);
            content.setTextMatrix(70, 200);


            // 获取每一页的高度、宽度
            pageSizeWithRotation = reader.getPageSizeWithRotation(i);
            float pageHeight = pageSizeWithRotation.getHeight();
            float pageWidth = pageSizeWithRotation.getWidth();

            // 根据纸张大小多次添加， 水印文字成30度角倾斜
            for (int height = interval + textH; height < pageHeight; height = height + textH * 3) {
                for (int width = interval + textW; width < pageWidth + textW; width = width + textW * 2) {
                    content.showTextAligned(Element.ALIGN_LEFT, text, width - textW, height - textH, 30);
                }
            }

            content.endText();
            addPageFootWaterContent(content, base, text);
        }
        stamper.close();
        reader.close();
        System.out.println("[>>> [结束]给 PDF 文件添加水印……");
    }


    /**
     * 知识来源：https://my.oschina.net/u/4353493/blog/3901174  <br/>
     * 对PDF添加多个水印
     *
     * @param outputFilePath 输出文件的位置（例如：”D:\\demo-no-paging-watermark.pdf“）
     * @param inputFilePath  原PDF位置（例如：”D:\\demo-no-paging.pdf“）
     * @param text           水印内容(例如："公司内部文件，请注意保密！")
     * @throws DocumentException
     * @throws IOException
     */
    public static void waterMarkPlus(String outputFilePath, String inputFilePath, String text) throws DocumentException, IOException {
        System.out.println("[>>> [开始]给 PDF 文件添加水印……");
        PdfReader reader = new PdfReader(inputFilePath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath));
        PdfContentByte content;
        BaseFont base = setBaseFont();

        int total = reader.getNumberOfPages() + 1;
        for (int i = 1; i < total; i++) {
            // 在内容上方加水印 (content = stamper.getUnderContent(i);//在内容下方加水印)
            content = stamper.getOverContent(i);
            // 获取每一页的高度、宽度
            Rectangle pageSizeWithRotation = reader.getPageSizeWithRotation(i);
            PdfWaterMarkUtil.addMultiTextWaterContent(content, base, text, pageSizeWithRotation);
            PdfWaterMarkUtil.addPageFootWaterContent(content, base, text);
        }
        stamper.close();
        reader.close();
        System.out.println("[>>> [结束]给 PDF 文件添加水印……");
    }


    /**
     * 给PDF添加多文字水印
     *
     * @param content   PDF主体
     * @param base      字体
     * @param text      水印内容
     * @param rectangle 页面
     */
    private static void addMultiTextWaterContent(PdfContentByte content, BaseFont base, String text, Rectangle rectangle) {
        PdfGState gs = new PdfGState();
        // 设置水印透明度
        gs.setFillOpacity(0.3F);
        gs.setStrokeOpacity(0.3F);

        content.saveState();
        content.setGState(gs);

        // 获取水印文字的高度和宽度，文字水印间隔interval
        int textH = 0, textW = 0, interval = -5;
        JLabel label = new JLabel();
        label.setText(text);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        textH = metrics.getHeight();
        textW = metrics.stringWidth(label.getText());
        System.out.println("textH: " + textH + "\t textW: " + textW);

        content.beginText();
        // 设置水印文字：颜色、大小、位置
        content.setColorFill(BaseColor.GRAY);
        content.setFontAndSize(base, 10);
        content.setTextMatrix(70, 200);
        // 当前页面的高度和宽度
        float pageHeight = rectangle.getHeight(), pageWidth = rectangle.getWidth();
        // 根据纸张大小多次添加， 水印文字成30度角倾斜
        for (int height = interval + textH; height < pageHeight; height = height + textH * 3) {
            for (int width = interval + textW; width < pageWidth + textW; width = width + textW * 2) {
                content.showTextAligned(Element.ALIGN_LEFT, text, width - textW, height - textH, 30);
            }
        }
        content.endText();
        content.stroke();
    }

    /**
     * 给PDF设置页脚水印
     *
     * @param content       PDF主题内容
     * @param base          字体样式
     * @param waterMarkText 水印内容
     */
    private static void addPageFootWaterContent(PdfContentByte content, BaseFont base, String waterMarkText) throws DocumentException {
        // 设置字体和字体大小
        content.beginText();
        content.setFontAndSize(base, 10);
        content.setColorFill(BaseColor.RED);

        // 设置字体样式
        float ta = 1F, tb = 0F, tc = 0F, td = 1F, tx = 0F, ty = 0F;
        // 设置加粗(加粗)
        ta += 0.25F;
        td += 0.05F;
        ty -= 0.2F;
        // 设置倾斜(值越大，倾斜度越大)
        tc += 0.1F;
        content.setTextMatrix(ta, tb, tc, td, tx, ty);

        // 设置相对于左下角位置(向右为x，向上为y)
        content.moveText(300F, 5F);
        // 显示text
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        content.showText(waterMarkText + "," + dateStr);
        content.endText();
        content.stroke();
        content.restoreState();
    }


    /**
     * 给 PDF文件添加logo水印<br/>
     *
     * @param inputPath  PDF源文件地址(例如：D:\\src.pdf)
     * @param outputPath 加水印后的PDF存放地址(例如：D:\\water-marked-src.pdf)
     * @param images     图片地址（例如：D:\\logo.png）
     * @return 是否水印成功？（true成功，false失败）
     */
    public static boolean imageWatermark(String inputPath, String outputPath, String images) {
        try {
            PdfReader reader = new PdfReader(inputPath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPath));
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.1f);

            com.itextpdf.text.Image image = Image.getInstance(images);
            int n = reader.getNumberOfPages();
            PdfContentByte under;
            for (int i = 1; i <= n; i++) {
                PdfContentByte pdfContentByte = stamper.getOverContent(i);
                // 获得PDF最顶层
                under = stamper.getOverContent(i);
                pdfContentByte.setGState(gs1);

                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 8; x++) {
                        // 水印文字成45度角倾斜
                        image.setRotation(30);// 旋转 弧度
                        // 设置旋转角度
                        image.setRotationDegrees(-45);// 旋转 角度
                        // 设置等比缩放
                        under.setColorFill(BaseColor.GRAY);
                        image.scaleToFit(80, 120);
                        image.setRotation(45);
                        // set the first background image of the absolute
                        image.setAbsolutePosition(70 + 140 * x, 125 * y);
                        pdfContentByte.addImage(image);
                    }
                }
            }
            stamper.close();
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 给 PDF文件添加文字水印<br/>
     *
     * @param InPdfFile     文件地址
     * @param outPdfFile    水印后文件的地址
     * @param textWatermark 水印文字
     * @return 是否水印成功？（true成功，false失败）
     */
    public static boolean textWatermark(String InPdfFile, String outPdfFile, String textWatermark) {
        try {
            PdfReader reader = new PdfReader(InPdfFile);
            //reader.unethicalreading = true;
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outPdfFile));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages();
            //文字的长度
            int l = textWatermark.length();
            int size = 0;
            if (l <= 6) {
                size = 22;
            }
            if (l > 6 && l <= 11) {
                size = 15;
            }
            if (l > 11) {
                size = 10;
            }
            if (l > 15) {
                size = 5;
            }
            Font f = new Font(base, size);
            Phrase p = new Phrase(textWatermark, f);
            // transparency
            PdfGState gs1 = new PdfGState();
            // 设置水印透明度
            gs1.setFillOpacity(0.2f);
            PdfContentByte over;
            Rectangle pagesize;
            for (int i = 1; i <= total; i++) {
                pagesize = reader.getPageSizeWithRotation(i);
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                over.setTextMatrix(30, 30);
                over.setColorFill(BaseColor.GRAY);
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 8; x++) {
                        // 水印文字成45度角倾斜
                        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 80 + 140 * x, 158 * y, -45);
                    }
                }
                over.restoreState();
            }
            stamper.close();
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 设置字体
     *
     * @return 基础字体样式
     * @throws IOException
     * @throws DocumentException
     */
    private static BaseFont setBaseFont() throws IOException, DocumentException {
        // 使用classpath下面的字体库（推荐使用服务器指定的字体库）
        BaseFont base = null;
        try {
            base = BaseFont.createFont("/calibri.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            // 日志处理 e.printStackTrace();
            System.out.println("项目classpath目录下不存在指定字体库，将使用系统默认字体库");
            base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        }
        return base;

    }


    /**
     * 给PDF文件添加单个文字水印
     *
     * @param inputFile     你的PDF文件地址
     * @param outputFile    添加水印后生成PDF存放的地址
     * @param waterMarkName 你的水印
     * @return 是否水印成功？（true成功，false 失败）
     */
    public static boolean waterMark(String inputFile, String outputFile, String waterMarkName) {
        System.out.println("[>>> 开始给 PDF 文件添加水印……");
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            //这里的字体设置比较关键，这个设置是支持中文的写法(使用系统字体)
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            int total = reader.getNumberOfPages() + 1;

            PdfContentByte contentByte;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                pageRect = stamper.getReader().getPageSizeWithRotation(i);
                // 计算水印X,Y坐标
                float x = pageRect.getWidth() / 10;
                float y = pageRect.getHeight() / 10 - 10;
                // 获得PDF最顶层
                contentByte = stamper.getOverContent(i);
                contentByte.saveState();
                // set Transparency
                PdfGState gs = new PdfGState();
                // 设置透明度为0.2
                gs.setFillOpacity(1.f);
                contentByte.setGState(gs);
                contentByte.restoreState();
                contentByte.beginText();
                contentByte.setFontAndSize(base, 60);
                contentByte.setColorFill(BaseColor.ORANGE);

                // 水印文字成45度角倾斜
                contentByte.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x, y, 55);
                // 添加水印文字
                contentByte.endText();
                contentByte.setLineWidth(1f);

                contentByte.stroke();
            }
            stamper.close();
            System.out.println("[>>> 结束给 PDF 文件添加水印……");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[>>> 给 PDF 文件添加水印发生异常……");
            return false;
        }
    }

}
