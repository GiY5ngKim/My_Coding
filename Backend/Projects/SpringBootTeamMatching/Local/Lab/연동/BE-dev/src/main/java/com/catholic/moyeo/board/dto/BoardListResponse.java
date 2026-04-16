package com.catholic.moyeo.board.dto;

import java.util.List;

/**
 * 게시글 목록 응답
 *
 * /api/boards/posts
 * /api/boards/posts/me
 * /api/boards/posts/bookmarks
 * 에서 공통 사용한다.
 */
public class BoardListResponse {

    private final List<BoardSummaryResponse> posts;
    private final PageInfoResponse pageInfo;

    public BoardListResponse(List<BoardSummaryResponse> posts, PageInfoResponse pageInfo) {
        this.posts = posts;
        this.pageInfo = pageInfo;
    }

    public static BoardListResponse of(List<BoardSummaryResponse> posts, PageInfoResponse pageInfo) {
        return new BoardListResponse(posts, pageInfo);
    }

    public List<BoardSummaryResponse> getPosts() { return posts; }
    public PageInfoResponse getPageInfo() { return pageInfo; }
}