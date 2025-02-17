package com.example.with_project.service;

import com.example.with_project.dto.AddUserRequest;
import com.example.with_project.entity.Role;
import com.example.with_project.entity.UserEntity;

import com.example.with_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {               // 참고자료의 3. 회원가입 구현

                                                // - 3.1 서비스 메소드코드 작성 - UserService.java

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {              // AddUserRequest 이 내가 정의한 파일 'UserDTO'
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(UserEntity.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(Role.USER)
                .build()).getId();
    }

    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}