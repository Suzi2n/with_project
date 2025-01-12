package com.example.with_project.dto;

import lombok.Getter;
import com.example.with_project.entity.Article;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;
    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}