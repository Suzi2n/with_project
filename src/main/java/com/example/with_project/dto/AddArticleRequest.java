package com.example.with_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.with_project.entity.Article;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    // 추가: MultipartFile 필드   2025/02/24
    private MultipartFile imageFile;

    public Article toEntity(String author, String imageUrl) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .imageUrl(imageUrl)
                .build();
    }
}