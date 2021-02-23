package com.study.module.util.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * FTP 文件上传和下载
 *
 * @author drew
 * @date 2021/2/19 11:06
 **/
public class FtpUtils {

    public static void main(String[] args) {
        String ftpHost = "192.168.20.40";
        String ftpUserName = "test";
        String ftpPassword = "12345";
        int ftpPort = 21;
        String ftpPath = "test/";
        String fileName = "test_通知_4724.docx";
        //上传一个文件
        try {
            String tmpPath = System.getProperty("java.io.tmpdir");
            FileInputStream in = new FileInputStream(new File("D:\\" + fileName));
            FtpUtils.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, in);
            System.out.println(tmpPath);
            FtpUtils.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, "E:\\", "test_通知_4724.docx", "测试下载文件.docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static Log logger = LogFactory.getLog(FtpUtils.class);

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return 连接对象
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);
            // 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);
            // 登陆FTP服务器
            ftpClient.setControlEncoding("UTF-8");
            // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param ftpHost        FTP IP地址
     * @param ftpUserName    FTP 用户名
     * @param ftpPassword    FTP用户名密码
     * @param ftpPort        FTP端口
     * @param ftpPath        FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath      下载到本地的位置 格式：H:/download
     * @param fileName       FTP服务器上要下载的文件名称
     * @param targetFileName FTP服务器上要下载的文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath, String localPath, String fileName, String targetFileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.changeWorkingDirectory(ftpPath);
            String ftpCharset = new String(fileName.getBytes("GBK"), FTP.DEFAULT_CONTROL_ENCODING);
            //编码文件格式,解决中文文件名
            File localFile = new File(localPath + File.separatorChar + targetFileName);
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(ftpCharset, os);
            os.close();
            ftpClient.logout();
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            logger.error("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件读取错误。");
            e.printStackTrace();
        }
    }

    /**
     * 向FTP服务器上传文件
     *
     * @param ftpHost     FTP服务器hostname
     * @param ftpUserName FTP登录账号
     * @param ftpPassword FTP登录密码
     * @param ftpPort     FTP服务器端口
     * @param ftpPath     FTP服务器基础目录 (FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath)
     * @param filename    上传到FTP服务器上的文件名
     * @param input       输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.changeWorkingDirectory(ftpPath);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }
            filename = new String(filename.getBytes("GBK"), FTP.DEFAULT_CONTROL_ENCODING);
            //编码文件名，支持中文文件名
            // 上传文件
            if (!ftpClient.storeFile(filename, input)) {
                return false;
            }
            input.close();
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }


}
