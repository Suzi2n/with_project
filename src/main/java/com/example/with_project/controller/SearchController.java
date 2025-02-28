package com.example.with_project.controller;

import com.example.with_project.entity.Hotel;
import com.example.with_project.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final BlogService blogService;

    @GetMapping("/search")
    public String searchHotels(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(required = false) String max,
            Model model) {

//        // BlogService에서 검색 조건에 맞는 Article 목록을 조회하는 메서드가 있다고 가정
//        List<Article> articles = blogService.searchArticles(address, checkIn, checkOut, max);
//        model.addAttribute("articles", articles);
//        model.addAttribute("searchParams",
//                String.format("address=%s&checkIn=%s&checkOut=%s&max=%s", address, checkIn, checkOut, max));

     //    BlogService에서 검색 조건에 맞는 Article 목록을 조회하는 메서드가 있다고 가정
        List<Hotel> hotels = blogService.searchHotels(address);
        model.addAttribute("hotels", hotels);
        model.addAttribute("searchParams",
               address);
        return "hotelList"; // 검색 결과를 articleList.html로 표시 (필요 시 수정)
    }
}
