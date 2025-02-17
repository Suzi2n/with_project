package com.example.with_project.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("loginType", "regular"); // 일반 로그인 시 세션에 저장

        System.out.println(">>> loginType in session = " + session.getAttribute("loginType"));

        //response.sendRedirect("/articles"); // 로그인 성공 후 이동할 페이지        (수정 2025-02-11)
        response.sendRedirect("/home");
    }
}
