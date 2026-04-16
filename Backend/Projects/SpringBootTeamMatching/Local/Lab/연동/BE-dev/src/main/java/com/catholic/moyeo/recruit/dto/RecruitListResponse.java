package com.catholic.moyeo.recruit.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 모집글 목록 응답 DTO
 *
 * API 명세:
 * {
 *   "recruits": [...],
 *   "pageInfo": { ... }
 * }
 */
public record RecruitListResponse(
        List<RecruitSummaryResponse> recruits,
        PageInfoResponse pageInfo
) {
    public static RecruitListResponse from(Page<RecruitSummaryResponse> page) {
        return new RecruitListResponse(
                page.getContent(),
                PageInfoResponse.from(page)
        );
    }
}