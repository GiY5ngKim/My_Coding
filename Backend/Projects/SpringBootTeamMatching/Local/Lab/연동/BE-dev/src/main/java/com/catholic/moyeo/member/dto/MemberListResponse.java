package com.catholic.moyeo.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberListResponse {

    private List<MemberCardResponse> items;
    private PageInfo pageInfo;

    @Getter
    @Builder
    public static class PageInfo {
        private long totalElements;
        private int totalPages;
        private int page;
        private int size;
    }
}