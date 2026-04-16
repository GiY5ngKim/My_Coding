package com.catholic.moyeo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 인증 필터
 *
 * 역할:
 * - 모든 HTTP 요청마다 실행
 * - 요청에 포함된 JWT(access_token)를 추출
 * - JWT 검증 후 인증된 사용자 정보를 SecurityContext에 등록
 *
 * 동작 흐름:
 * 1. Authorization 헤더 또는 쿠키에서 JWT 추출
 * 2. JwtProvider를 통해 토큰 검증 및 Claims 파싱
 * 3. Claims 기반으로 Authentication 객체 생성
 * 4. SecurityContextHolder에 인증 정보 저장
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT 생성 및 검증 담당 컴포넌트
    private final JwtProvider jwtProvider;

    /**
     * 매 요청마다 실행되는 필터 로직
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = null;

        /**
         * 1️ Authorization 헤더에서 JWT 추출
         *
         * 형식:
         * Authorization: Bearer {JWT}
         */
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            token = auth.substring(7); // "Bearer " 제거
        }

        /**
         * 2⃣ Authorization 헤더가 없을 경우 쿠키에서 JWT 추출
         *
         * 브라우저 환경에서는 HttpOnly 쿠키 사용
         */
        if (token == null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("access_token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        /**
         * 3️ JWT가 존재하는 경우에만 인증 시도
         */
        if (token != null) {
            try {
                Claims claims = jwtProvider.parseClaims(token);

                // JWT subject에 저장된 사용자 ID
                Long memberId = Long.valueOf(claims.getSubject());


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, List.of());

                // 요청 정보(IP, 세션 등) 추가
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }  catch (Exception e) {
            System.out.println("JWT 인증 실패: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            SecurityContextHolder.clearContext();
        }

    }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/")
                || path.startsWith("/oauth2/")
                || path.startsWith("/login/")
                || path.startsWith("/oauth/")
                || path.equals("/oauth/callback");
    }

}
