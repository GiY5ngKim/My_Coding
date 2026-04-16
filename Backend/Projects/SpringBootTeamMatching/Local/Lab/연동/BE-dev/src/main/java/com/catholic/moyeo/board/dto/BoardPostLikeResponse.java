package com.catholic.moyeo.board.dto;

/**
 * 게시글 좋아요 응답
 *
 * 용도:
 * - 좋아요 추가/취소 후 현재 상태를 즉시 반환한다.
 *
 * 필드:
 * - liked: 현재 로그인 사용자의 좋아요 여부
 * - likeCount: 처리 후 게시글의 총 좋아요 수
 */
public class BoardPostLikeResponse {

    private final boolean liked;
    private final long likeCount;

    public BoardPostLikeResponse(boolean liked, long likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public static BoardPostLikeResponse of(boolean liked, long likeCount) {
        return new BoardPostLikeResponse(liked, likeCount);
    }

    public boolean isLiked() {
        return liked;
    }

    public long getLikeCount() {
        return likeCount;
    }
}