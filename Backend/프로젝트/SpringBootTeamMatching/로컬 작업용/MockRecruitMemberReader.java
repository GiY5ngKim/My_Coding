package com.catholic.moyeo.recruit.service;

import com.catholic.moyeo.recruit.dto.ApplicationResponse;
import com.catholic.moyeo.recruit.dto.RecruitAuthorResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * local 환경용 RecruitMemberReader mock 구현체
 *
 * 정책:
 * - Member 도메인이 완전히 연결되지 않은 local 테스트 환경에서 사용한다.
 * - author / applicant 응답이 null로 내려가지 않도록 임시 테스트 값을 제공한다.
 * - 로컬 테스트 시 응답 확인이 쉽도록 nickname 값을 일관되게 유지한다.
 */
@Component
@Primary
@Profile("local")
public class MockRecruitMemberReader implements RecruitMemberReader {

    @Override
    public RecruitAuthorResponse getAuthor(Long memberId) {
        return new RecruitAuthorResponse(
                memberId,
                "mockNickname",
                "컴퓨터공학과"
        );
    }

    @Override
    public ApplicationResponse.Applicant getApplicant(Long memberId) {
        return new ApplicationResponse.Applicant(
                memberId,
                "mockNickname",
                "mock@catholic.ac.kr"
        );
    }
}