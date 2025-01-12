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


    /*
    // OAuth 로그인 뷰
    @GetMapping("/login")
    public String login(){
        return "oauthLogin";
    }


     */
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}