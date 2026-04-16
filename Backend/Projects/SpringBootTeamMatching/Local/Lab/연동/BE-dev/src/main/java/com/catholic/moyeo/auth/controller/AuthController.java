package com.catholic.moyeo.auth.controller;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class AuthController {

        @PostMapping("/api/auth/logout")
        public void logout(HttpServletResponse response) {

            Cookie cookie = new Cookie("access_token", null); //쿠키비움
            cookie.setHttpOnly(true);
            cookie.setPath("/"); //전체 경로에 적용
            cookie.setMaxAge(0); //쿠키 삭제

            response.addCookie(cookie);
        }
    }