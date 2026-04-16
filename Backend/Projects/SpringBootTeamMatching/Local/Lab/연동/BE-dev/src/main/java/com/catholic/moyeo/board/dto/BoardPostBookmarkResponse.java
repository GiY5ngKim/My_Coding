package com.catholic.moyeo.board.dto;

/**
 * 게시글 북마크 응답
 *
 * 용도:
 * - 북마크 추가/취소 후 현재 상태를 즉시 반환한다.
 *
 * 필드:
 * - bookmarked: 현재 로그인 사용자의 북마크 여부
 */
public class BoardPostBookmarkResponse {

    private final boolean bookmarked;

    public BoardPostBookmarkResponse(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public static BoardPostBookmarkResponse of(boolean bookmarked) {
        return new BoardPostBookmarkResponse(bookmarked);
    }

    public boolean isBookmarked() {
        return bookmarked;
    }
}