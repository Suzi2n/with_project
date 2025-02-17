package com.example.with_project.config;

import com.example.with_project.config.jwt.TokenProvider;
import com.example.with_project.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.with_project.config.oauth.OAuth2SuccessHandler;
//import com.example.with_project.config.oauth.OAuth2UserCustomService;
import com.example.with_project.config.oauth.userinfo.CustomOAuth2UserService;
import com.example.with_project.repository.RefreshTokenRepository;
import com.example.with_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

   // private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() { // 스프링 시큐리티 기능 비활성화
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(
                        new AntPathRequestMatcher("/img/**"),
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/js/**")
                );
    }


    @Bean
    public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {      // 기존의 SecurityFilterChain 에서 oauth만 지정하도록 ㄴ수정
        return http
                .securityMatcher("/oauth2/**", "/login/oauth2/**" )   /// "/home"  삭제
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logout").permitAll() // ✅ 로그아웃 요청 허용   /// 2025-02-15 추가
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorizationEndpoint ->
                                authorizationEndpoint.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                       // .userInfoEndpoint(userInfoEndpoint ->
                                 // userInfoEndpoint.userService(oAuth2UserCustomService))
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(customOAuth2UserService))

                        .successHandler(oAuth2SuccessHandler()))   // 소셜 로그인 성공 시
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    /// 추가??? 20025-02-11
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/refresh-token").permitAll() // ✅ refresh-token 요청은 인증 없이 허용   ///  2025-02-15 추가
                        .anyRequest().authenticated()
                )
                .build();
    }





/*
    // 통합 SecurityFilterChain: OAuth2 로그인과 JWT 인증을 모두 포함      /// 202-02-12
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (필요에 따라 다시 활성화 가능)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 정책: OAuth2 로그인은 IF_REQUIRED, API는 JWT를 사용하므로 상황에 맞게 처리
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 가능한 URL 설정
                        .requestMatchers("/login", "/logout", "/signup", "/forgot-password", "/static/**").permitAll()
                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // OAuth2 로그인 설정 (쿠키 기반 인증 요청 저장소 사용)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // 커스텀 로그인 페이지 URL
                        .authorizationEndpoint(authorizationEndpoint ->
                                authorizationEndpoint
                                        .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                        )
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler())
                )

                // JWT 인증 필터 추가 (UsernamePasswordAuthenticationFilter 이전에 실행)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }


 */


    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }


    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }


}