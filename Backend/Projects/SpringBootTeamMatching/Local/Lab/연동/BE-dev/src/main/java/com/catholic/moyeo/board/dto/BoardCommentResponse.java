package com.catholic.moyeo.board.dto;

import java.time.LocalDateTime;

/**
 * 댓글 응답
 *
 * 용도:
 * - 댓글 조회/작성/수정 후 현재 댓글 정보를 반환한다.
 *
 * 필드:
 * - commentId: 댓글 ID
 * - content: 댓글 내용
 * - author: 작성자 정보
 * - mine: 현재 로그인 사용자가 작성한 댓글인지 여부
 * - createdAt: 댓글 생성 시각
 */
public class BoardCommentResponse {

    private final Long commentId;
    private final String content;
    private final BoardAuthorResponse author;
    private final boolean mine;
    private final LocalDateTime createdAt;

    public BoardCommentResponse(
            Long commentId,
            String content,
            BoardAuthorResponse author,
            boolean mine,
            LocalDateTime createdAt
    ) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.mine = mine;
        this.createdAt = createdAt;
    }

    public static BoardCommentResponse from(
            Long commentId,
            String content,
            BoardAuthorResponse author,
            boolean mine,
            LocalDateTime createdAt
    ) {
        return new BoardCommentResponse(commentId, content, author, mine, createdAt);
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public BoardAuthorResponse getAuthor() {
        return author;
    }

    public boolean isMine() {
        return mine;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}