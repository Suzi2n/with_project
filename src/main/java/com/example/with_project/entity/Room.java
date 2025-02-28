package com.example.with_project.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "room")

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기존의 Long hotelId 대신 Article 타입으로 선언
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String name;       // 객실 이름  (번호)

    @Column(nullable = false)
    private String content;    // 객실 설명

    @Column(name = "max")
    private int max;           // 최대 인원

    @Column
    private int bed;           // 침대 개수

    @Column(name = "normal_price", precision = 10, scale = 2)
    private BigDecimal normalPrice; // 객실 가격

    @Column(name = "remove_date")
    private LocalDateTime removeDate; // 삭제 여부 (null이면 사용중)

    // getters/setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    public int getBed() {
        return bed;
    }
    public void setBed(int bed) {
        this.bed = bed;
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }
    public void setNormalPrice(BigDecimal normalPrice) {
        this.normalPrice = normalPrice;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }
    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }
}
