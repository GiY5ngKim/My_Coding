package com.catholic.moyeo.recruit.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 지원 승인/거절 요청
 *
 * API 명세 body:
 * - status: ACCEPTED | REJECTED
 *
 * 정책:
 * - DTO는 String으로 받는다.
 * - 서비스에서 ACCEPTED / REJECTED 허용 여부를 최종 검증한다.
 * - APPLIED로 변경하는 API는 없다.
 */
public class ApplicationDecisionRequest {

    @NotBlank
    private String status;

    public ApplicationDecisionRequest() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}