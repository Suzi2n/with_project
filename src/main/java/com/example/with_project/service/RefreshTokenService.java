package com.example.with_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.with_project.config.jwt.TokenProvider;
import com.example.with_project.entity.RefreshToken;
import  com.example.with_project.repository.RefreshTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    @Transactional
    public void delete() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증되지 않은 상태입니다.");
        }

        //디버깅 로직을 추가

        logger.info("Authentication details: {}", authentication);


        // 인증 로직을 수행하기 위해 principal, credentials에 username, password 값을 넣는다.
        // 따라서, credential = '비밀번호'

        Object credentials = authentication.getCredentials();
        if (credentials == null) {
            throw new SecurityException("자격 증명이 누락되었습니다.");
        }

        String token = credentials.toString();
        logger.info("Token extracted: {}", token);

        // 토큰을 통해 사용자 ID 추출
        Long userId = tokenProvider.getUserId(token);

        if (userId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 사용자 ID로 리프레시 토큰 삭제
        refreshTokenRepository.deleteByUserId(userId);


        // 이후 로직
       // Object credentials = authentication.getCredentials();
        // 인증 정보를 사용한 토큰 처리

        /////// 위의 코드 추가



       // String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        //Long userId = tokenProvider.getUserId(token);


    }
}