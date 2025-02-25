package com.example.with_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.with_project.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    // 추가: MultipartFile 필드   2025/02/24
    //private MultipartFile imageFile;

    // 이미지 경로(혹은 URL)
    private String imageUrl;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.author = article.getAuthor();
        this.imageUrl = article.getImageUrl();
    }
}