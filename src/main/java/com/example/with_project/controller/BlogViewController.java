package com.example.with_project.controller;

import com.example.with_project.dto.HotelListViewResponse;
import com.example.with_project.dto.HotelViewResponse;
import com.example.with_project.entity.Hotel;
import com.example.with_project.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;
    @GetMapping("/hotels")
    public String getHotels(Model model) {
        List<HotelListViewResponse> hotels = blogService.findAll().stream()
                .map(HotelListViewResponse::new)
                .toList();

        model.addAttribute("hotels", hotels);

        return "hotelList";
    }

    @GetMapping("/hotels/{id}")
    public String getHotel(@PathVariable Long id, Model model) {
        Hotel hotel = blogService.findById(id);

        model.addAttribute("hotel", new HotelViewResponse(hotel));
        return "hotel";
    }

    @GetMapping("/new-hotel")
    public String newHotel(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            // 새 글 작성
            model.addAttribute("hotel", new HotelViewResponse());
        } else {
            // 기존 글 불러오기
            Hotel hotel = blogService.findById(id);
            model.addAttribute("hotel", new HotelViewResponse(hotel));
        }

        return "newHotel";
    }
}