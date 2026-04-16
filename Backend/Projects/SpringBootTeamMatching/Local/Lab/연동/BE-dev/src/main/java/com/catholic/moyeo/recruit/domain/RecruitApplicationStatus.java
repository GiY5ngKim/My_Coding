package com.catholic.moyeo.recruit.domain;

/**
 * 지원 상태 정책(중요)
 * - APPLIED  : 지원 완료(기본)
 * - ACCEPTED : 승인됨 (applicant_count 증가 대상)
 * - REJECTED : 거절됨
 *
 * 취소는 status 전환이 아니라 "row 삭제"로 처리한다.
 */
public enum RecruitApplicationStatus {
    APPLIED,
    ACCEPTED,
    REJECTED
}