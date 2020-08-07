package com.jiangfeixiang.mpdemo.springbootmp.controller.testpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * @author Administrator
 * @date 2020/8/6 下午 5:34
 */
public class PDFBillUtil {//(此工具类转载 http://blog.csdn.net/justinytsoft/article/details/53320225)

    // BillPDF billPDF
    public static void createBillPDF() {
        try {
            // 输出路径
            String outPath = "C:/Users/Administrator/Desktop/Helloworld.PDF";

            // 设置纸张
            Rectangle rect = new Rectangle(PageSize.A4);

            // 创建文档实例
            Document document = new Document(rect);

            PdfWriter.getInstance(document, new FileOutputStream(outPath));

            // 设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12,
                    com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.ITALIC);
            com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.NORMAL);

            document.open();

            // table1
            PdfPTable table1 = new PdfPTable(3);
            PdfPCell cell11 = new PdfPCell(new Paragraph("费用报销", FontChinese24));
            cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell11.setBorder(0);
            String imagePath = "C:\\Users\\Administrator\\Pictures\\1.jpg";
            Image image1 = Image.getInstance(imagePath);

            Image image2 = Image.getInstance(imagePath);
            // 设置每列宽度比例
            int width11[] = {35, 40, 25};
            table1.setWidths(width11);
            table1.getDefaultCell().setBorder(0);
            table1.addCell(image1);
            table1.addCell(cell11);
            table1.addCell(image2);
            document.add(table1);
            // 加入空行
            Paragraph blankRow1 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow1);

            // table2
            PdfPTable table2 = new PdfPTable(2);
            // 设置每列宽度比例
            int width21[] = {2, 98};
            table2.setWidths(width21);
            table2.getDefaultCell().setBorder(0);
            PdfPCell cell21 = new PdfPCell(new Paragraph("报销概要", FontChinese16));
            String imagePath2 = "C:\\Users\\Administrator\\Pictures\\1.jpg";
            Image image21 = Image.getInstance(imagePath2);
            cell21.setBorder(0);
            table2.addCell(image21);
            table2.addCell(cell21);
            document.add(table2);
            // 加入空行
            Paragraph blankRow2 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow2);

            // table3
            PdfPTable table3 = new PdfPTable(3);
            int width3[] = {40, 35, 25};
            table3.setWidths(width3);
            PdfPCell cell31 = new PdfPCell(new Paragraph("申请人：" + "XXX", FontChinese11Normal));
            PdfPCell cell32 = new PdfPCell(new Paragraph("日期：" + "2011-11-11", FontChinese11Normal));
            PdfPCell cell33 = new PdfPCell(new Paragraph("报销单号：" + "123456789", FontChinese11Normal));
            cell31.setBorder(0);
            cell32.setBorder(0);
            cell33.setBorder(0);
            table3.addCell(cell31);
            table3.addCell(cell32);
            table3.addCell(cell33);
            document.add(table3);
            // 加入空行
            Paragraph blankRow31 = new Paragraph(18f, " ", FontChinese11);
            document.add(blankRow31);

            // table4
            PdfPTable table4 = new PdfPTable(2);
            int width4[] = {40, 60};
            table4.setWidths(width4);
            PdfPCell cell41 = new PdfPCell(new Paragraph("公司：" + "XXX", FontChinese11Normal));
            PdfPCell cell42 = new PdfPCell(new Paragraph("部门：" + "XXX", FontChinese11Normal));
            cell41.setBorder(0);
            cell42.setBorder(0);
            table4.addCell(cell41);
            table4.addCell(cell42);
            document.add(table4);
            // 加入空行
            Paragraph blankRow41 = new Paragraph(18f, " ", FontChinese11);
            document.add(blankRow41);

            // table5
            PdfPTable table5 = new PdfPTable(1);
            PdfPCell cell51 = new PdfPCell(new Paragraph("报销说明：" + "XXX", FontChinese11));
            cell51.setBorder(0);
            table5.addCell(cell51);
            document.add(table5);
            // 加入空行
            Paragraph blankRow51 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow51);

            // table6
            PdfPTable table6 = new PdfPTable(2);
            table6.getDefaultCell().setBorder(0);
            table6.setWidths(width21);
            PdfPCell cell61 = new PdfPCell(new Paragraph("报销明细", FontChinese16));
            cell61.setBorder(0);
            table6.addCell(image21);
            table6.addCell(cell61);
            document.add(table6);
            // 加入空行
            Paragraph blankRow4 = new Paragraph(18f, " ", FontChinese16);
            document.add(blankRow4);

            // table7
            PdfPTable table7 = new PdfPTable(6);
            BaseColor lightGrey = new BaseColor(0xCC, 0xCC, 0xCC);
            int width7[] = {20, 18, 13, 20, 14, 15};
            table7.setWidths(width7);
            PdfPCell cell71 = new PdfPCell(new Paragraph("费用类型", FontChinese11Bold));
            PdfPCell cell72 = new PdfPCell(new Paragraph("费用发生时间", FontChinese11Bold));
            PdfPCell cell73 = new PdfPCell(new Paragraph("详细信息", FontChinese11Bold));
            PdfPCell cell74 = new PdfPCell(new Paragraph("消费金币/币种", FontChinese11Bold));
            PdfPCell cell75 = new PdfPCell(new Paragraph("报销汇率", FontChinese11Bold));
            PdfPCell cell76 = new PdfPCell(new Paragraph("报销金额", FontChinese11Bold));
            // 表格高度
            cell71.setFixedHeight(25);
            cell72.setFixedHeight(25);
            cell73.setFixedHeight(25);
            cell74.setFixedHeight(25);
            cell75.setFixedHeight(25);
            cell76.setFixedHeight(25);
            // 水平居中
            cell71.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell72.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell73.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell74.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell75.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell76.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 垂直居中
            cell71.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell72.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell73.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell74.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell75.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell76.setVerticalAlignment(Element.ALIGN_MIDDLE);
            // 边框颜色
            cell71.setBorderColor(lightGrey);
            cell72.setBorderColor(lightGrey);
            cell73.setBorderColor(lightGrey);
            cell74.setBorderColor(lightGrey);
            cell75.setBorderColor(lightGrey);
            cell76.setBorderColor(lightGrey);
            // 去掉左右边框
            cell71.disableBorderSide(8);
            cell72.disableBorderSide(4);
            cell72.disableBorderSide(8);
            cell73.disableBorderSide(4);
            cell73.disableBorderSide(8);
            cell74.disableBorderSide(4);
            cell74.disableBorderSide(8);
            cell75.disableBorderSide(4);
            cell75.disableBorderSide(8);
            cell76.disableBorderSide(4);
            table7.addCell(cell71);
            table7.addCell(cell72);
            table7.addCell(cell73);
            table7.addCell(cell74);
            table7.addCell(cell75);
            table7.addCell(cell76);
            document.add(table7);

            // table8
            PdfPTable table8 = new PdfPTable(6);
            int width8[] = {20, 18, 13, 20, 14, 15};
            table8.setWidths(width8);
            PdfPCell cell81 = new PdfPCell(new Paragraph("差旅报销", FontChinese12));
            PdfPCell cell82 = new PdfPCell(new Paragraph("2011-11-11", FontChinese12));
            PdfPCell cell83 = new PdfPCell(new Paragraph("XXX", FontChinese12));
            PdfPCell cell84 = new PdfPCell(new Paragraph("XXX", FontChinese12));
            PdfPCell cell85 = new PdfPCell(new Paragraph("XXX", FontChinese12));
            PdfPCell cell86 = new PdfPCell(new Paragraph("XXX", FontChinese12));
            // 表格高度
            cell81.setFixedHeight(25);
            cell82.setFixedHeight(25);
            cell83.setFixedHeight(25);
            cell84.setFixedHeight(25);
            cell85.setFixedHeight(25);
            cell86.setFixedHeight(25);
            // 水平居中
            cell81.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell82.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell83.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell84.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell85.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell86.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 垂直居中
            cell81.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell82.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell83.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell84.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell85.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell86.setVerticalAlignment(Element.ALIGN_MIDDLE);
            // 边框颜色
            cell81.setBorderColor(lightGrey);
            cell82.setBorderColor(lightGrey);
            cell83.setBorderColor(lightGrey);
            cell84.setBorderColor(lightGrey);
            cell85.setBorderColor(lightGrey);
            cell86.setBorderColor(lightGrey);
            // 去掉左右边框
            cell81.disableBorderSide(8);
            cell82.disableBorderSide(4);
            cell82.disableBorderSide(8);
            cell83.disableBorderSide(4);
            cell83.disableBorderSide(8);
            cell84.disableBorderSide(4);
            cell84.disableBorderSide(8);
            cell85.disableBorderSide(4);
            cell85.disableBorderSide(8);
            cell86.disableBorderSide(4);
            table8.addCell(cell81);
            table8.addCell(cell82);
            table8.addCell(cell83);
            table8.addCell(cell84);
            table8.addCell(cell85);
            table8.addCell(cell86);
            document.add(table8);
            // 加入空行
            Paragraph blankRow5 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow5);

            // table9
            PdfPTable table9 = new PdfPTable(3);
            int width9[] = {30, 50, 20};
            table9.setWidths(width9);
            PdfPCell cell91 = new PdfPCell(new Paragraph("", FontChinese12));
            PdfPCell cell92 = new PdfPCell(new Paragraph("收到的报销金额", FontChinese12));
            PdfPCell cell93 = new PdfPCell(new Paragraph("1000", FontChinese24));
            cell92.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell92.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell93.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell93.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell91.setBorder(0);
            cell92.setBorder(0);
            cell93.setBorder(0);
            table9.addCell(cell91);
            table9.addCell(cell92);
            table9.addCell(cell93);
            document.add(table9);

            document.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createBillPDF();
//        BillPDF billPDF = new BillPDF();
//        createBillPDF(billPDF);
    }
}
