package com.catholic.moyeo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2 로그인 실패 시 처리 핸들러
 */
@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final OAuth2Props oauth2Props;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.err.println("❌ OAuth2 로그인 실패: " + exception.getMessage());
        
        // 프론트엔드로 리다이렉트하며 에러 파라미터를 붙여줍니다.
        // OAUTH2_REDIRECT_URL 로 지정된 프론트 경로 (예: http://localhost:5173/home)
        response.sendRedirect(oauth2Props.getRedirectUrl() + "?error=oauth2_failed");
    }
}
