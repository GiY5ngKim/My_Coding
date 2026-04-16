package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 게시글 엔티티
 *
 * 정책:
 * - 작성자는 authorUserId만 저장한다.
 * - 게시판은 category 없이 title + content만 사용한다.
 * - createdAt / updatedAt은 엔티티 lifecycle에서 자동 설정한다.
 */
@Entity
@Table(name = "board_post")
public class BoardPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_post_id")
    private Long id;

    @Column(name = "author_user_id", nullable = false)
    private Long authorUserId;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected BoardPost() {}

    public BoardPost(Long authorUserId, String title, String content) {
        this.authorUserId = authorUserId;
        this.title = title;
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * null이 아닌 필드만 반영한다.
     */
    public void update(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public Long getId() { return id; }
    public Long getAuthorUserId() { return authorUserId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}