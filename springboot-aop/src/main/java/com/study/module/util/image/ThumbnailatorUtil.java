package com.study.module.util.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 一个非常好的图片处理类工具：thumbnailator
 * <p>Thumbnailator是一个非常好的图片开源工具，可以很好的完成图片处理。
 * 从API提供现有的图像文件和图像对象的缩略图中简化了缩略过程，两三行代码就能够从现有图片生成缩略图，
 * 且允许微调缩略图生成，同时保持了需要写入到最低限度的代码量。同时还支持根据一个目录批量生成缩略图。
 * </p>
 *
 * @author drew
 * @date 2021/3/2 9:36
 **/
public class ThumbnailatorUtil {

    public static void main(String[] args) {
        String output = "D:/" + UUID.randomUUID().toString().substring(0, 8).concat(".png");
        // imgThumb("D:/logo.png", output, 20, 20);
        ImgWatermark("D:/source_0.png", output, 300, 300, Positions.BOTTOM_RIGHT, "D:/logo.png",0.5f, 0.8f);
    }

    /**
     * 指定大小进行缩放
     * 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public static void imgThumb(String source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定大小进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public static void imgThumb(File source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照比例进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param scale  缩放比例
     */
    public static void imgScale(String source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgScale(File source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source          输入源（文件路径，例如：D:/demo.jpg）
     * @param output          输出源(输出文件所在的目录： D:/demo.jpg)
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度
     */
    public static void imgRotate(String source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgRotate(File source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水印(对图片源文件进行图片水印)
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址(例如：D:/output.png)
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     */
    public static void ImgWatermark(String source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgWatermark(File source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgSourceRegion(String source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgSourceRegion(File source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按坐标裁剪
     *
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化图像格式
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    public static void imgFormat(String source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imgFormat(File source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到OutputStream
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @return toOutputStream(流对象)
     */
    public static OutputStream imgOutputStream(String source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public static OutputStream imgOutputStream(File source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 输出到BufferedImage
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     * @return BufferedImage
     */
    public static BufferedImage imgBufferedImage(String source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public static BufferedImage imgBufferedImage(File source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

}
