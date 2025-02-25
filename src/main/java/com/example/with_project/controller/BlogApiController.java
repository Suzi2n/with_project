package com.example.with_project.controller;


import lombok.RequiredArgsConstructor;
import com.example.with_project.entity.Article;
import com.example.with_project.dto.AddArticleRequest;
import com.example.with_project.dto.ArticleResponse;
import com.example.with_project.dto.UpdateArticleRequest;
import com.example.with_project.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class BlogApiController {

    private final BlogService blogService;

    /**
     * 글 작성 (multipart/form-data)
     */
    // consumes 설정을 통해 multipart 요청임을 명시
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArticleResponse> addArticle(
            @ModelAttribute AddArticleRequest request, // DTO에 MultipartFile 필드 포함
            Principal principal
    ) {
        // DB 저장
        Article savedArticle = blogService.save(request, principal.getName());
        // 엔티티 → 응답 DTO
        ArticleResponse response = new ArticleResponse(savedArticle);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 전체 글 조회 (JSON)
     */
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll().stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok(articles);
    }

    /**
     * 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(new ArticleResponse(article));
    }

    /**
     * 글 수정 (multipart/form-data)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable long id,
            @ModelAttribute UpdateArticleRequest request
    ) {
        Article updatedArticle = blogService.update(id, request);
        ArticleResponse response = new ArticleResponse(updatedArticle);
        return ResponseEntity.ok(response);
    }

    /**
     * 글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }
}

/*    // 주석 2025/02/24
public class BlogApiController {
    private final BlogService blogService;
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}

 */