package com.example.with_project.service;

import lombok.RequiredArgsConstructor;
import com.example.with_project.config.jwt.TokenProvider;
import com.example.with_project.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        UserEntity user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}