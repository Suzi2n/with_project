package com.example.with_project.dto;


import com.example.with_project.entity.Hotel;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class HotelListViewResponse {

    private final Long id;
    private final String title;
    private final String content;


    // 추가 2025/02/27
    private final String address;
    private final BigDecimal price;


//    // 다중 이미지 경로
//    private final List<String> imageUrls;

    public HotelListViewResponse(Hotel hotel) {
        this.id = hotel.getId();
        this.title = hotel.getTitle();
        this.content = hotel.getContent();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();

//        // article.getArticleImages() → 각 ArticleImage에서 url 추출
//        this.imageUrls = article.getArticleImages().stream()
//                .map(image -> image.getUrl())
//                .toList();
    }
}