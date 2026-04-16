package com.cat.picshare.auth.handler; // auth 패키지 선언

import com.cat.picshare.common.response.ApiResponse; // 공통 응답을 사용하기 위한 임포트
import com.cat.picshare.common.response.ApiError; // 공통 에러를 사용하기 위한 임포트
import com.fasterxml.jackson.databind.ObjectMapper; // JSON 직렬화를 위한 임포트
import jakarta.servlet.ServletException; // 서블릿 예외 타입을 사용하기 위한 임포트
import jakarta.servlet.http.HttpServletRequest; // 요청 타입을 사용하기 위한 임포트
import jakarta.servlet.http.HttpServletResponse; // 응답 타입을 사용하기 위한 임포트
import org.springframework.http.MediaType; // Content-Type 설정을 위한 임포트
import org.springframework.security.core.AuthenticationException; // 인증 예외 타입을 사용하기 위한 임포트
import org.springframework.security.web.AuthenticationEntryPoint; // EntryPoint 인터페이스를 사용하기 위한 임포트

import java.io.IOException; // IO 예외를 처리하기 위한 임포트

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint { // 인증 실패 시 401을 내려주는 EntryPoint

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환기를 만든다.

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException { // 인증 실패 시 호출된다.
        ApiError error = new ApiError("C002", "인증이 필요합니다", null); // details=null 포함해서 에러 객체를 만든다(당신의 예시 포맷을 따른다).
        ApiResponse<Void> body = ApiResponse.fail(error); // 공통 응답 포맷으로 감싼다.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // JSON 응답임을 설정
        response.setCharacterEncoding("UTF-8"); // 한글 깨짐을 방지하기 위해 UTF-8을 설정
        response.getWriter().write(objectMapper.writeValueAsString(body)); // 바디를 JSON 문자열로 써서 내려준다.
    }
}