package com.catholic.moyeo.security;

/**
 *구글 로그인 성공
 * → 이메일 가져옴
 * → 도메인 검사
 * 사용 위치:
 *   - OAuth2SuccessHandler에서 로그인 성공 직후 검사
 */
public class EmailDomainPolicy {
    public static final String ALLOWED_DOMAIN ="catholic.ac.kr";

    //catholic만 통과
    public static boolean isAllowed(String email){
        if (email ==null) return false;
        String lower = email.trim().toLowerCase();
        return lower.endsWith("@" + ALLOWED_DOMAIN);
    }
}
