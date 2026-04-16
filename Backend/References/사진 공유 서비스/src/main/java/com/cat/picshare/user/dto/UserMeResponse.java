package com.cat.picshare.user.dto; // 사용자 DTO 패키지

public class UserMeResponse { // 내 정보 응답 DTO
    private final Long id; // 사용자 ID
    private final String email; // 사용자 이메일

    public UserMeResponse(Long id, String email) { // 생성자
        this.id = id; // id 세팅
        this.email = email; // email 세팅
    }

    public Long getId() { return id; } // id getter
    public String getEmail() { return email; } // email getter
} // 클래스 종료
