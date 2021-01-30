package com.study.module.util.watermark;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 给图片添加水印
 *
 * @author tgy
 */
public class MarkImageUtils {

    public static void main(String[] args) {
        String output = "D:/";
        //源图片路径
        String source = "D:/source_0.png";
        //覆盖图片路径
        String icon = "D:/logo.png";
        //图片名称
        String imageName = "arm_" + UUID.randomUUID().toString().replace("-", "");
        //图片类型jpg,jpeg,png,gif
        String imageType = "jpg";
        String text = "加水印了";
        //马赛克大小
        int size = 4;
        //水印旋转角度-45，null表示不旋转
        Integer degree = -45;
        String result = null;

        //给图片添加图片水印
        result = MarkImageUtils.markImageByMoreIcon(icon, source, output, imageName, imageType, degree);
//        result = MarkImageUtils.markImageBySingleIcon(icon, source, output, imageName, imageType, degree);
        //给图片添加文字水印
//        result = MarkImageUtils.markImageByMoreText(source, output, imageName, imageType, Color.red, text, degree);
//        result = MarkImageUtils.markImageBySingleText(source, output, imageName, imageType, Color.red, text, degree);
        //给图片打马赛克
//        result = MarkImageUtils.markImageByMosaic(source, output, imageName, imageType, size);
        System.out.println("over >>>> " + result);
    }


    /**
     * 给图片不同位置添加多个图片水印、可设置水印图片旋转角度
     *
     * @param icon      水印图片路径（如：F:/images/icon.png）
     * @param source    没有加水印的图片路径（如：F:/images/6.jpg）
     * @param output    加水印后的图片路径（如：F:/images/）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param degree    水印图片旋转角度，为null表示不旋转
     */
    public static String markImageByMoreIcon(String icon, String source, String output, String imageName, String imageType, Integer degree) {
        String result = "添加图片水印出错";
        try {
            File file = new File(source);
            File ficon = new File(icon);
            if (!file.isFile()) {
                return source + " 不是一个图片文件!";
            }
            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            //icon高度
            int icheight = ic.getHeight(null);
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = 0;
            //默认两张水印图片的间隔高度是水印图片的1/3
            int temp = icheight / 3;
            int space = 1;
            if (height >= icheight) {
                space = height / icheight;
                if (space >= 2) {
                    temp = y = icheight / 2;
                    if (space == 1 || space == 0) {
                        x = 0;
                        y = 0;
                    }
                }
            } else {
                x = 0;
                y = 0;
            }
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            for (int i = 0; i < space; i++) {
                if (null != degree) {
                    //设置水印旋转
                    g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
                }
                //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                ImageIcon imgIcon = new ImageIcon(icon);
                //得到Image对象。
                Image con = imgIcon.getImage();
                //透明度，最小值为0，最大值为1
                float clarity = 0.6f;
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
                //表示水印图片的坐标位置(x,y)
                //g.drawImage(con, 300, 220, null);
                g.drawImage(con, x, y, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                y += (icheight + temp);
            }
            g.dispose();
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
            result = "图片完成添加Icon水印";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给图片添加单个图片水印、可设置水印图片旋转角度
     *
     * @param icon      水印图片路径（如：F:/images/icon.png）
     * @param source    没有加水印的图片路径（如：F:/images/6.jpg）
     * @param output    加水印后的图片路径（如：F:/images/）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param degree    水印图片旋转角度，为null表示不旋转
     */
    public static String markImageBySingleIcon(String icon, String source, String output, String imageName, String imageType, Integer degree) {
        String result = "添加图片水印出错";
        try {
            File file = new File(source);
            File ficon = new File(icon);
            if (!file.isFile()) {
                return source + " 不是一个图片文件!";
            }
            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            //icon高度
            int icheight = ic.getHeight(null);
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = (height / 2) - (icheight / 2);
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(icon);
            //得到Image对象。
            Image con = imgIcon.getImage();
            //透明度，最小值为0，最大值为1
            float clarity = 0.6f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
            //表示水印图片的坐标位置(x,y)
            //g.drawImage(con, 300, 220, null);
            g.drawImage(con, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
            result = "图片完成添加Icon水印";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给图片添加多个文字水印、可设置水印文字旋转角度
     *
     * @param source    需要添加水印的图片路径（如：F:/images/6.jpg）
     * @param output    添加水印后图片输出路径（如：F:/images/）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param color     水印文字的颜色
     * @param word      水印文字
     * @param degree    水印文字旋转角度，为null表示不旋转
     */
    public static String markImageByMoreText(String source, String output, String imageName, String imageType, Color color, String word, Integer degree) {
        String result = "添加文字水印出错";
        try {
            //读取原图片信息
            File file = new File(source);
            if (!file.isFile()) {
                return file + " 不是一个图片文件!";
            }
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            //文字大小
            int size = 50;
            //加水印
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.drawImage(img, 0, 0, width, height, null);
            //设置水印字体样式
            Font font = new Font("宋体", Font.PLAIN, size);
            //根据图片的背景设置水印颜色
            g.setColor(color);
            int x = width / 3;
            int y = size;
            int space = height / size;
            for (int i = 0; i < space; i++) {
                //如果最后一个坐标的y轴比height高，直接退出
                if ((y + size) > height) {
                    break;
                }
                if (null != degree) {
                    //设置水印旋转
                    g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
                }
                g.setFont(font);
                //水印位置
                g.drawString(word, x, y);
                y += (2 * size);
            }
            g.dispose();
            //输出图片
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
            result = "图片完成添加Word水印";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给图片添加单个文字水印、可设置水印文字旋转角度
     *
     * @param source    需要添加水印的图片路径（如：F:/images/6.jpg）
     * @param output    添加水印后图片输出路径（如：F:/images/）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param color     水印文字的颜色
     * @param word      水印文字
     * @param degree    水印文字旋转角度，为null表示不旋转
     */
    public static String markImageBySingleText(String source, String output, String imageName, String imageType, Color color, String word, Integer degree) {
        String result = "添加文字水印出错";
        try {
            //读取原图片信息
            File file = new File(source);
            if (!file.isFile()) {
                return file + " 不是一个图片文件!";
            }
            Image img = ImageIO.read(file);
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            //加水印
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.drawImage(img, 0, 0, width, height, null);
            //设置水印字体样式
            Font font = new Font("宋体", Font.PLAIN, 50);
            //根据图片的背景设置水印颜色
            g.setColor(color);
            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            g.setFont(font);
            int x = width / 3;
            int y = height / 2;
            //水印位置
            g.drawString(word, x, y);
            g.dispose();
            //输出图片
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
            result = "图片完成添加Word水印";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给图片加马赛克
     *
     * @param source    原图片路径（如：F:/images/6.jpg）
     * @param output    打马赛克后，图片保存的路径（如：F:/images/）
     * @param imageName 图片名称（如：11111）
     * @param imageType 图片类型（如：jpg）
     * @param size      马赛克尺寸，即每个矩形的宽高
     * @return
     */
    public static String markImageByMosaic(String source, String output, String imageName, String imageType, int size) {
        String result = "图片打马赛克出错";
        try {
            File file = new File(source);
            if (!file.isFile()) {
                return file + " 不是一个图片文件!";
            }
            BufferedImage img = ImageIO.read(file); // 读取该图片
            int width = img.getWidth(null); //原图片宽
            int height = img.getHeight(null); //原图片高
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //马赛克格尺寸太大或太小
            if (width < size || height < size) {
                return "马赛克格尺寸太大";
            }
            if (size <= 0) {
                return "马赛克格尺寸太小";
            }
            int xcount = 0; //x方向绘制个数
            int ycount = 0; //y方向绘制个数
            if (width % size == 0) {
                xcount = width / size;
            } else {
                xcount = width / size + 1;
            }
            if (height % size == 0) {
                ycount = height / size;
            } else {
                ycount = height / size + 1;
            }
            int x = 0; //x坐标
            int y = 0;
            //y坐标
            //绘制马赛克(绘制矩形并填充颜色)
            Graphics2D g = bi.createGraphics();
            for (int i = 0; i < xcount; i++) {
                for (int j = 0; j < ycount; j++) {
                    //马赛克矩形格大小
                    int mwidth = size;
                    int mheight = size;
                    if (i == xcount - 1) {  //横向最后一个不够一个size
                        mwidth = width - x;
                    }
                    if (j == ycount - 1) { //纵向最后一个不够一个size
                        mheight = height - y;
                    }
                    //矩形颜色取中心像素点RGB值
                    int centerX = x;
                    int centerY = y;
                    if (mwidth % 2 == 0) {
                        centerX += mwidth / 2;
                    } else {
                        centerX += (mwidth - 1) / 2;
                    }
                    if (mheight % 2 == 0) {
                        centerY += mheight / 2;
                    } else {
                        centerY += (mheight - 1) / 2;
                    }
                    Color color = new Color(img.getRGB(centerX, centerY));
                    g.setColor(color);
                    g.fillRect(x, y, mwidth, mheight);
                    y = y + size;// 计算下一个矩形的y坐标
                }
                y = 0;// 还原y坐标
                x = x + size;// 计算x坐标
            }
            g.dispose();
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
            result = "打马赛克成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
