package com.example.with_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.with_project.dto.AddArticleRequest;
import com.example.with_project.dto.UpdateArticleRequest;
import com.example.with_project.entity.Article;
import com.example.with_project.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;

    // 예시: 로컬 디렉토리에 저장
    private static final String UPLOAD_DIR = "C:/uploads";

    /**
     * 게시글 생성 (이미지 파일 포함)
     */
    public Article save(AddArticleRequest request, String userName) {
        // 1) 이미지 파일이 있으면 로컬에 업로드
        String storedFilePath = null;
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            storedFilePath = uploadFile(request.getImageFile());
        }

        // 2) DTO -> 엔티티 변환 시 imageUrl(경로)까지 전달
        Article article = request.toEntity(userName, storedFilePath);

        // 3) DB 저장
        return blogRepository.save(article);
    }

    /**
     * 모든 게시글 조회
     */
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    /**
     * 단일 게시글 조회
     */
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    /**
     * 게시글 삭제
     */
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        // 작성자 본인 여부 확인
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    /**
     * 게시글 수정 (이미지 파일 교체 가능)
     */
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        // 작성자 본인 여부 확인
        authorizeArticleAuthor(article);

        // 기존 imageUrl 가져오기
        String newImageUrl = article.getImageUrl();
        // 새 이미지가 업로드되었다면 파일 교체
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            newImageUrl = uploadFile(request.getImageFile());
        }

        // 엔티티 수정 (제목, 내용, 이미지 경로)
        article.update(
                request.getTitle(),
                request.getContent(),
                newImageUrl
        );
        return article;
    }

    /**
     * 로컬 디렉토리에 이미지 파일 업로드 후 경로 반환
     */
    private String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            // 업로드 폴더가 없으면 생성
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 원본 파일명에서 확장자 추출
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 파일명 충돌 방지를 위해 UUID 사용
            String newFileName = UUID.randomUUID() + ext;
            String filePath = UPLOAD_DIR + "/" + newFileName;

            // 실제 파일 저장
            file.transferTo(new File(filePath));

            // DB에는 절대경로 전체 대신, 필요한 경로만 저장해도 됨
            // ex) return "/images/" + newFileName;
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    /**
     * 작성자와 현재 사용자가 일치하는지 확인
     */
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}



/*  2025/02/24 주석

package com.example.with_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.with_project.entity.Article;
import com.example.with_project.dto.AddArticleRequest;
import com.example.with_project.dto.UpdateArticleRequest;
import com.example.with_project.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}

 */