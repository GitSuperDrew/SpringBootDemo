package com.study.module.util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 无头浏览器 chromedriver.exe 入门使用demo
 *
 * @author zl
 * @date 2021/1/29 10:49
 **/
public class ChromeDriverUseDemo {
    /**
     * 驱动安装地址
     */
    private static final String DRIVER_ADDR = "D:/chromedriver1.exe";


    public static void main(String[] args) throws InterruptedException {
        String url = "http://www.baidu.com";
        String url1 = "http://www.douban.com";
        String localFilePath = "D:/source_" + UUID.randomUUID().toString().replace("-", "") + ".png";

        // 用法一：获取页面图片
        // getWebPageImg(url1, localFilePath);
        // 1-2：获取页面全部截图
        getWebPageAllImg(url1, localFilePath);
        // 用法二：获取页面源码
        // getWebPageCode(url);

    }

    /**
     * 获取指定页面全页面截图
     *
     * @param url           指定地址(例如：https://www.airbnb.co.in/s/India/)
     * @param localFilePath 本地存放地址（例如：D:/tt.png）
     */
    public static void getWebPageAllImg(String url, String localFilePath) {
        long startTime = System.currentTimeMillis();
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", DRIVER_ADDR);
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置无头模式
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--dns-prefetch-disable");
        // chromeOptions.addArguments("--no-referrers");
        // chromeOptions.addArguments("--disable-gpu");
        // chromeOptions.addArguments("--disable-audio");
        // chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--allow-insecure-localhost");
        // 设置开发者模式
        List<String> excludeSwitches = new ArrayList<>();
        excludeSwitches.add("enable-automation");
        chromeOptions.setExperimentalOption("excludeSwitches", excludeSwitches);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(setDimension(true));
        driver.get(url);
        //take screenshot of the entire page
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File(localFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "图片已经生成到：" + localFilePath);
    }

    /**
     * 截取指定网页的图片
     *
     * @param url           指定网页地址url(例如：http://www.baidu.com)
     * @param localFilePath 图片存放地址（例如：“D:/images/111.png”）
     * @throws InterruptedException
     */
    public static void getWebPageImg(String url, String localFilePath) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        // 1.调用chrome driver驱动程序
        System.setProperty("webdriver.chrome.driver", DRIVER_ADDR);
        // 2.创建 chrome option 对象
        ChromeOptions chromeOptions = new ChromeOptions();

        // 3.1 属性设置：无图模式
        Map<String, Object> prefs = new HashMap<String, Object>(10);
        prefs.put("profile.managed_default_content_settings.images", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        // 3.2 设置免检测（开发者模式）
        List<String> excludeSwitches = new ArrayList<>();
        excludeSwitches.add("enable-automation");
        chromeOptions.setExperimentalOption("excludeSwitches", excludeSwitches);
        // 3.3 设置代理IP
        // String ip = "localhost:8080";
        // chromeOptions.addArguments("--proxy-server=http://" + ip);

        // 3.4 ====== 设置浏览器启动参数
        chromeOptions.addArguments("--headless");// 设置无头浏览
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--no-referrers");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-audio");
        // chromeOptions.addArguments("--disable-plugins");// 禁用插进
        // chromeOptions.addArguments("--disable-images");// 禁用图片
        chromeOptions.addArguments("--start-maximized");// 启动就最大化
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--allow-insecure-localhost");


        // 4 浏览器设置：大小
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(setDimension(true));

        // 网页等待时间
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // 正式获取网页数据
        driver.get(url);

        Thread.sleep(1000L);
        //3.当前页面截图并存储到本地（原图片）
        File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
//            copyFileUsingFileChannels(srcfile, new File(localFilePath));
            copyFileUsingFileStreams(srcfile, new File(localFilePath));
//            FileCopyUtils.copy(srcfile, new File(localFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.close();
        System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 设置截取图片的尺寸
     *
     * @param isDefault true 默认屏幕的大小，false 宽1300高900
     * @return dimension 尺度
     */
    private static Dimension setDimension(Boolean isDefault) {
        if (isDefault) {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth(), height = screenSize.getHeight();
            return new Dimension((int) width, (int) height);
        } else {
            return new Dimension(1300, 900);
        }
    }


    /**
     * 文件复制(channel流)
     *
     * @param source 源文件
     * @param dest   目标文件
     * @throws IOException
     */
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        try (FileChannel inputChannel = new FileInputStream(source).getChannel();
             FileChannel outputChannel = new FileOutputStream(dest).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }

    /**
     * 文件复制（IO流）
     *
     * @param source 源文件
     * @param dest   目标文件地址
     * @throws IOException
     */
    private static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }


    /**
     * 获取指定网页源码
     *
     * @param url 指定网页地址(例如："http://www.baidu.com")
     * @throws InterruptedException
     */
    public static void getWebPageCode(String url) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        // 1.调用chrome driver驱动程序
        System.setProperty("webdriver.chrome.driver", DRIVER_ADDR);
        // 2.创建 chrome option 对象
        ChromeOptions chromeOptions = new ChromeOptions();

        // 3.1 属性设置：无图模式
        Map<String, Object> prefs = new HashMap<>(2);
        prefs.put("profile.managed_default_content_settings.images", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        // 3.2 设置免检测（开发者模式）
        List<String> excludeSwitches = new ArrayList<>();
        excludeSwitches.add("enable-automation");
        chromeOptions.setExperimentalOption("excludeSwitches", excludeSwitches);
        // 3.3 设置代理IP
        // String ip = "localhost:8080";
        // chromeOptions.addArguments("--proxy-server=http://" + ip);
        // 3.4 设置无头
        chromeOptions.addArguments("--headless");

        // 4 浏览器设置：大小
        WebDriver driver = new ChromeDriver(chromeOptions);
        // 调整浏览器大小
        driver.manage().window().setSize(new Dimension(1300, 800));
        // 4.1 设置 Cookie
//        Cookie cookie = new Cookie("name", "value");
//        driver.manage().addCookie(cookie);


        // 正式获取网页数据
        driver.get(url);


        // 休眠1s,为了让js执行完
        Thread.sleep(1000L);

        System.out.println("网页源码：\n" + driver.getPageSource());

        driver.close();
        System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
    }


}
