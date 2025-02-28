package com.example.with_project.config;

import com.example.with_project.Handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
public class WebSecurityConfig {     // 일반로그인 구현

    @Bean
    public SecurityFilterChain basicLoginSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/login", "/logout","/signup", "/forgot-password", "/static/**") // 경로 매칭
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 로그아웃, 회원가입 등은 인증 없이 허용
                        .requestMatchers("/login", "/logout","/logout", "/signup", "/forgot-password", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(new CustomLoginSuccessHandler())  // ✅ 로그인 성공 핸들러 적용
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")           // (기본값 "/logout", POST 요청)
                    //   .logoutSuccessUrl("/login")     // 로그아웃 성공 시 이동할 페이지      2025-02-11 수정
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true)    // 세션 무효화
                        .permitAll()                    // 로그아웃 URL은 인증없이 접근 가능
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }



    // 비밀번호를 암호화하여 저장
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




