package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 게시글 북마크 엔티티
 *
 * 정책:
 * - 한 사용자는 같은 게시글을 한 번만 북마크할 수 있다.
 * - Board 모듈의 기존 like 구조와 맞추기 위해 연관관계 대신 ID 기반으로 관리한다.
 */
@Entity
@Table(
        name = "board_post_bookmark",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_board_post_bookmark",
                        columnNames = {"board_post_id", "user_id"} // 같은 사람이 글 하나를 여러 번 북마크하지 못하도록
                )
        }
)
public class BoardPostBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_post_bookmark_id")
    private Long id;

    // 북마크 대상 게시글 ID
    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    // 북마크한 사용자 ID
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 북마크 생성 시각
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected BoardPostBookmark() {}

    public BoardPostBookmark(Long boardPostId, Long userId) {
        this.boardPostId = boardPostId;
        this.userId = userId;
    }

    // insert 되기 직전에 생성 시간 저장
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getBoardPostId() { return boardPostId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}