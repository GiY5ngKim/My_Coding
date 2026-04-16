package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "board_post_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_board_post_like",
                        columnNames = {"board_post_id", "user_id"} //같은사람이 글 하나에 좋아요 여러번 못하도록

                )
        }
)
public class BoardPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_post_like_id")
    private Long id;

    @Column(name = "board_post_id", nullable = false)
    private Long boardPostId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected BoardPostLike() {}

    public BoardPostLike(Long boardPostId, Long userId) {
        this.boardPostId = boardPostId;
        this.userId = userId;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getBoardPostId() { return boardPostId; }
    public Long getUserId() { return userId; }
}