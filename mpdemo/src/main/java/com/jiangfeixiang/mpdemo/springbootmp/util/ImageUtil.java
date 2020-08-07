package com.jiangfeixiang.mpdemo.springbootmp.util;

import cn.hutool.core.lang.UUID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 图片工具类
 *
 * @author Administrator
 * @date 2020/8/7 上午 11:35
 */
public class ImageUtil {

    public static void main(String[] args) throws Exception {
        testCreateImagePng();
    }

    public static void testCreateImagePng() throws Exception {
        // 生成图片成功后PNG存放的地址
        String rootPath = "D:\\";
        String imageContent = "中华人民共和国";
        Font font = new Font("华文彩云", Font.PLAIN, 100);
        File outFile = Paths.get(rootPath, "b" + UUID.fastUUID() + ".png").toFile();
        createImage(imageContent, font, outFile);
    }

    /**
     * 根据文本和字体的设置，获取生成图片的宽度和长度
     *
     * @param text 文本内容
     * @param font 字体样式
     * @return
     */
    private static int[] getWidthAndHeight(String text, Font font) {
        Rectangle2D r = font.getStringBounds(text, new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));
        int unitHeight = (int) Math.floor(r.getHeight());
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 15;
        System.out.println("width:" + width + ", height:" + height);
        return new int[]{width, height};
    }

    /**
     * 根据str,font的样式以及输出文件目录
     *
     * @param text    图片文本内容
     * @param font    字体设置
     * @param outFile 输入文件
     * @throws Exception
     */
    public static void createImage(String text, Font font, File outFile) {
        // 获取font的样式应用在str上的整个矩形
        try {
            int[] arr = getWidthAndHeight(text, font);
            int width = arr[0], height = arr[1];
            // 创建图片(创建图片画布)
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics g = image.getGraphics();
            // 先用白色填充整张图片,也就是背景
            g.setColor(Color.WHITE);
            //画出矩形区域，以便于在矩形区域内写入文字
            g.fillRect(0, 0, width, height);
            // 再换成黑色，以便于写入文字
            g.setColor(Color.black);
            // 设置画笔字体
            g.setFont(font);
            // 画出一行字符串
            g.drawString(text, 0, font.getSize());
            // 画出第二行字符串，注意y轴坐标需要变动
            g.drawString(text, 0, 2 * font.getSize());
            g.dispose();
            // 输出png图片
            ImageIO.write(image, "png", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
