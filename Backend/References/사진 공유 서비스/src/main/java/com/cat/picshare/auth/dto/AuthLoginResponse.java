package com.cat.picshare.auth.dto; // auth dto 패키지 선언

public class AuthLoginResponse { // 로그인 응답 DTO

    private String accessToken; // 발급된 액세스 토큰

    public AuthLoginResponse(String accessToken) { // 생성자
        this.accessToken = accessToken; // 토큰을 저장
    }

    public String getAccessToken() { // 토큰을 반환
        return accessToken; // 토큰을 반환
    }
}