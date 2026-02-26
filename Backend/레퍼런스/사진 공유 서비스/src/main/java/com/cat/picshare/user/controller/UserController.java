package com.cat.picshare.user.controller; // 사용자 컨트롤러 패키지

import com.cat.picshare.common.response.ApiResponse; // 공통 응답
import com.cat.picshare.user.dto.UserMeResponse; // 응답 DTO
import com.cat.picshare.user.service.UserService; // 서비스
import org.springframework.security.core.Authentication; // 인증 객체
import org.springframework.web.bind.annotation.GetMapping; // GET 매핑
import org.springframework.web.bind.annotation.RequestMapping; // 경로 매핑
import org.springframework.web.bind.annotation.RestController; // REST 컨트롤러

@RestController // REST 컨트롤러
@RequestMapping("/api/users") // 사용자 API prefix
public class UserController { // 사용자 컨트롤러

    private final UserService userService; // 사용자 서비스

    public UserController(UserService userService) { // 생성자 주입
        this.userService = userService; // 주입 저장
    }

    @GetMapping("/me") // 내 정보 조회
    public ApiResponse<UserMeResponse> me(Authentication authentication) { // Authentication 주입 받기
        String email = authentication.getName(); // 인증 주체(subject=email) 가져오기
        return ApiResponse.ok(userService.getMeByEmail(email)); // 내 정보 반환
    }
} // 클래스 종료