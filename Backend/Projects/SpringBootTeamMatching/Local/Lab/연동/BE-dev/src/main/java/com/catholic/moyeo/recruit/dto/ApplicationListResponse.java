package com.catholic.moyeo.recruit.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 지원자 목록 응답 DTO
 *
 * API 명세:
 * {
 *   "applications": [...],
 *   "pageInfo": { ... }
 * }
 */
public record ApplicationListResponse(
        List<ApplicationResponse> applications,
        PageInfoResponse pageInfo
) {
    public static ApplicationListResponse from(Page<ApplicationResponse> page) {
        return new ApplicationListResponse(
                page.getContent(),
                PageInfoResponse.from(page)
        );
    }
}