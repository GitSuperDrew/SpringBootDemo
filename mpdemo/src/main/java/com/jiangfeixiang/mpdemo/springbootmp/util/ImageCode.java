package com.jiangfeixiang.mpdemo.springbootmp.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Administrator
 * @date 2020/6/20 上午 10:52
 */
public class ImageCode {

    private static char[] MAP_TABLE = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0', '1',
            '2', '3', '4', '5', '6', '7',
            '8', '9'};

    /**
     * 生成验证码图片
     *
     * @param width   宽度（传入-1，默认60）
     * @param height  高度（传入-1，默认40）
     * @param codeLen 验证码位数（位数限制在最多6位）
     * @param os      输出流
     * @return 验证码图片
     */
    public static Map<String, Object> getImageCode(int width, int height, int codeLen, OutputStream os) {
        width = width <= 0 ? 100 : width;
        height = height <= 0 ? 20 : height;
        codeLen = codeLen <= 0 || codeLen >= 6 ? 6 : codeLen;
        Map<String, Object> returnMap = new HashMap<>();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的码
        StringBuilder strEnsure = new StringBuilder();
        // codeLen代表多少位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < codeLen; ++i) {
            strEnsure.append(MAP_TABLE[(int) (MAP_TABLE.length * Math.random())]);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //直接生成
            String str = strEnsure.substring(i, i + 1);
            g.drawString(str, 13 * i + 6, 16);
        }

        // 释放图形上下文
        g.dispose();
        returnMap.put("image", image);
        returnMap.put("strEnsure", strEnsure.toString());
        return returnMap;
    }

    //给定范围获得随机颜色
    static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
