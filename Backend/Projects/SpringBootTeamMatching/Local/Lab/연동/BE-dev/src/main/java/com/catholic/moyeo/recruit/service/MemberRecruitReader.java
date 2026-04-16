package com.catholic.moyeo.recruit.service;

import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.repository.MemberRepository;
import com.catholic.moyeo.recruit.dto.ApplicationResponse;
import com.catholic.moyeo.recruit.dto.RecruitAuthorResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member 기반 RecruitMemberReader 구현체
 *
 * 역할:
 * - Recruit 모듈에서 memberId(userId)를 기반으로 Member 정보를 조회한다.
 * - 조회된 Member 정보를 Recruit API 응답 DTO로 변환한다.
 *
 * 설계 배경:
 * - Recruit 모듈은 Member 엔티티를 직접 참조하지 않는다.
 * - 대신 recruit_post, recruit_application에는 userId(memberId)만 저장된다.
 * - 응답 생성 시점에 MemberRepository를 통해 실제 회원 정보를 조회하여
 *   author / applicant DTO를 조합한다.
 *
 * 사용 위치:
 * - RecruitService
 *   - 모집글 작성자(author) 정보 조회
 *   - 지원자(applicant) 정보 조회
 *
 * Profile 정책:
 * - local 환경을 제외한 모든 환경(dev, prod 등)에서 사용된다.
 */
@Component
@Transactional(readOnly = true)
public class MemberRecruitReader implements RecruitMemberReader {

    private final MemberRepository memberRepository;

    public MemberRecruitReader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 모집글 작성자 정보 조회
     *
     * 반환 필드:
     * - memberId
     * - nickname
     * - departmentName
     */
    @Override
    public RecruitAuthorResponse getAuthor(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        return new RecruitAuthorResponse(
                member.getId(),
                member.getNickname()
        );
    }

    /**
     * 지원자 정보 조회
     *
     * 반환 필드:
     * - memberId
     * - nickname
     * - contactEmail
     */
    @Override
    public ApplicationResponse.Applicant getApplicant(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        return new ApplicationResponse.Applicant(
                member.getId(),
                member.getNickname(),
                member.getEmail()
        );
    }
}