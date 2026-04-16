package com.cat.picshare.auth.controller; // auth 패키지 선언

import com.cat.picshare.auth.jwt.JwtTokenProvider;
import com.cat.picshare.auth.dto.AuthLoginRequest; // 로그인 요청 DTO를 사용하기 위한 임포트
import com.cat.picshare.auth.dto.AuthLoginResponse; // 로그인 응답 DTO를 사용하기 위한 임포트
import com.cat.picshare.common.response.ApiResponse; // 공통 응답을 사용하기 위한 임포트
import jakarta.validation.Valid; // 유효성 검증 트리거를 위한 임포트
import org.springframework.web.bind.annotation.PostMapping; // POST 매핑을 위한 임포트
import org.springframework.web.bind.annotation.RequestBody; // 요청 바디 바인딩을 위한 임포트
import org.springframework.web.bind.annotation.RequestMapping; // 공통 경로 지정을 위한 임포트
import org.springframework.web.bind.annotation.RestController; // REST 컨트롤러 선언을 위한 임포트

@RestController // REST 컨트롤러임을 선언
@RequestMapping("/api/auth") // auth API의 공통 경로를 지정
public class AuthController { // 인증 관련 컨트롤러

    private final JwtTokenProvider tokenProvider; // 토큰 Provider를 주입받는다.

    public AuthController(JwtTokenProvider tokenProvider) { // 생성자
        this.tokenProvider = tokenProvider; // Provider를 저장
    }

    @PostMapping("/login") // 로그인 엔드포인트
    public ApiResponse<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest request) { // 로그인 요청을 처리
        String subject = request.getEmail(); // 지금 단계에서는 email을 subject로 사용
        String token = tokenProvider.createAccessToken(subject); // Access Token을 발급
        return ApiResponse.ok(new AuthLoginResponse(token)); // 공통 응답 포맷으로 토큰을 내려준다.
    }
}