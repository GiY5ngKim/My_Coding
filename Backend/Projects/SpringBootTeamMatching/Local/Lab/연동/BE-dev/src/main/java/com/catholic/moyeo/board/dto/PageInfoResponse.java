package com.catholic.moyeo.board.dto;

import org.springframework.data.domain.Page;

/**
 * Page를 그대로 노출하지 않고 필요한 정보만 감싼다.
 */
public class PageInfoResponse {

    private final long totalElements;
    private final int totalPages;
    private final int page;
    private final int size;

    public PageInfoResponse(long totalElements, int totalPages, int page, int size) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;
    }

    public static PageInfoResponse from(Page<?> page) {
        return new PageInfoResponse(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public int getPage() { return page; }
    public int getSize() { return size; }
}