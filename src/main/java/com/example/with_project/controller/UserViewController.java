package com.example.with_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {


    // 로그인 뷰
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }


    // 홈 뷰
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/article")
    public String article() {
        return "article"; // templates 폴더 내 article.html 파일을 렌더링
    }
}