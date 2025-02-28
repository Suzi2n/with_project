
package com.example.with_project.controller;

import lombok.RequiredArgsConstructor;
import com.example.with_project.dto.CreateAccessTokenRequest;
import com.example.with_project.dto.CreateAccessTokenResponse;
import com.example.with_project.service.RefreshTokenService;
import com.example.with_project.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token")

    public ResponseEntity<Void> deleteRefreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        refreshTokenService.delete(); // 토큰 삭제 로직 호출
        return ResponseEntity.ok().build();
    }
}

 /*    ///////// 2025-02-15 수정

package com.example.with_project.controller;

import com.example.with_project.config.jwt.TokenProvider;
import com.example.with_project.dto.CreateAccessTokenResponse;
import com.example.with_project.entity.RefreshToken;
import com.example.with_project.service.RefreshTokenService;
import com.example.with_project.service.UserService;
import com.example.with_project.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TokenApiController {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(30); // 30분 유효기간


     // ✅ Refresh Token을 이용해 새로운 Access Token 발급

    @PostMapping("/token")
    public ResponseEntity<?> createNewAccessToken(HttpServletRequest request) {
        // ✅ Refresh Token을 쿠키에서 가져옴
        Optional<String> refreshTokenOpt = CookieUtil.getCookie(request, REFRESH_TOKEN_COOKIE_NAME);

        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 없습니다.");
        }

        String refreshToken = refreshTokenOpt.get();
        Optional<RefreshToken> savedRefreshToken = refreshTokenService.findByToken(refreshToken);

        if (savedRefreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token입니다.");
        }

        // ✅ Refresh Token이 유효하다면 새로운 Access Token 발급
        Long userId = savedRefreshToken.get().getUserId();
        String newAccessToken = tokenProvider.generateToken(userService.findById(userId), ACCESS_TOKEN_DURATION);

        return ResponseEntity.ok().body(new CreateAccessTokenResponse(newAccessToken));
    }


     // ✅ Refresh Token 삭제 (로그아웃)

    @DeleteMapping("/refresh-token")
    public ResponseEntity<?> deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> refreshTokenOpt = CookieUtil.getCookie(request, REFRESH_TOKEN_COOKIE_NAME);

        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 없습니다.");
        }

        String refreshToken = refreshTokenOpt.get();

        try {
            // ✅ Refresh Token을 DB에서 삭제
            refreshTokenService.delete(refreshToken);

            // ✅ 쿠키에서 Refresh Token 제거
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);

            return ResponseEntity.ok().body("Refresh Token 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 삭제 중 오류 발생");
        }
    }
}
*/