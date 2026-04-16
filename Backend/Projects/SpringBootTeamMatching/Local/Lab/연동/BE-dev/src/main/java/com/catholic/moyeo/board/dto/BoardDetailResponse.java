package com.catholic.moyeo.board.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 응답
 *
 * mine:
 * - 프론트에서 수정/삭제 메뉴 노출 여부를 빠르게 판단할 수 있게 제공한다.
 */
public class BoardDetailResponse {

    private final Long postId;
    private final String title;
    private final String content;
    private final BoardAuthorResponse author;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean mine;

    private final long likeCount;
    private final long commentCount;
    private final boolean likedByMe;
    private final boolean bookmarkedByMe;

    private final List<String> images;

    public BoardDetailResponse(
            Long postId,
            String title,
            String content,
            BoardAuthorResponse author,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean mine,
            long likeCount,
            long commentCount,
            boolean likedByMe,
            boolean bookmarkedByMe,
            List<String> images
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mine = mine;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.likedByMe = likedByMe;
        this.bookmarkedByMe = bookmarkedByMe;
        this.images = images;
    }

    public static BoardDetailResponse from(
            Long postId,
            String title,
            String content,
            BoardAuthorResponse author,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean mine,
            long likeCount,
            long commentCount,
            boolean likedByMe,
            boolean bookmarkedByMe,
            List<String> images
    ) {
        return new BoardDetailResponse(
                postId,
                title,
                content,
                author,
                createdAt,
                updatedAt,
                mine,
                likeCount,
                commentCount,
                likedByMe,
                bookmarkedByMe,
                images
        );
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public BoardAuthorResponse getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isMine() { return mine; }

    public long getLikeCount() { return likeCount; }
    public long getCommentCount() { return commentCount; }
    public boolean isLikedByMe() { return likedByMe; }
    public boolean isBookmarkedByMe() { return bookmarkedByMe; }
    public List<String> getImages() { return images; }
}