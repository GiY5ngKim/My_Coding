package com.cat.picshare.auth.dto; // auth dto 패키지 선언

import jakarta.validation.constraints.Email; // 이메일 형식 검증을 위한 임포트
import jakarta.validation.constraints.NotBlank; // 빈 값 검증을 위한 임포트

public class AuthLoginRequest { // 로그인 요청 DTO

    @Email // 이메일 형식을 검증
    @NotBlank // 비어있지 않아야
    private String email; // 이메일 필드

    @NotBlank // 비어있지 않아야
    private String password; // 비밀번호 필드

    public String getEmail() { // email을 반환
        return email; // email을 반환
    }

    public void setEmail(String email) { // email을 설정
        this.email = email; // email을 저장
    }

    public String getPassword() { // password를 반환
        return password; // password를 반환
    }

    public void setPassword(String password) { // password를 설정
        this.password = password; // password를 저장
    }
}