package com.catholic.moyeo.board.service;

import com.catholic.moyeo.security.AuthUtil;
import org.springframework.stereotype.Component;

/**
 * 현재 로그인 사용자 ID 제공 구현체
 *
 * 정책:
 * - Board 모듈도 Recruit 모듈과 동일하게 AuthUtil을 사용해 현재 사용자 ID를 조회한다.
 * - 현재 프로젝트의 Security principal은 JWT 필터에서 Long 또는 숫자 문자열 형태로 저장된다.
 * - 따라서 Board에서 별도로 principal 타입을 추론하지 않고,
 *   프로젝트 공통 유틸(AuthUtil)의 해석 방식에 맞춰 일관되게 처리한다.
 *
 * 예외:
 * - 인증 정보가 없거나 principal 타입이 유효하지 않으면
 *   AuthUtil에서 IllegalStateException을 던진다.
 */
@Component
public class SecurityCurrentUserProvider implements CurrentUserProvider {

    @Override
    public Long getCurrentUserId() {
        return AuthUtil.currentMemberId();
    }
}