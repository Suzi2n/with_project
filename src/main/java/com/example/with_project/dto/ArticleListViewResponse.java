package com.example.with_project.dto;


import lombok.Getter;
import com.example.with_project.entity.Article;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 추가: MultipartFile 필드   2025/02/24
    private MultipartFile imageFile;

    // 이미지 경로(혹은 URL)
    private String imageUrl;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrl = article.getImageUrl();
    }
}