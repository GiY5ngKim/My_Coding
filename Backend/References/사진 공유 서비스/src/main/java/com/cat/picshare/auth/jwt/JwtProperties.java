package com.cat.picshare.auth.jwt; // auth 패키지 선언이다.

import org.springframework.boot.context.properties.ConfigurationProperties; // yml 바인딩을 위한 임포트이다.

@ConfigurationProperties(prefix = "jwt") // jwt.* 설정을 이 클래스에 바인딩
public class JwtProperties { // JWT 설정을 담는 클래스이다.

    private String secret; // 서명 비밀키이다.
    private long accessTokenExpirationMs; // 토큰 만료시간(ms)이다.
    private String header; // 토큰을 담는 헤더 이름이다.
    private String tokenPrefix; // Bearer 접두사이다.

    public String getSecret() { // secret 값을 반환
        return secret; // secret을 반환
    }

    public void setSecret(String secret) { // secret 값을 주입받는다.
        this.secret = secret; // 필드에 저장
    }

    public long getAccessTokenExpirationMs() { // 만료시간을 반환
        return accessTokenExpirationMs; // 만료시간을 반환
    }

    public void setAccessTokenExpirationMs(long accessTokenExpirationMs) { // 만료시간을 주입받는다.
        this.accessTokenExpirationMs = accessTokenExpirationMs; // 필드에 저장
    }

    public String getHeader() { // 헤더명을 반환
        return header; // 헤더명을 반환
    }

    public void setHeader(String header) { // 헤더명을 주입받는다.
        this.header = header; // 필드에 저장
    }

    public String getTokenPrefix() { // 접두사를 반환
        return tokenPrefix; // 접두사를 반환
    }

    public void setTokenPrefix(String tokenPrefix) { // 접두사를 주입받는다.
        this.tokenPrefix = tokenPrefix; // 필드에 저장
    }
}