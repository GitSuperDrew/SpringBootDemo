package com.study.module.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 知识来源地址：https://www.cnblogs.com/wakey/p/10862858.html
 *
 * @author drew
 * @date 2021/2/2 16:09
 **/
public class ChromeDriverApiUsingDemo {

    public static void main(String[] args) throws Exception {

//        isElementText(); // 重点方法
        testIsElement();

//        testCookie();
//        executeJavaScript();
//        screen();
//        operateCheckBox();
//        operateRadio();
//        operateDropList();
//        doubleClick();
//        clickButton();
//        clearText();
//        getCurrentUrl();
//        visitRecentUrl();
//        operateBrower();
//        getTitle();

//        roverOnElement();
//        operatePrompt();
//        operateAlert();
//        operateConfirm();
//        operateWindowByPageSource();

//        clickKeyboardKeys();
//        rightClickMouse();

//        getWeatherElementAttribute();
//        getWeatherElementCss();
    }

    private static WebDriver driver;
    // D:\workspace\github_projects\WebGo\html\函数工具→演示\demo-html.html

    /**
     * 判断页面元素是否存在
     *
     * @param by 根据什么元素
     * @return 是否找到元素了
     */
    public static boolean isElement(By by) {
        WebElement element = driver.findElement(by);
        return element != null;
    }

    public static void testIsElement() {
        initDriver();

        driver.get("http://www.baidu.com");
        if (isElement(By.id("kw"))) {
            WebElement webElement = driver.findElement(By.id("kw"));
            if (webElement.isEnabled()) {
                webElement.sendKeys("百度的首页搜索框被成功找到");
            }
        } else { //将测试用例设置为失败，并打印失败原因
            System.out.println("页面的输入框元素未找到");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }

    /**
     * 常用的显式等待
     */
    public static void testWait() {
        //声明一个WebDriverWait对象，设置最长等待时间为10秒
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //判断页面title是否包含“测试页面”4个字
        String str = "测试页面";
        wait.until(ExpectedConditions.titleContains("测试页面"));
        System.out.println("页面标题出现了‘测试页面’4个字");
    }

    /**
     * 获取页面元素的css属性样式值
     */
    public static void getWeatherElementCss() {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        WebElement input = driver.findElement(By.id("kw"));
        String cssWidth = input.getCssValue("width");
        String cssHeight = input.getCssValue("height");
        String background = input.getCssValue("background-color");
        System.out.println(Arrays.asList(cssHeight, cssWidth, background));
        driver.close();
    }

    /**
     * 查看页面元素的属性
     */
    public static void getWeatherElementAttribute() throws Exception {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        String str = "今天天气不错";
        WebElement input = driver.findElement(By.id("kw"));
        input.sendKeys(str);
        String inputText = input.getAttribute("value");
        System.out.println("....." + inputText);
        // Assert.assertEquals(inputText,"今天天气不错");
        driver.close();
    }

    /**
     * 模拟鼠标右击事件
     */
    public static void rightClickMouse() {
        initDriver();

        driver.get("http://www.baidu.com");
        Actions action = new Actions(driver);
        //模拟鼠标右击事件
        action.contextClick(driver.findElement(By.id("su"))).perform();
        driver.close();
    }

    /**
     * 模拟键盘的操作
     */
    public static void clickKeyboardKeys() throws Exception {
        initDriver();

        driver.get("http://www.baidu.com");
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);//按下ctrl键
        action.keyDown(Keys.SHIFT);//按下shift键
        action.keyDown(Keys.ALT);//按下alt键

        action.keyUp(Keys.CONTROL);//释放ctrl键
        action.keyUp(Keys.SHIFT);//释放shift键
        action.keyUp(Keys.ALT);//释放alt键

        //模拟键盘在输入框中输入TEST
        action.keyDown(Keys.SHIFT).sendKeys("test").perform();
        Thread.sleep(1000);
        driver.quit();
    }

    /**
     * 使用页面的文字内容识别和处理新弹出的浏览器窗口
     */
    public static void operateWindowByPageSource() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        //将当前浏览器窗口句柄存在一个变量中
        String parentWindowHandle = driver.getWindowHandle();
        //点击页面上的链接地址
        driver.findElement(By.xpath("//a")).click();
        //获取当前所有打开的窗口的句柄，并存在set中
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            try {
                if (driver.switchTo().window(windowHandle).getPageSource().contains("百度一下")) {
                    driver.findElement(By.id("kw")).sendKeys("百度首页的浏览器窗口被找到");
                }
            } catch (NoSuchWindowException e) {
                e.printStackTrace();
            }
        }
        //返回到最开始打开的浏览器窗口
        driver.switchTo().window(parentWindowHandle);
        // Assert.assertEquals(driver.getTitle(),"测试页面");
        System.out.println("测试页面的标题:\t" + driver.getTitle());
    }


    /**
     * 操作javascript的Alter窗口
     */
    public static void operateAlert() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        //查找到按钮元素
        WebElement button = driver.findElement(By.name("alertBtn"));
        button.click();

        try {
            //获取alert对象
            Alert alert = driver.switchTo().alert();
            // Assert.assertEquals("这是一个alert弹窗", alert.getText());
            System.out.println("这是一个alert弹窗" + alert.getText());
            //关闭弹窗
            alert.accept();
        } catch (NoAlertPresentException e) {
            // Assert.fail("页面alert弹窗未找到");
            System.out.println("页面 alert 弹窗未找到");
            e.printStackTrace();
        }
    }

    /**
     * 操作javascript的confirm窗口
     */
    public static void operateConfirm() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        driver.findElement(By.name("confirmBtn")).click();

        try {
            Alert alert = driver.switchTo().alert();
            // Assert.assertEquals("这是一个confirm弹窗", alert.getText());
            System.out.println("这是一个confirm弹窗" + alert.getText());
            alert.accept(); // 点击确定，关闭弹窗
            //alert.dismiss(); // 点击取消，关闭弹出窗
        } catch (NoAlertPresentException e) {
            // Assert.fail("页面confirm弹窗未找到");
            System.out.println("页面 confirm 弹窗未找到");
            e.printStackTrace();
        }
    }


    /**
     * 操作javascript的prompt窗口
     *
     * @throws Exception
     */
    public static void operatePrompt() throws Exception {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        driver.findElement(By.name("promptbtn")).click();

        try {
            Alert alert = driver.switchTo().alert();
            // Assert.assertEquals("这是一个prompt弹窗", alert.getText());
            System.out.println("弹窗标题: " + alert.getText());
            alert.sendKeys("好好学习，天天向上");
            Thread.sleep(3000);
            alert.accept();
        } catch (NoAlertPresentException e) {
            // Assert.fail("页面prompt弹窗未找到");
            System.out.println("页面弹窗未找到");
            e.printStackTrace();
        }
    }

    /**
     * 在指定元素上方进行鼠标悬浮,及点击悬浮后出现的菜单
     *
     * @throws Exception
     */
    public static void roverOnElement() throws Exception {
        // 初始化浏览器
        initDriver();

        driver.get("http://www.baidu.com");
        Actions action = new Actions(driver);
        // 在指定元素上进行鼠标悬浮
        action.moveToElement(driver.findElement(By.name("tj_briicon"))).perform();
        Thread.sleep(10000);
        // 点击悬浮后出现的菜单
        driver.findElement(By.linkText("糯米")).click();
        driver.close();
    }

    /**
     * 使用frame中的html源码内容来操作frame
     */
    public void testHandleFrame() {
        initDriver();

        // 需要由ifrmeset布局的页面（左上中下右）
        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        // 找到所有frame标签的内容
        List<WebElement> frames = driver.findElements(By.tagName("frame"));
        for (WebElement frame : frames) {
            driver.switchTo().frame(frame);
            // 判断frame页面源码中是否包含“中间 frame”
            if (driver.getPageSource().contains("中间 frame")) {
                // 找到页面P标签页面对象
                WebElement text = driver.findElement(By.xpath("//p"));
                // Assert.assertEquals("这是中间 frame 页面上的文字", text.getText());
                System.out.println("这是中间的 frame 页面上的文字: " + text.getText());
                break;
            } else {
                // 返回frameset页面
                driver.switchTo().defaultContent();
            }
        }
        driver.switchTo().defaultContent();
        driver.close();
    }


    /**
     * 操作iframe的页面元素
     */
    public static void testHandleFrame2() {
        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        driver.switchTo().frame("leftframe");
        //找到包含“这是iframe 页面上的文字”的元素对象
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        //进入iframe页面区域
        driver.switchTo().frame(iframe);
        //在iframe页面找p标签的页面元素
        WebElement p = driver.findElement(By.xpath("//p"));
//        Assert.assertEquals("这是iframe 页面上的文字", p.getText());
        System.out.println("iframe 页面上的文字：" + p.getText());
        driver.close();
    }


    /**
     * 操作浏览器的cookie
     */
    public static void testCookie() {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        //得到当前页面下所有的cookie，并输出所在域、name、value、有效时期和路径
        Set<Cookie> cookies = driver.manage().getCookies();
        Cookie cookie = new Cookie("cookieName", "cookieValue");
        System.out.println(String.format("Domain->name->value->expiry->path"));
        for (Cookie cookie2 : cookies) {
            System.out.println(String.format(
                    "%s->%s->%s->%s->%s",
                    cookie2.getDomain(),
                    cookie2.getName(),
                    cookie2.getValue(),
                    cookie2.getExpiry(),
                    cookie2.getPath()
            ));
        }
        // >>> 获取 cookie
        System.out.println("打印所有的 cookies ");
        Iterator<Cookie> cookieIterator = driver.manage().getCookies().iterator();
        while (cookieIterator.hasNext()) {
            System.out.println(cookieIterator.next().getName() + ">>>>" + cookieIterator.next().getValue());
        }

        // >>> 删除cookie
        //通过cookie的namen属性删除
        driver.manage().deleteCookieNamed("cookieName");
        //通过cookie对象删除
        driver.manage().deleteCookie(cookie);
        //删除全部cookie
        driver.manage().deleteAllCookies();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.close();
    }


    /**
     * 执行javascript脚本
     */
    public static void executeJavaScript() {
        initDriverHeadless();

        driver.get("D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");


        //声明一个JavaScript执行对象
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = (String) js.executeScript("return document.title");
        System.out.println("expected: " + title);
        // String bottonText = (String) js.executeScript("var button = document.getElementById('su').type='hidden';");
        String bottonText = (String) js.executeScript("var ppInterval = setInterval(function(){\n" +
                "        if(performance.timing.loadEventEnd > 0){\n" +
                "            setTimeout(function(){\n" +
                "                var div = document.createElement('div');\n" +
                "                div.id = 'screenPrint';\n" +
                "                document.body.append(div);\n" +
                "            }, 1500);\n" +
                "            clearInterval(ppInterval);\n" +
                "        }\n" +
                "    }, 50)");
        System.out.println("》》》》》》" + bottonText);


        // 执行JavaScript脚本二：
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        String functionBody = "return arguments[1]+','+arguments[2]";
        String returnRes = (String) jsExec.executeScript(functionBody, 1, "hello", "selenium");
        System.out.println("。。。。" + returnRes);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取刚生成的dom节点
        // WebElement element1 = driver.findElement(By.xpath("//div[@id='screenPrint']"));
        WebElement element = driver.findElement(By.id("screenPrint"));
        System.out.println("》》》》》 " + element);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }


    /**
     * 将当前浏览器窗口截屏(注意：不是全网页截图)
     */
    public static void screen() {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(setDimension(true));
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String filePathName = "D:/test-" + UUID.randomUUID().toString().substring(0, 8) + ".png";
            FileUtils.copyFile(file, new File(filePathName));
            System.out.println("截图成功！文件存放地址为：" + filePathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * 操作复选框
     *
     * @throws Exception
     */
    public static void operateCheckBox() throws Exception {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        //查找属性为汽车的复选框元素
        WebElement element = driver.findElement(By.xpath("//input[@value='bus']"));
        //如果复选框未选中，则选中
        if (!element.isSelected()) {
            element.click();
        }
        System.out.println("是否选中？" + element.isSelected());
        //如果复选框被选中，则取消选中
        /*if(element.isSelected()){
            element.click();
        }
        Assert.assertTrue(element.isSelected());*/
        //查找属性为fruit3的元素,并选中
        List<WebElement> list = driver.findElements(By.name("fruit3"));
        for (WebElement checkbox : list) {
            checkbox.click();
        }
        Thread.sleep(2000);
        driver.close();
    }


    /**
     * 操作单选框
     */
    public static void operateRadio() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        //查找属性值为radio的单选按钮对象
        WebElement element = driver.findElement(By.xpath("//input[@value='radio']"));
        //如果这个单选项未被选中，则执行click()方法选中这个按钮
        if (!element.isSelected()) {
            element.click();
        }
        //断言属性值为 radio 的单选按钮是否处于选中状态
        System.out.println("是否被选中：" + element.isSelected());

        //查找name属性值为username的所有对象
        List<WebElement> elements = driver.findElements(By.name("username"));
        //查找value属性为lisi的单选按钮对象，如果处于未选中状态，则执行click方法选中
        for (WebElement element2 : elements) {
            if (element2.getAttribute("value").equals("lisi")) {
                if (!element2.isSelected()) {
                    element2.click();
                    //断言单选按钮是否被选中
                    System.out.println("断言单选按钮是否被选中？" + element2.isSelected());
                    //成功选中后，退出
                    break;
                }
            }
        }
    }


    /**
     * 操作多选的选择列表
     */
    public static void operateMultiple() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        //找到页面的下拉列表元素
        WebElement element = driver.findElement(By.name("fruit2"));
        Select dropList = new Select(element);
        //判断下拉列表是否支持多选，支持多选isMultiple返回true
//        Assert.assertTrue();
        System.out.println("下拉框是否支持多选：" + dropList.isMultiple());
        //使用选择索引选择橘子选项
        dropList.selectByIndex(1);
        //使用选择value属性选择荔枝选项
        dropList.selectByValue("lizhi");
        //使用选项文字选择山楂
        dropList.selectByVisibleText("山楂");

        //取消所有选项的选中状态
        dropList.deselectAll();
        //再次选中3个数据
        dropList.selectByIndex(1);
        dropList.selectByValue("lizhi");
        dropList.selectByVisibleText("山楂");
        //取消索引为1的选项
        dropList.deselectByIndex(1);
        //取消value属性为lizhi的选项
        dropList.deselectByValue("lizhi");
        //取消选项文字为山楂的选项
        dropList.deselectByVisibleText("山楂");
        driver.close();
    }


    /**
     * 检查单选列表的选项文字是否条预期
     */
    public static void checkSelectText() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        WebElement element = driver.findElement(By.name("fruit"));
        Select dropList = new Select(element);
        //将下拉列表中期望出现的选项文字存在list集合中，Arrays.asList 将数组转换为list对象
        String[] arr = {"桃子", "橘子", "荔枝", "山楂"};
        List<String> expect_option = Arrays.asList(arr);
        //声明一个新的list，用于存取从页面上获取的所有选 项文字
        List<String> act_option = new ArrayList<>();
        for (WebElement option : dropList.getOptions()) {
            act_option.add(option.getText());
            //断言预期对象与实际对象是否完全一致
            System.out.println(Arrays.equals(expect_option.toArray(), act_option.toArray()));
        }
    }


    /**
     * 操作单选下拉列表
     */
    public static void operateDropList() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        WebElement element = driver.findElement(By.name("fruit"));
        Select dropList = new Select(element);
        //判断下拉列表是否可多选
        System.out.println("下拉列表是否可选？\t" + dropList.isMultiple());
        //断言当前选中的选项文本是否为桃子
        System.out.println("当前选中的选项文本是否为桃子？\t" + dropList.getFirstSelectedOption().getText());
        //选中下拉列表中的第2个选项
        dropList.selectByIndex(1);
        System.out.println("当前选中的是否为【橘子】\t" + dropList.getFirstSelectedOption().getText());
        //使用下拉列表选项的value属性值来选中操作
        dropList.selectByValue("lizhi");
//        Assert.assertEquals("荔枝", dropList.getFirstSelectedOption().getText());
        //通过选项的文字来进行操作
        dropList.selectByVisibleText("山楂");
//        Assert.assertEquals("山楂", dropList.getFirstSelectedOption().getText());
    }


    /**
     * 鼠标双击元素
     */
    public static void doubleClick() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        WebElement inputBox = driver.findElement(By.id("inputBox"));
        //声明Action对象
        Actions builder = new Actions(driver);
        builder.doubleClick(inputBox).build().perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    /**
     * 单击按钮
     */
    public static void clickButton() {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.id("button"));
        button.click();//单击按钮
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }


    /**
     * 获取页面的URL
     */
    public static void getCurrentUrl() {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("当前浏览的页面地址为：" + currentUrl);
        driver.close();
    }

    /**
     * 清除文本框中的内容，在文本框中输入指定内容
     *
     * @throws InterruptedException
     */
    public static void clearText() throws InterruptedException {
        initDriver();

        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");
        WebElement input = driver.findElement(By.id("text"));
        Thread.sleep(2000);
        input.clear(); //清除文本框中的内容
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //在文本框中输入指定内容
        input.sendKeys("selenium自动化测试");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
    }


    /**
     * 初始化浏览器窗口
     */
    public static void initDriver() {
        //webdriver对象的声明
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    /**
     * 无头浏览器初始化
     */
    public static void initDriverHeadless() {
        //webdriver对象的声明
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
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
    }

    /**
     * 判断元素是否存在
     */
    public static void isElementText() {
        initDriverHeadless();
        //判断p标签内容与selenium自动化测试是否完全匹配
        driver.get("file:///D:\\workspace\\github_projects\\WebGo\\html\\函数工具→演示\\demo-html.html");

        // 方式一：
        WebElement element = driver.findElement(By.xpath("//div[@id='flagTime']"));
        String text = element.getText();
        System.out.println("》》》》》 " + text);

        // 方式二：
        WebElement element1 = driver.findElement(By.id("flagTime"));
        if (!ObjectUtils.isEmpty(element1)) {
            System.out.println("DOM元素内容：" + element1.getText());
        }

        driver.quit();
    }

    /**
     * 页面操作，返回上一个页面，前进，刷新
     */
    public static void visitRecentUrl() {
        initDriver();
        String url1 = "http://www.baidu.com";
        String url2 = "http://www.sina.com";
        driver.navigate().to(url1);
        System.out.println("url1: driver.getPageSource() = " + driver.getPageSource());
        driver.navigate().to(url2);
        driver.navigate().back();//返回到上一个页面
        driver.navigate().forward();//前进到下一页面
        System.out.println("url2: driver.getPageSource() = " + driver.getPageSource());
        driver.navigate().refresh();//刷新当前页面
        driver.close();
    }

    /**
     * 操作浏览器窗口
     */
    public static void operateBrower() {
        initDriver();
        //设置浏览器的横纵坐标
        Point point = new Point(150, 150);
        //设置浏览器的宽高
        Dimension dimension = new Dimension(500, 500);
        driver.manage().window().setPosition(point);
        driver.manage().window().setSize(dimension);
        System.out.println(driver.manage().window().getPosition());
        System.out.println(driver.manage().window().getSize());
        driver.manage().window().maximize();//窗口最大化
        driver.get("http//www.baidu.com");
        driver.close();
    }

    /**
     * 获取页面的title属性
     */
    public static void getTitle() {
        initDriverHeadless();

        driver.get("http://www.baidu.com");
        String title = driver.getTitle();
        System.out.println(title);
        driver.close();
    }


    /**
     * Return a value calculated via javascript.
     *
     * @param javascriptCode Any valid javascript code with a return value
     * @param webDriver      无头浏览器
     * @param isAsync        true异步，false 同步
     * @return 执行脚本后的无头浏览器（异步）
     */
    public static String getByJavascript(String javascriptCode, WebDriver webDriver, boolean isAsync) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        return isAsync ? (String) javascriptExecutor.executeAsyncScript(javascriptCode) : (String) javascriptExecutor.executeScript(javascriptCode);
    }


}
