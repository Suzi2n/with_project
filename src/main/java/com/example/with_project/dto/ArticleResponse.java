package com.example.with_project.dto;

import lombok.Getter;
import com.example.with_project.entity.Article;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    // 이미지 경로(혹은 URL)
    private final String imageUrl;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrl = article.getImageUrl();
    }
}