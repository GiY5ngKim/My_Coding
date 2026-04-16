package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_comment")
public class BoardComment {

    // 닉네임은 댓글 데이터가 아니라 회원 데이터라 안 넣었음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected BoardComment() {}

    public BoardComment(Long boardPostId, Long userId, String content) {
        this.boardPostId = boardPostId;
        this.userId = userId;
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public Long getId() { return id; }
    public Long getBoardPostId() { return boardPostId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}