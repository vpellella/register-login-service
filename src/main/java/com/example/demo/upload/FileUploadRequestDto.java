package com.example.demo.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileUploadRequestDto {

    private String fileName;
    private File file;

}
