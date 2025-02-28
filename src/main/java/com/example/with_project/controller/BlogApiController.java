package com.example.with_project.controller;


import com.example.with_project.dto.HotelResponse;
import com.example.with_project.entity.Hotel;
import lombok.RequiredArgsConstructor;
import com.example.with_project.dto.AddHotelRequest;
import com.example.with_project.dto.UpdateArticleRequest;
import com.example.with_project.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
/*
@RequestMapping("/api/articles")    // 2025/02/27 주석 처리. 이미지 주석처리 후 이거 없애니까 글 등록 완료

 */

/*
public class BlogApiController {

    private final BlogService blogService;


    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }


    /**
     * 글 작성 (multipart/form-data)
     */
    // consumes 설정을 통해 multipart 요청임을 명시

/*

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HotelResponse> addArticle(
            @ModelAttribute AddArticleRequest request, // DTO에 MultipartFile 필드 포함
            Authentication authentication
    ) {
        // 1) SecurityContext의 로그인 사용자 정보 획득
        var userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername(); // 실제 로그인된 아이디

        // 2) 서비스 호출 시 로그인된 사용자명(또는 ID)을 넘김
        Article savedArticle = blogService.save(request, username);

        // 3) 결과 리턴
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new HotelResponse(savedArticle));
    }



    /**
     * 전체 글 조회 (JSON)
     */

/*
    @GetMapping
    public ResponseEntity<List<HotelResponse>> findAllArticles() {
        List<HotelResponse> articles = blogService.findAll().stream()
                .map(HotelResponse::new)
                .toList();
        return ResponseEntity.ok(articles);
    }

    /**
     * 단건 조회
     */

/*
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(new HotelResponse(article));
    }

    /**
     * 글 수정 (multipart/form-data)
     */

/*
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HotelResponse> updateArticle(
            @PathVariable long id,
            @ModelAttribute UpdateArticleRequest request
    ) {
        Article updatedArticle = blogService.update(id, request);
        HotelResponse response = new HotelResponse(updatedArticle);
        return ResponseEntity.ok(response);
    }

    /**
     * 글 삭제
     */

/*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }
}

*/    // 주석 2025/02/24
public class BlogApiController {
    private final BlogService blogService;
    @PostMapping("/api/hotels")
    public ResponseEntity<Hotel> addHotel(@RequestBody AddHotelRequest request, Principal principal) {
        Hotel savedHotel = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedHotel);
    }

    @GetMapping("/api/hotels")
    public ResponseEntity<List<HotelResponse>> findAllHotels() {
        List<HotelResponse> hotels = blogService.findAll()
                .stream()
                .map(HotelResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(hotels);
    }

    @GetMapping("/api/hotels/{id}")
    public ResponseEntity<HotelResponse> findHotel(@PathVariable long id) {
        Hotel hotel = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new HotelResponse(hotel));
    }

    @DeleteMapping("/api/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/hotels/{id}")
    public ResponseEntity<Hotel> updateArticle(@PathVariable long id,
                                               @RequestBody UpdateArticleRequest request) {
        Hotel updatedHotel = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedHotel);
    }
}

