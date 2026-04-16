package com.catholic.moyeo.recruit.domain;

/**
 * 모집글 상태
 * - OPEN: 모집중
 * - CLOSED: 마감
 *
 * 정책:
 * - deadline이 지나면 자동으로 CLOSED 처리(스케줄러/배치)될 수 있으나,
 *   현재(2026/03/13)는 "행위 시점"에 서비스가 deadline+status를 함께 검증하는 것을 1차 기준으로 한다.
 * - CLOSED 상태에서는 apply/cancel/decide 모두 불가 (서비스에서 IllegalStateException -> 400)
 */
public enum RecruitPostStatus {
    OPEN,
    CLOSED
}