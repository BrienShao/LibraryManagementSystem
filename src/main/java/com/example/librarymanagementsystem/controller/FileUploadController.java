package com.example.librarymanagementsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String fileUpload(MultipartFile file) throws IOException {
        // 把文件存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        // 保证名字唯一性，防止文件覆盖
        file.transferTo(new File("C:\\Users\\86422\\Desktop\\images\\" + fileName));
        return "URL访问地址";
    }
}
