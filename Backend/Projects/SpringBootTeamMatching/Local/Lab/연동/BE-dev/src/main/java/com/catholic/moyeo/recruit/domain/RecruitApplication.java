package com.catholic.moyeo.recruit.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * recruit_application 테이블 매핑
 *
 * 정책(중요):
 * - 취소는 row 삭제로 처리 (status=CANCELED 같은 상태 전환 안 씀)
 * - 중복지원은 DB UNIQUE(recruit_post_id, user_id) + 서비스 체크로 2중 방어
 * - ACCEPTED 취소(삭제) 시 applicant_count는 "서비스에서" -1 처리해야 함(자동 아님)
 * - CLOSED 상태의 모집글에서는 취소(삭제) 불가(서비스에서 IllegalStateException)
 *
 * NOTE(팀 공유):
 * - 관계(@ManyToOne)로 매핑하지 않고 FK 값(Long)만 보관한다.
 * - 따라서 post 조회/검증은 서비스에서 recruitPostId로 별도 조회해야 한다.
 */
@Entity
@Table(
        name = "recruit_application",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_recruit_application_post_user",
                        columnNames = {"recruit_post_id", "user_id"}
                )
        }
)
public class RecruitApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(name = "recruit_post_id", nullable = false)
    private Long recruitPostId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RecruitApplicationStatus status = RecruitApplicationStatus.APPLIED;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected RecruitApplication() {}

    public RecruitApplication(Long recruitPostId, Long userId) {
        this.recruitPostId = recruitPostId;
        this.userId = userId;
        this.status = RecruitApplicationStatus.APPLIED;
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) this.status = RecruitApplicationStatus.APPLIED;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getRecruitPostId() { return recruitPostId; }
    public Long getUserId() { return userId; }
    public RecruitApplicationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStatus(RecruitApplicationStatus status) { this.status = status; }
}