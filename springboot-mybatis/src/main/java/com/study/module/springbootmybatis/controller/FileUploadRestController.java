package com.study.module.springbootmybatis.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传 Restful 接口
 *
 * @author Administrator
 * @date 2020/11/13 下午 8:48
 */
@RestController
@RequestMapping(value = "/rest/fileUpload")
public class FileUploadRestController {

    /**
     * 文件名长度
     */
    private static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_EXTENSIONS = {
            "jpg", "img", "png", "gif"
    };

    /**
     * 项目路径
     */
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @PostMapping("/restUpload")
    public Map<String, Object> singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("文件为空!");
        }
        String filename = upload(file);
        String url = "/upload/" + filename;
        Map<String, Object> map = new HashMap<>(2);
        map.put("msg", "上传成功");
        map.put("url", url);
        return map;
    }


    /**
     * 上传方法
     */
    private String upload(MultipartFile file) throws Exception {
        int len = file.getOriginalFilename().length();
        if (len > DEFAULT_FILE_NAME_LENGTH) {
            throw new Exception("文件名超出限制!");
        }
        String extension = getExtension(file);
        if (!isValidExtension(extension)) {
            throw new Exception("文件格式不正确");
        }
        // 自定义文件名
        String filename = getPathName(file);
        // 获取file对象
        File desc = getFile(filename);
        // 写入file
        file.transferTo(desc);
        return filename;
    }

    /**
     * 获取file对象
     */
    private File getFile(String filename) throws IOException {
        File file = new File(UPLOADED_FOLDER + "/" + filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 验证文件类型是否正确
     */
    private boolean isValidExtension(String extension) {
        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 此处自定义文件名,uuid + extension
     */
    private String getPathName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID().toString() + "." + extension;
    }

    /**
     * 获取扩展名
     */
    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }
}
