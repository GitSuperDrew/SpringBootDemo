package com.study.module.util.code;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.UUID;

/**
 * 二维码生成/读取工具：（① 只要图片中存在二维码，即可读取；② 但是如果一张图片中存在多个二维码就没有测试。）
 *
 * @author drew
 * @date 2021/1/31 22:03
 **/
public class QrCodeGeneratorUtil {

    public static void main(String[] args) {
        try {
            // 1. 生成二维码
            generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
            // 2. 读取生成二维码的值
            System.out.println("二维码的内容为：" + readQrCodeImage(QR_CODE_IMAGE_PATH));
            System.out.println("二维码的内容为：" + readQrCodeImage("D:/qrcode-src.jpg"));
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }

    private static final String QR_CODE_IMAGE_PATH = "D:/QRCode" + UUID.randomUUID().toString().substring(0, 8) + ".png";

    /**
     * 生成二维码
     *
     * @param text     生成二维码的内容
     * @param width    二维码图片的宽度
     * @param height   二维码图片的高度
     * @param filePath 生成二维码图片的存放路径
     * @throws WriterException
     * @throws IOException
     */
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    /**
     * 读取含有二维码的图片的文本内容（如果一张图片中存在多个二维码，是否能读取成功呢？待定）
     *
     * @param qrCodeImagePath 还有二维码图片的地址（例如：D:/image/qrcode-src.jpg）
     * @return 二维码读取的文本内容
     */
    private static String readQrCodeImage(String qrCodeImagePath) {
        MultiFormatReader read = new MultiFormatReader();
        File file = new File(qrCodeImagePath);
        BufferedImage image;
        String resultInfo = "";
        Result res;
        try {
            image = ImageIO.read(file);
            Binarizer binarizer = new HybridBinarizer(new BufferedImageLuminanceSource(image));
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            res = read.decode(binaryBitmap);
            resultInfo = res.getText();
            System.out.println("二维码的内容：" + res.toString());
            System.out.println("图片的格式：\t" + res.getBarcodeFormat());
            System.out.println("二维码的内容：" + res.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 将字符串生成二维码图片（可能会产生中文乱码）
     * <br/>此接口可以返回一个字节数组给前端，前端“翻译”数组得到一个二维码图片（此接口可替换生成图片的接口--附加的接口）
     *
     * @param text   文本内容
     * @param width  二维码图片宽度
     * @param height 二维码图片高度
     * @return 字节数组流（可转图片格式）
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    /**
     * 字符编码格式
     */
    private static final String CHARSET = "utf-8";

    /**
     * 将字符串生成二维码图片
     *
     * @param text   文本内容
     * @param width  二维码图片的宽度
     * @param height 二维码图片的高度
     * @return 字节数组（可以转图片）
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] getQRCodeImage2(String text, int width, int height) throws WriterException, IOException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }


}
