package com.example.with_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;

       // 추가: MultipartFile 필드   2025/02/24
    private MultipartFile imageFile;

    /// 추가 225/02/27

    private String address;
    private BigDecimal price;


}