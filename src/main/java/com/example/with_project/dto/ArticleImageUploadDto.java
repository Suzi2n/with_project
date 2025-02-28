package com.example.with_project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ArticleImageUploadDto {    ///  MultipartFile을 List로 받는 간단한 DTO
    private List<MultipartFile> files;
}
