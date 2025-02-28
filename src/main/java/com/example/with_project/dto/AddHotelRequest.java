package com.example.with_project.dto;

import com.example.with_project.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddHotelRequest {
    private String title;
    private String content;

    private String address;
    private BigDecimal price;

    // 추가: MultipartFile 필드   2025/02/24
    //private MultipartFile imageFile;

    // 이부분 추가
    //private List<String> imageUrls;

    // 다중 이미지: MultipartFile 리스트
    private List<MultipartFile> files;

    public Hotel toEntity(String author) {
        return Hotel.builder()
                .title(title)
                .content(content)
                .author(author)
                .address(address)
                .price(price)
                .build();
    }
}