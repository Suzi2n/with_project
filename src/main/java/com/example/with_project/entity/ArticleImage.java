package com.example.with_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "article_image")
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url; // 실제 로컬 경로 or S3 경로 등

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Hotel hotel;

    public ArticleImage(String url) {
        this.url = url;
    }


    // 연관관계 세터
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

}
