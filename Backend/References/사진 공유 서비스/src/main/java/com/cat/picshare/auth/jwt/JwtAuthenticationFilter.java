package com.cat.picshare.auth.jwt; // auth 패키지 선언

import jakarta.servlet.FilterChain; // 필터 체인 타입을 사용하기 위한 임포트
import jakarta.servlet.ServletException; // 서블릿 예외 타입을 사용하기 위한 임포트
import jakarta.servlet.http.HttpServletRequest; // HTTP 요청 타입을 사용하기 위한 임포트
import jakarta.servlet.http.HttpServletResponse; // HTTP 응답 타입을 사용하기 위한 임포트
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 인증 객체 타입을 사용하기 위한 임포트
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 단순 권한 객체를 사용하기 위한 임포트
import org.springframework.security.core.context.SecurityContextHolder; // SecurityContext를 제어하기 위한 임포트
import org.springframework.web.filter.OncePerRequestFilter; // 요청당 1회 실행되는 필터를 사용하기 위한 임포트

import java.io.IOException; // IO 예외를 처리하기 위한 임포트
import java.util.List; // 권한 리스트를 만들기 위한 임포트

public class JwtAuthenticationFilter extends OncePerRequestFilter { // 요청당 1회 실행되는 JWT 인증 필터

    private final JwtTokenProvider tokenProvider; // 토큰 생성/검증기를 가진다.
    private final JwtProperties jwtProperties; // 헤더명/프리픽스 설정을 가진다.

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, JwtProperties jwtProperties) { // 생성자
        this.tokenProvider = tokenProvider; // Provider를 저장
        this.jwtProperties = jwtProperties; // Properties를 저장
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { // 필터 본체
        String headerValue = request.getHeader(jwtProperties.getHeader()); // Authorization 헤더 값을 읽는다.

        try { // JWT 처리 중 예외가 터져도 500으로 죽지 않게 방어한다.
            if (headerValue != null && headerValue.startsWith(jwtProperties.getTokenPrefix())) { // Bearer 접두사가 있으면 토큰으로 본다.
                String token = headerValue.substring(jwtProperties.getTokenPrefix().length()).trim(); // 접두사를 제거하고 토큰만 뽑는다(공백 제거)

                if (!token.isEmpty() && tokenProvider.validateToken(token)) { // 토큰이 유효하면 인증 객체를 만든다.
                    String subject = tokenProvider.getSubject(token); // subject를 꺼낸다.
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER")); // 지금 단계는 권한을 최소로 고정
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject, null, authorities); // 인증 객체를 만든다.
                    SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 객체를 세팅
                }
            }
        } catch (Exception e) { // JWT 관련 예외는 여기서 먹고, 인증 없이 진행시켜서 401로 떨어지게 만든다.
            SecurityContextHolder.clearContext(); // 혹시라도 남아있을 수 있는 인증 정보를 정리한다.
        }

        filterChain.doFilter(request, response); // 다음 필터 또는 컨트롤러로 진행
    }
}
