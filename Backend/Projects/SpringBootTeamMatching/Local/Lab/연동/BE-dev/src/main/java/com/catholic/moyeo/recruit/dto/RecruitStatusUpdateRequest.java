package com.catholic.moyeo.recruit.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 모집 상태 변경 요청 (작성자 전용)
 *
 * API 명세 body:
 * - status: OPEN | CLOSED
 *
 * 정책:
 * - DTO는 String으로 받는다.
 * - 허용값 검증 및 enum 파싱은 서비스에서 수행한다.
 * - 이유: Spring enum 바인딩 예외보다
 *   서비스에서 정책 메시지를 통일해서 400 처리하기 위함이다.
 */
public class RecruitStatusUpdateRequest {

    @NotBlank
    private String status;

    public RecruitStatusUpdateRequest() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}