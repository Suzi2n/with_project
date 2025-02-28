package com.example.with_project.dto;

import com.example.with_project.entity.Hotel;
import lombok.Getter;

import java.math.BigDecimal;

@Getter

public class HotelResponse {

    private final Long id;     /// 추가 2025/02/26
    private final String title;
    private final String content;
    private final String address;   // 추가 2025/02/27
    private final BigDecimal price;

//    private final List<String> imageUrls;/// 여러 장

    public HotelResponse(Hotel hotel) {
        this.id = hotel.getId();
        this.title = hotel.getTitle();
        this.content = hotel.getContent();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();

//        this.imageUrls = article.getArticleImages().stream()
//                .map(ArticleImage::getUrl)
//                .toList();
    }
}