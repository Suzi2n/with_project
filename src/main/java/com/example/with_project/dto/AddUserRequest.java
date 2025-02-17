package com.example.with_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {  // 자체 로그인 회원가입 API의 RequestBody
    private String email;
    private String password;
    private String nickname;
}