package com.catholic.moyeo.recruit.dto;

import org.springframework.data.domain.Page;

/**
 * 목록 응답의 pageInfo DTO
 */
public record PageInfoResponse(
        long totalElements,
        int totalPages,
        int page,
        int size
) {
    public static PageInfoResponse from(Page<?> page) {
        return new PageInfoResponse(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}