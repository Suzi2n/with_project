package com.example.with_project.controller;

import com.example.with_project.dto.AddUserRequest;
import com.example.with_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequiredArgsConstructor
public class RegisterController {     // 참고자료에서의 3-2. 컨트롤러 작성
                                        // UserApiController.java

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        try {
            userService.save(request); // 회원 가입 메소드 호출
            return "redirect:/login"; // 회원가입 완료 후 로그인 페이지로 이동
        } catch (DataIntegrityViolationException e) {
            // 중복 이메일 에러 처리
            return "redirect:/signup?error=duplicate-email"; // 중복 이메일 처리
        } catch (Exception e) {
            // 기타 예외 처리
            return "redirect:/signup?error=unknown"; // 기타 에러 처리
        }
    }



}