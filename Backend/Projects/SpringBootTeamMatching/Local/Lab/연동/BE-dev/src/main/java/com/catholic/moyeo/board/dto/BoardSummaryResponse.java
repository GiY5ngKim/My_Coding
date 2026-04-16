package com.catholic.moyeo.board.dto;

import java.time.LocalDateTime;

/**
 * 게시글 목록 응답
 *
 * 목록 / 내 게시글 목록 / 북마크 목록에서 공통으로 사용한다.
 */
public class BoardSummaryResponse {

    private final Long postId;
    private final String title;
    private final BoardAuthorResponse author;
    private final LocalDateTime createdAt;

    private final long likeCount;
    private final long commentCount;
    private final boolean likedByMe;
    private final boolean bookmarkedByMe;

    public BoardSummaryResponse(
            Long postId,
            String title,
            BoardAuthorResponse author,
            LocalDateTime createdAt,
            long likeCount,
            long commentCount,
            boolean likedByMe,
            boolean bookmarkedByMe
    ) {
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.likedByMe = likedByMe;
        this.bookmarkedByMe = bookmarkedByMe;
    }

    public static BoardSummaryResponse from(
            Long postId,
            String title,
            BoardAuthorResponse author,
            LocalDateTime createdAt,
            long likeCount,
            long commentCount,
            boolean likedByMe,
            boolean bookmarkedByMe
    ) {
        return new BoardSummaryResponse(
                postId, title, author, createdAt,
                likeCount, commentCount, likedByMe, bookmarkedByMe
        );
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public BoardAuthorResponse getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public long getLikeCount() { return likeCount; }
    public long getCommentCount() { return commentCount; }
    public boolean isLikedByMe() { return likedByMe; }
    public boolean isBookmarkedByMe() { return bookmarkedByMe; }
}