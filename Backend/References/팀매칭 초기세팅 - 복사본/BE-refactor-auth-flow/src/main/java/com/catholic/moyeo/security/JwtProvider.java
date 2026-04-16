package com.catholic.moyeo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 생성 및 검증을 담당하는 클래스
 *
 * 역할:
 * 1. 로그인 성공 시 Access Token(JWT) 생성
 * 2. 요청에 포함된 JWT 검증 및 Claims 추출
 *
 * 특징:
 * - HS256 대칭키 방식 사용
 * - 서버는 세션을 저장하지 않음 (Stateless)
 */
@Getter
@Component
public class JwtProvider {

    // JWT 서명에 사용할 비밀키
    private final SecretKey key;

    // Access Token 만료 시간(ms 단위)
    private final long accessExpMs;

    /**
     * JWT 관련 설정 값 주입
     *
     * @param secret        JWT 서명용 secret key (application.yml)
     * @param accessExpMin  Access Token 만료 시간 (분 단위)
     */
    public JwtProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-exp-min}") long accessExpMin
    ) {
        //SecretKey 생성
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // 분 → 밀리초 변환
        this.accessExpMs = accessExpMin * 60 * 1000;
    }

    /**
     * Access Token 생성
     *
     * @param memberId 로그인 사용자 ID
     * @param email    로그인 사용자 이메일
     * @return JWT 문자열
     */
    public String createAccessToken(Long memberId, String email) {
        Date now = new Date();                          // 토큰 발급 시각
        Date exp = new Date(now.getTime() + accessExpMs); // 토큰 만료 시각

        return Jwts.builder()
                // JWT subject: 사용자 식별자 (memberId)
                .subject(String.valueOf(memberId))

                // 추가 정보 (필요 시 확장 가능)
                .claim("email", email)

                // 발급 시간 / 만료 시간 설정
                .issuedAt(now)
                .expiration(exp)

                // HS256 알고리즘으로 서명
                .signWith(key, SignatureAlgorithm.HS256)

                // JWT 문자열 생성
                .compact();
    }

    /**
     * 이 JWT가 진짜 우리 서버가 만든 토큰인지 확인하고, 안에 들어있는 정보만 꺼내기
     */
    public Claims parseClaims(String token) {
        return Jwts.parser()
                // 서명 검증에 사용할 키 설정
                .verifyWith(key)
                .build()

                // JWT 파싱 및 검증
                .parseSignedClaims(token)

                // Payload(Claims) 반환
                .getPayload();
    }



}
