package com.example.with_project.service;


import com.example.with_project.dto.AddHotelRequest;
import com.example.with_project.dto.UpdateArticleRequest;
import com.example.with_project.entity.Hotel;
import com.example.with_project.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    // 만약 ArticleImage에 대한 별도 Repository가 필요하다면:
    // private final ArticleImageRepository articleImageRepository;

    // 예시: 로컬 디렉토리에 저장
    private static final String UPLOAD_DIR = "C:/uploads";

    /**
     * 게시글 생성 (다중 이미지 파일 포함)
     */
    /*
    public Article save(AddArticleRequest request, String userName) {
        // 1) DTO -> Article 엔티티 생성 (아직 ArticleImage는 없음)
        Article article = request.toEntity(userName);

        // 2) 업로드할 파일이 있다면 로컬에 저장하고, 그 경로들로 ArticleImage 엔티티 생성
        List<MultipartFile> files = request.getFiles();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String storedFilePath = uploadFile(file);
                // 로컬 업로드 후 경로 반환
                if (storedFilePath != null) {
                    ArticleImage articleImage = new ArticleImage(storedFilePath);
                    // 편의 메서드를 통해 양방향 관계 설정
                    article.addImage(articleImage);
                }
            }
        }

        // 3) Article 저장 (cascade = ALL 이면, 연관된 ArticleImage도 자동 저장)
        return blogRepository.save(article);
    }

    */
    public Hotel save(AddHotelRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
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
            String ext = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 파일명 충돌 방지를 위해 UUID 사용
            String newFileName = UUID.randomUUID() + ext;
            String filePath = UPLOAD_DIR + "/" + newFileName;

            // 실제 파일 저장
            file.transferTo(new File(filePath));

            return filePath; // or "/images/" + newFileName 등
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    // 나머지 로직(조회, 삭제, 수정) ---------------------------------

    /**
     * 모든 게시글 조회
     */
    public List<Hotel> findAll() {
        return blogRepository.findAll();
    }

    /**
     * 단일 게시글 조회
     */
    public Hotel findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    /**
     * 게시글 삭제
     */
    public void delete(long id) {
        Hotel hotel = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(hotel);
        blogRepository.delete(hotel);
    }

    /**
     * 게시글 수정 (이미지 파일 교체 가능) - 다중 이미지일 경우 예시
     */

        // 일단 기존꺼 재활용
    @Transactional
    public Hotel update(long id, UpdateArticleRequest request) {
        Hotel hotel = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(hotel);
        hotel.update(request.getTitle(), request.getContent() , request.getAddress(), request.getPrice());

        return hotel;
    }

    /*
      ////////// 일단 이 기능은 주석 처리 !!!!

    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(article);

        // 1) Article 기본 정보 수정
        article.update(request.getTitle(), request.getContent());

        // 2) 새 이미지 파일들이 온다면 → 기존 이미지들을 지우고 새로 넣거나,
        //    또는 원하는 로직(추가만 한다든지...)으로 구현
        if (request.getNewFiles() != null && !request.getNewFiles().isEmpty()) {
            // 예시) 모든 기존 이미지를 제거 (orphanRemoval = true 이면 자동으로 DB에서 삭제)
            article.getArticleImages().clear();

            // 새 파일들을 업로드 후 리스트에 등록
            for (MultipartFile newFile : request.getNewFiles()) {
                String filePath = uploadFile(newFile);
                if (filePath != null) {
                    ArticleImage newImage = new ArticleImage(filePath);
                    article.addImage(newImage);
                }
            }
        }

        // 3) 저장
        return article;
    }


     */


    /**
     * 작성자와 현재 사용자가 일치하는지 확인
     */
    private static void authorizeArticleAuthor(Hotel hotel) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!hotel.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }


    ////  2025/02/28    검색 기능 때문에 추가

    public List<Hotel> searchHotels(String address){
        // 필요한 경우, 날짜 및 인원수 조건도 추가로 처리
        if(address == null || address.isEmpty()) {
            return blogRepository.findAll();
        } else {
            return blogRepository.findByAddressContaining(address);
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