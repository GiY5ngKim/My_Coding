package com.catholic.moyeo.recruit.service;

import com.catholic.moyeo.recruit.dto.ApplicationResponse;
import com.catholic.moyeo.recruit.dto.RecruitAuthorResponse;

/**
 * Recruit 모듈에서 Member 정보를 읽기 위한 추상화
 *
 * 정책:
 * - RecruitPost / RecruitApplication 은 memberId(userId)만 보관한다.
 * - 실제 응답에 필요한 nickname / email / departmentName 은 Member 도메인에서 조회한다.
 * - 작성자(author) 응답과 지원자(applicant) 응답을 하나의 Reader에서 통합 관리한다.
 *
 * 제공 기능:
 * - getAuthor(memberId):
 *   목록/상세 응답의 author { memberId, nickname, departmentName } 조회
 *
 * - getApplicant(memberId):
 *   지원자 목록/승인·거절 응답의
 *   applicant { memberId, nickname, contactEmail } 조회
 */
public interface RecruitMemberReader {

    /**
     * 작성자 정보를 조회한다.
     *
     * @param memberId app_user.user_id
     * @return author 응답 DTO
     */
    RecruitAuthorResponse getAuthor(Long memberId);

    /**
     * 지원자 정보를 조회한다.
     *
     * @param memberId app_user.user_id
     * @return applicant 응답 DTO
     */
    ApplicationResponse.Applicant getApplicant(Long memberId);
}