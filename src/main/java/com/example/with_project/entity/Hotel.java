package com.example.with_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "author", nullable = false)
    private String author;


    ///  2025/02-27 추가
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


//    // (가령) 이미지를 저장할 필드 추가   2025/02/24
//    @Column(name = "image_url")
//    private String imageUrl;


    // 이부분이 추가 2025/02/26
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleImage> articleImages = new ArrayList<>();


    // getters/setters
    // 추가 2025/2/28
    @Setter
    @Getter
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();


    // 추가 2025/02/26

    // 연관관계 편의 메서드
    public void addImage(ArticleImage articleImage) {
        this.articleImages.add(articleImage);
        articleImage.setHotel(this);
    }


    @Builder
    public Hotel(String author, String title, String content, String imageUrl, String address, BigDecimal price) {
        this.author = author;
        this.title = title;
        this.content = content;
      //  this.imageUrl = imageUrl;
        this.address = address;
        this.price = price;
    }

    public void update(String title, String content, String address, BigDecimal price) {
        this.title = title;
        this.content = content;
      //  this.imageUrl = imageUrl;
        this.address = address;
        this.price = price;
    }



}