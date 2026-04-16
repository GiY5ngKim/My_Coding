package com.catholic.moyeo.recruit.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 내가 지원한 모집글 목록 응답 DTO
 *
 * API 명세:
 * {
 *   "recruits": [...],
 *   "pageInfo": { ... }
 * }
 */
public record MyAppliedRecruitListResponse(
        List<MyAppliedRecruitResponse> recruits,
        PageInfoResponse pageInfo
) {
    public static MyAppliedRecruitListResponse from(Page<MyAppliedRecruitResponse> page) {
        return new MyAppliedRecruitListResponse(
                page.getContent(),
                PageInfoResponse.from(page)
        );
    }
}