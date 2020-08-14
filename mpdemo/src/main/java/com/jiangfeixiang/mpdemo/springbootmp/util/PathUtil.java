package com.jiangfeixiang.mpdemo.springbootmp.util;

import cn.hutool.http.HttpUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * @author Administrator
 * @date 2020/8/10 下午 1:35
 */
public class PathUtil {

    /**
     * 获取项目的绝对路径
     *
     * @return 项目的绝对路径
     */
    public static String getRootPath() {
        String classPath = "", rootPath = "";
        try {
            //防止有空格,%20等的出现
            classPath =
                    URLDecoder.decode(Objects.requireNonNull(PathUtil.class.getClassLoader().getResource("/")).getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!"".equals(classPath)) {
            //windows下
            if ("\\".equals(File.separator)) {
                rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
                rootPath = rootPath.replace("/", "\\");
            }
            //linux下
            if ("/".equals(File.separator)) {
                rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
                rootPath = rootPath.replace("\\", "/");
            }
        }
        return rootPath;
    }

    /**
     * 获取项目根目录的绝对路径
     *
     * @return 项目根目.例如<br /> F:\tomcat\webapps\J2EEUtil\
     */
    public static String getAbsolutePathWithWebProject(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取项目根目录下的指定目录的绝对路径
     *
     * @param path 项目根目下的指定目录.例如:/login/
     * @return 项目根目下的指定目录.例如:<br/> F:\tomcat\webapps\J2EEUtil\login\
     */
    public static String getAbsolutePathWithWebProject(HttpServletRequest request, String path) {
        return request.getSession().getServletContext().getRealPath(path);
    }

    /**
     * 获取项目根目录的绝对路径
     *
     * @return 项目根目.例如<br /> F:\tomcat\webapps\J2EEUtil\
     */
    public static String getAbsolutePathWithWebProject(ServletContext context) {
        return context.getRealPath("/");
    }

    /**
     * 获取项目根目录下的指定目录的绝对路径
     *
     * @param path 项目根目下的指定目录 .例如:/login/
     * @return 项目根目下的指定目录.例如:<br/> F:\tomcat\webapps\J2EEUtil\login\
     */
    public static String getAbsolutePathWithWebProject(ServletContext context, String path) {
        return context.getRealPath(path);
    }

    /**
     * 获取项目classpath目录的绝对路径
     *
     * @return classes目录的绝对路径<br /> file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/
     */
    public static URL getAbsolutePathWithClass() {
        return PathUtil.class.getResource("/");
    }

    /**
     * 获取项目classPath目录下的指定目录的绝对路径
     *
     * @param path classes目录下的指定目录.比如:/com/
     * @return file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/com/
     */
    public static URL getAbsolutePathWithClass(String path) {
        return PathUtil.class.getResource(path);
    }

    /**
     * 获取指定类文件的所在目录的绝对路径
     *
     * @param clazz 类
     * @return 类文件的绝对路径.例如:<br/> 包com.Aries.Util.Web下的Main.java类.<br/>
     * 路径为:file:/
     * F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/com/Aries/Util/Web/
     */
    public static URL getAbsolutePathWithClass(Class clazz) {
        return clazz.getResource("");
    }

    /**
     * 获取访问者用户的ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前系统的IP地址
     *
     * @return IP地址
     */
    public static String getServiceIp() {
        InetAddress address;
        String myIp = null;
        try {
            address = InetAddress.getLocalHost();
            myIp = address.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myIp;
    }


    public static void main(String[] args) {
        // 当前线程所在class类的项目路径
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        // Class文件所在路径（项目文件编译后class文件所在绝对路径）
        System.out.println(PathUtil.class.getResource("/"));
        // 系统的盘符 例如 E:\
        System.out.println(new File("/").getAbsolutePath());
        // 获取项目绝对起始路径，调用方法
        System.out.println(getAbsolutePathWithClass());
        // 获取：指定文件所在文件夹
        System.out.println(getAbsolutePathWithClass(PathUtil.class));
        // System.out.println(PathUtil.getRootPath());
        System.out.println(getServiceIp());
    }
}
