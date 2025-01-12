package com.example.with_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain basicLoginSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/login", "/signup", "/forgot-password", "/static/**") // 경로 매칭
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/forgot-password", "/static/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
