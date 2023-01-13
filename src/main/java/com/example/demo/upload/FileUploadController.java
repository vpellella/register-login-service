package com.example.demo.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@RestController
@Slf4j
public class FileUploadController {

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("Beginning of uploadservice");
        File newFile = new File("src/main/resources/temp/" + file.getOriginalFilename());
        try {
            System.out.println(newFile.createNewFile());
            Files.write(newFile.toPath(), file.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            return "Errors exists : " + e.getMessage();
        }
        return "File uploaded successfully";
    }
}