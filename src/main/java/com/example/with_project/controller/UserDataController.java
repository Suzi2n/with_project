package com.example.with_project.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserDataController {

    @GetMapping("/articleList")
    public String getUserProfile(OAuth2AuthenticationToken authentication, Model model) {
        if (authentication != null) {
            Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
            String name = (String) attributes.get("name");  // "이수진"
            model.addAttribute("userName", name);
        }
        return "hotelList";  // hotelList.html 템플릿 반환
    }

}
