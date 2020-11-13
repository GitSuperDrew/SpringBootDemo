package com.study.module.springbootmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传
 *
 * @author Administrator
 * @date 2020/11/13 下午 8:19
 */
@Controller
@RequestMapping(value = "/fileUpload")
public class FileUploadController {

    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @GetMapping("/redirectFilePage")
    public String index() {
        return "/file-upload/file";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "文件为空,请选择你的文件上传");
            return "redirect:uploadStatus";
        }
        saveFile(file);
        redirectAttributes.addFlashAttribute("msg", "上传文件" + file.getOriginalFilename() + "成功");
        redirectAttributes.addFlashAttribute("url", "/upload/" + file.getOriginalFilename());
        return "redirect:uploadStatus";
    }

    private void saveFile(MultipartFile file) throws IOException {
        Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
        file.transferTo(path);
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "file-upload/uploadStatus";
    }
}
