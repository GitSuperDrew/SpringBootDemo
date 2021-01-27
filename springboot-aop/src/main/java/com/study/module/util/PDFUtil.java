package com.study.module.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.UUID;

import static java.nio.file.Files.createTempFile;

/**
 * @author zl
 * @date 2021/1/26 18:31
 **/
@Slf4j
public class PDFUtil {
    public final static int A0 = 0;
    public final static int A1 = 1;
    public final static int A2 = 2;
    public final static int A3 = 3;
    public final static int A4 = 4;
    public final static int A5 = 5;
    public final static int A6 = 6;

    private final static PDRectangle[] PAGE_SIZE = new PDRectangle[]{
            PDRectangle.A0,
            PDRectangle.A1,
            PDRectangle.A2,
            PDRectangle.A3,
            PDRectangle.A4,
            PDRectangle.A5,
            PDRectangle.A6
    };

    public static void main(String[] args) throws IOException {
        String imgPath = "D:\\apache-tomcat-8.5.20\\temp\\1111.png";
        // 第一种：导出img转PDF文件（PDF文件不分页）
        String targetPath = "D:\\apache-tomcat-8.5.20\\temp\\" + UUID.randomUUID().toString().substring(0, 8) + ".pdf";
        PDFUtil.img2Pdf(targetPath, imgPath);

        // 第二种：导出img转PDF文件（PDF文件分页）
//        String targetPath2 = createTempFile("pdf-" + UUID.randomUUID().toString().substring(0, 8), ".pdf").toAbsolutePath().toString();
//        PDFUtil.img2Pdf2(targetPath2, imgPath, A2);

    }


    /**
     * 不分页，img 转 pdf
     *
     * @param pdfFilePath pdf 文件路径（例如：“D:\\test\\111.png”）
     * @param imgFilePath 图片文件路径（例如：“D:\\test\\” + UUID + ".pdf"）
     * @throws IOException
     */
    public static void img2Pdf(String pdfFilePath, String imgFilePath) throws IOException {
        try (PDDocument doc = new PDDocument();) {
            PDImageXObject imageXObject = PDImageXObject.createFromFile(imgFilePath, doc);
            float width = imageXObject.getWidth();
            float height = imageXObject.getHeight();

            PDPage pdPage = addImgPageToDoc(doc, imageXObject, 0f, 0f, width, height);
            doc.addPage(pdPage);

            doc.save(pdfFilePath);
        } catch (Exception e) {
            log.error("生成PDF失败", e);
            throw e;
        }
    }

    /**
     * 将图片添加到DOC文档中
     *
     * @param doc          文档对象
     * @param imageXObject 图片对象
     * @param ox           相对X轴坐标
     * @param oy           相对Y轴坐标
     * @param imgWidth     图片宽度
     * @param imgHeight    图片高度
     * @return
     * @throws IOException
     */
    private static PDPage addImgPageToDoc(PDDocument doc, PDImageXObject imageXObject, float ox, float oy,
                                          float imgWidth, float imgHeight) throws IOException {
        PDPage page = new PDPage(new PDRectangle(imgWidth, imgHeight));
        try (PDPageContentStream contentStream =
                     new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.drawImage(imageXObject, ox, oy, imgWidth, imgHeight);
        } catch (Exception e) {
            log.error("PDF:插入图片失败", e);
            throw e;
        }
        return page;
    }


    /**
     * 分页，img转pdf时出现分页
     *
     * @param pdfFilePath PDF文件路径 文件路径（例如：“D:\\test\\111.png”）
     * @param imgFilePath 图片文件路径（例如：“D:\\test\\” + UUID + ".pdf"）
     * @param pageSize    页面大小型号（A0~A6）常见如 A4
     * @throws IOException
     */
    public static void img2Pdf2(String pdfFilePath, String imgFilePath, int pageSize) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDImageXObject imageXObject = PDImageXObject.createFromFile(imgFilePath, doc);
            float width = imageXObject.getWidth();
            float height = imageXObject.getHeight();
            PDRectangle rectangle = PAGE_SIZE[pageSize];
            float pageHeight = (width / rectangle.getWidth() * rectangle.getHeight());
            float leftHeight = height;
            float imgWidth = (rectangle.getWidth() - 5);
            float imgHeight = (rectangle.getWidth() / width * height);
            if (leftHeight < pageHeight) {
                PDPage pdPage = addImgPageToDoc2(doc, imageXObject, 0f, (rectangle.getHeight() - imgHeight), imgWidth, imgHeight);
                doc.addPage(pdPage);
            } else {
                float position = -(imgHeight - rectangle.getHeight());
                while (leftHeight > 0) {
                    PDPage pdPage = addImgPageToDoc2(doc, imageXObject, 0f, position, imgWidth, imgHeight);
                    leftHeight -= pageHeight;
                    position += rectangle.getHeight();
                    doc.addPage(pdPage);
                }
            }
            doc.save(pdfFilePath);
        } catch (Exception e) {
            log.error("生成PDF失败", e);
            throw e;
        }
    }

    private static PDPage addImgPageToDoc2(PDDocument doc, PDImageXObject imageXObject, float ox, float oy,
                                           float imgWidth, float imgHeight) throws IOException {
        PDPage page = new PDPage(PDRectangle.A2);
        try (PDPageContentStream contentStream =
                     new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.drawImage(imageXObject, ox, oy, imgWidth, imgHeight);
        } catch (Exception e) {
            log.error("PDF:插入图片失败", e);
            throw e;
        }
        return page;
    }
}
