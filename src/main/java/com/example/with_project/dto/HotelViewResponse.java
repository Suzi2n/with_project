package com.example.with_project.dto;

import com.example.with_project.entity.Hotel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class HotelViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;
    private String address;
    private BigDecimal price;

    // 추가: MultipartFile 필드   2025/02/24
    //private MultipartFile imageFile;

    // 이미지 경로(혹은 URL)를 다중으로 보관
    private List<String> imageUrls;

    public HotelViewResponse(Hotel hotel) {
        this.id = hotel.getId();
        this.title = hotel.getTitle();
        this.content = hotel.getContent();
        this.createdAt = hotel.getCreatedAt();
        this.author = hotel.getAuthor();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();


//        // 'articleImages'에서 url만 뽑아서 리스트로 변환
//        this.imageUrls = article.getArticleImages().stream()
//                .map(ArticleImage::getUrl)
//                .toList();
    }
}