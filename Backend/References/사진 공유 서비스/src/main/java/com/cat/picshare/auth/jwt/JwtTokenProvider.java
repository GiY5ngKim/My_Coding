package com.cat.picshare.auth.jwt; // auth 패키지 선언

import io.jsonwebtoken.Claims; // JWT 클레임 타입을 사용하기 위한 임포트
import io.jsonwebtoken.Jwts; // JWT 빌더/파서를 사용하기 위한 임포트
import io.jsonwebtoken.SignatureAlgorithm; // 서명 알고리즘을 사용하기 위한 임포트
import io.jsonwebtoken.security.Keys; // HMAC 키 생성을 위한 임포트
import org.springframework.stereotype.Component; // 스프링 컴포넌트 등록을 위한 임포트

import java.nio.charset.StandardCharsets; // 문자열을 바이트로 변환하기 위한 임포트
import java.security.Key; // 서명 키 타입을 사용하기 위한 임포트
import java.util.Date; // 만료시간 계산을 위한 임포트

@Component // 스프링 빈으로 등록
public class JwtTokenProvider { // JWT 생성/검증을 담당하는 클래스

    private final JwtProperties jwtProperties; // JWT 설정을 주입받는다.
    private final Key signingKey; // 서명에 사용할 Key 객체

    public JwtTokenProvider(JwtProperties jwtProperties) { // 생성자
        this.jwtProperties = jwtProperties; // 설정을 저장
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)); // HMAC 키를 생성
    }

    public String createAccessToken(String subject) { // Access Token을 생성
        Date now = new Date(); // 현재 시간을 만든다.
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenExpirationMs()); // 만료 시간을 계산
        return Jwts.builder() // 토큰 빌더를 시작
                .setSubject(subject) // subject(예: email 또는 userId)를 넣는다.
                .setIssuedAt(now) // 발급 시간을 넣는다.
                .setExpiration(expiry) // 만료 시간을 넣는다.
                .signWith(signingKey, SignatureAlgorithm.HS256) // HS256으로 서명
                .compact(); // 문자열 토큰으로 만든다.
    }

    public boolean validateToken(String token) { // 토큰 유효성을 검증
        try { // 예외 가능 구간을 감싼다.
            Jwts.parserBuilder() // 파서 빌더를 만든다.
                    .setSigningKey(signingKey) // 서명 키를 설정
                    .build() // 파서를 만든다.
                    .parseClaimsJws(token); // 파싱이 되면 유효한 토큰
            return true; // 검증 성공을 반환
        } catch (Exception e) { // 서명 불일치/만료/형식 오류 등 모든 예외를 처리
            return false; // 검증 실패를 반환
        }
    }

    public String getSubject(String token) { // 토큰에서 subject를 뽑는다.
        Claims claims = Jwts.parserBuilder() // 파서 빌더를 만든다.
                .setSigningKey(signingKey) // 서명 키를 설정
                .build() // 파서를 만든다.
                .parseClaimsJws(token) // 토큰을 파싱
                .getBody(); // 클레임 바디를 얻는다.
        return claims.getSubject(); // subject를 반환
    }
}