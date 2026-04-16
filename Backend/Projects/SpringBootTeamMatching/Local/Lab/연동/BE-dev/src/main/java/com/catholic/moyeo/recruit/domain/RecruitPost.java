package com.catholic.moyeo.recruit.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * recruit_post 테이블 매핑
 *
 * 핵심 정책(팀 공유):
 * - required_skills: DB는 VARCHAR(255) CSV("A,B,C"), API는 List<String> (서버 join/split)
 * - applicant_count: 작성자 포함 현재 참여 인원. applicant_count 기본 1에서 시작.
 * - deadline 지난 경우: 서비스에서 행위 시점에 차단(400). (스케줄러로 CLOSED 처리하는 건 선택)
 *
 * 값 고정 정책(중요):
 * - activityCategory / recruitCategory는 "허용 값 고정" 대상이지만, DB는 VARCHAR 유지
 * - 따라서 엔티티는 String을 유지하고, 서비스에서 허용 값 검증/정규화 후 저장한다.
 *
 * 상태 정책(중요):
 * - status는 OPEN/CLOSED 고정값이다.
 * - DB는 Enum STRING으로 저장한다.
 * - 서비스에서 String -> Enum 파싱/검증 후 setStatus 한다.
 *
 * tag 정책(중요):
 * - tag는 표시만 한다. (필터/검색 X)
 *   (추후 다중 태그가 필요하면 ERD 변경 + 테이블 분리 등 재설계 필요)
 *
 * department 정책(중요):
 * - department는 표시만 한다. (분류/검색/필터 X)
 * - 사용자가 원할 때만 입력하는 선택값이다.
 *
 * 도메인 방어 정책(추가):
 * - total_headcount는 1 이상이어야 한다.
 * - total_headcount는 applicant_count보다 작아질 수 없다.
 * - applicant_count 증가 시 정원 초과는 허용하지 않는다.
 * - applicant_count 감소 시 작성자 포함 정책상 최소 1을 유지한다.
 *
 * 레거시 호환 정책(중요):
 * - 현재 DB 컬럼명은 기존 호환을 위해 유지한다.
 *   type 컬럼     -> activityCategory 저장
 *   category 컬럼 -> recruitCategory 저장
 *
 * - 또한 기존 DTO/응답 조립 코드 호환을 위해
 *   getType()/setType(), getCategory()/setCategory() alias를 함께 제공한다.
 */
@Entity
@Table(name = "recruit_post")
public class RecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_post_id")
    private Long id;

    @Column(name = "author_user_id", nullable = false)
    private Long authorUserId;

    /**
     * 1차 카테고리
     * - ActivityCategory Enum name 저장
     *
     * 주의:
     * - DB 컬럼명은 기존 호환을 위해 type 유지
     */
    @Column(name = "type", nullable = false, length = 50)
    private String activityCategory;

    /**
     * 2차 카테고리
     * - RecruitCategory Enum name 저장
     *
     * 주의:
     * - DB 컬럼명은 기존 호환을 위해 category 유지
     */
    @Column(name = "category", nullable = false, length = 50)
    private String recruitCategory;

    /**
     * 표시용 단일 태그 (예: "AfterEffects")
     * - 검색/필터 파라미터에서는 사용하지 않음
     */
    @Column(name = "tag", length = 50)
    private String tag;

    /**
     * 표시용 학과 문자열
     * - 분류/검색/필터 파라미터에서는 사용하지 않음
     * - 사용자가 원할 때만 입력하는 선택값
     */
    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 모집 기술(요구 기술) - CSV
     * 예: "Python,TensorFlow"
     */
    @Column(name = "required_skills", length = 255)
    private String requiredSkills;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RecruitPostStatus status = RecruitPostStatus.OPEN;

    @Column(name = "total_headcount", nullable = false)
    private short totalHeadcount;

    @Column(name = "applicant_count", nullable = false)
    private short applicantCount;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected RecruitPost() {}

    public RecruitPost(Long authorUserId,
                       String activityCategory,
                       String recruitCategory,
                       String tag,
                       String department,
                       String title,
                       String content,
                       String requiredSkills,
                       short totalHeadcount,
                       LocalDate deadline) {
        this.authorUserId = authorUserId;
        this.activityCategory = activityCategory;
        this.recruitCategory = recruitCategory;
        this.tag = tag;
        this.department = department;
        this.title = title;
        this.content = content;
        this.requiredSkills = requiredSkills;
        this.totalHeadcount = totalHeadcount;
        this.deadline = deadline;

        this.status = RecruitPostStatus.OPEN;
        this.applicantCount = 1; // 정책: 작성자 포함 참여 인원
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        // 방어 로직 (최소한의 무결성 보장)
        if (this.status == null) this.status = RecruitPostStatus.OPEN;
        if (this.totalHeadcount <= 0) this.totalHeadcount = 1;

        // 작성자 포함 정책: 최소 1
        if (this.applicantCount <= 0) this.applicantCount = 1;

        // applicant_count가 total_headcount를 초과하지 않도록 방어
        if (this.applicantCount > this.totalHeadcount) this.applicantCount = this.totalHeadcount;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getAuthorUserId() { return authorUserId; }

    /**
     * 1차 카테고리 getter
     */
    public String getActivityCategory() { return activityCategory; }

    /**
     * 2차 카테고리 getter
     */
    public String getRecruitCategory() { return recruitCategory; }

    /**
     * 레거시 호환 getter
     * - 기존 코드/DTO에서 getType()을 호출하면 1차 카테고리를 반환한다.
     */
    public String getType() { return activityCategory; }

    /**
     * 레거시 호환 getter
     * - 기존 코드/DTO에서 getCategory()를 호출하면 2차 카테고리를 반환한다.
     */
    public String getCategory() { return recruitCategory; }

    public String getTag() { return tag; }
    public String getDepartment() { return department; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getRequiredSkills() { return requiredSkills; }
    public RecruitPostStatus getStatus() { return status; }
    public short getTotalHeadcount() { return totalHeadcount; }
    public short getApplicantCount() { return applicantCount; }
    public LocalDate getDeadline() { return deadline; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * 1차 카테고리 setter
     */
    public void setActivityCategory(String activityCategory) { this.activityCategory = activityCategory; }

    /**
     * 2차 카테고리 setter
     */
    public void setRecruitCategory(String recruitCategory) { this.recruitCategory = recruitCategory; }

    /**
     * 레거시 호환 setter
     * - 기존 코드에서 setType(...)을 호출하면 1차 카테고리를 갱신한다.
     */
    public void setType(String type) { this.activityCategory = type; }

    /**
     * 레거시 호환 setter
     * - 기존 코드에서 setCategory(...)을 호출하면 2차 카테고리를 갱신한다.
     */
    public void setCategory(String category) { this.recruitCategory = category; }

    public void setTag(String tag) { this.tag = tag; }
    public void setDepartment(String department) { this.department = department; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public void setStatus(RecruitPostStatus status) { this.status = status; }

    /**
     * total_headcount 변경
     *
     * 정책:
     * - 1 이상이어야 한다.
     * - applicant_count보다 작아질 수 없다.
     *
     * NOTE:
     * - 서비스에서도 동일 검증을 수행하지만,
     *   엔티티에서도 한 번 더 방어해 도메인 정합성을 유지한다.
     */
    public void setTotalHeadcount(short totalHeadcount) {
        if (totalHeadcount <= 0) {
            throw new IllegalArgumentException("totalHeadcount must be positive");
        }
        if (totalHeadcount < this.applicantCount) {
            throw new IllegalArgumentException("totalHeadcount cannot be less than applicantCount");
        }
        this.totalHeadcount = totalHeadcount;
    }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    /**
     * 참여 인원 증가
     *
     * 정책:
     * - ACCEPTED 전환 시에만 서비스에서 호출한다.
     * - 정원 초과는 허용하지 않는다.
     *
     * NOTE:
     * - 서비스에서 정원 체크를 먼저 수행하지만,
     *   도메인에서도 한 번 더 방어하여 실수성 호출을 막는다.
     */
    public void increaseApplicantCount() {
        if (this.applicantCount >= this.totalHeadcount) {
            throw new IllegalStateException("Recruit is full");
        }
        this.applicantCount++;
    }

    /**
     * 참여 인원 감소
     *
     * 정책:
     * - ACCEPTED 상태 지원 row 삭제 또는 ACCEPTED -> REJECTED 전환 시 사용된다.
     * - 작성자 포함 정책상 최소 1 밑으로 내려갈 수 없다.
     */
    public void decreaseApplicantCount() {
        if (this.applicantCount > 1) this.applicantCount--;
    }

    public boolean isClosed() { return this.status == RecruitPostStatus.CLOSED; }
}