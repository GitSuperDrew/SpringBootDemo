package com.study.module.mybatis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;

/**
 * 获取当前系统信息
 *
 * @author Administrator
 */
public class SystemInfoUtil {
    // 当前实例
    private static SystemInfoUtil currentSystem = null;
    private InetAddress localHost = null;

    private SystemInfoUtil() {
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 单例模式获取对象
     *
     * @return
     */
    public static SystemInfoUtil getInstance() {
        if (currentSystem == null) {
            currentSystem = new SystemInfoUtil();
        }
        return currentSystem;
    }

    /**
     * 本地IP
     *
     * @return IP地址
     */
    public String getIP() {
        String ip = localHost.getHostAddress();
        return ip;
    }

    /**
     * 获取用户机器名称
     *
     * @return
     */
    public String getHostName() {
        return localHost.getHostName();
    }

    /**
     * 获取C盘卷 序列号
     *
     * @return
     */
    public String getDiskNumber() {
        String line = "";
        // 记录硬盘序列号
        String HdSerial = "";
        try {
            // 获取命令行参数
            Process proces = Runtime.getRuntime().exec("cmd /c dir c:");
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream()));
            while ((line = buffreader.readLine()) != null) {
                // 读取参数并获取硬盘序列号
                if (line.indexOf("卷的序列号是 ") != -1) {
                    HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HdSerial;
    }

    /**
     * 获取Mac地址
     *
     * @return Mac地址，例如：F0-4D-A2-39-24-A6
     */
    public String getMac() {
        NetworkInterface byInetAddress;
        try {
            byInetAddress = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = byInetAddress.getHardwareAddress();
            return getMacFromBytes(hardwareAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前系统名称
     *
     * @return 当前系统名，例如： windows xp
     */
    public String getSystemName() {
        Properties sysProperty = System.getProperties();
        // 系统名称
        String systemName = sysProperty.getProperty("os.name");
        return systemName;
    }

    private String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }

    public static void main(String[] args) {
        // 1.系统属性
        Properties sysProperty=System.getProperties();
        Set<Object> keySet = sysProperty.keySet();
        for (Object object : keySet) {
            String property = sysProperty.getProperty(object.toString());
            System.out.println(object.toString()+" : "+property);
        }

        // 2.中文属性参照如下
        System.out.println("Java的运行环境版本："+sysProperty.getProperty("java.version"));
        System.out.println("Java的运行环境供应商："+sysProperty.getProperty("java.vendor"));
        System.out.println("Java供应商的URL："+sysProperty.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径："+sysProperty.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本："+sysProperty.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商："+sysProperty.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称："+sysProperty.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本："+sysProperty.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商："+sysProperty.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称："+sysProperty.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本："+sysProperty.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商："+sysProperty.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称："+sysProperty.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号："+sysProperty.getProperty("java.class.version"));
        System.out.println("Java的类路径："+sysProperty.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表："+sysProperty.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径："+sysProperty.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径："+sysProperty.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称："+sysProperty.getProperty("os.name"));
        System.out.println("操作系统的构架："+sysProperty.getProperty("os.arch"));
        System.out.println("操作系统的版本："+sysProperty.getProperty("os.version"));
        System.out.println("文件分隔符："+sysProperty.getProperty("file.separator"));   //在 unix 系统中是＂／＂
        System.out.println("路径分隔符："+sysProperty.getProperty("path.separator"));   //在 unix 系统中是＂:＂
        System.out.println("行分隔符："+sysProperty.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
        System.out.println("用户的账户名称："+sysProperty.getProperty("user.name"));
        System.out.println("用户的主目录："+sysProperty.getProperty("user.home"));
        System.out.println("用户的当前工作目录："+sysProperty.getProperty("user.dir"));

    }
}
