package com.catholic.moyeo.recruit.dto;

import com.catholic.moyeo.recruit.domain.RecruitPost;
import com.catholic.moyeo.recruit.domain.RecruitPostStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 모집글 목록 아이템 응답 DTO
 *
 * API 기준:
 * recruits[] {
 *   recruitId,
 *   type,
 *   category,
 *   tag,
 *   department,
 *   title,
 *   status,
 *   skills,
 *   appliedByMe,
 *   applicantCount,
 *   totalHeadcount,
 *   deadline,
 *   createdAt,
 *   author {
 *     memberId,
 *     nickname,
 *     departmentName
 *   }
 * }
 *
 * 카테고리 2단계 정책:
 * - type     = 1차 필터(ActivityCategory)
 * - category = 2차 필터(RecruitCategory)
 *
 * 레거시/호환 정책:
 * - 기존 응답 스키마와 프론트 호환을 위해 type/category 필드는 유지한다.
 * - 추가로 activityCategory/recruitCategory alias getter를 제공한다.
 *
 * NOTE:
 * - applicantCount는 "작성자 포함 현재 참여 인원"이다.
 * - author 정보는 Member 조회 결과를 서비스에서 조합해서 내려준다.
 * - department는 모집글 작성 시 사용자가 선택적으로 입력한 표시용 문자열이다.
 * - department는 분류/검색/필터 용도로 사용하지 않는다.
 */
public class RecruitSummaryResponse {

    private Long recruitId;

    /**
     * 1차 카테고리
     * - ActivityCategory Enum name 저장값
     */
    private String type;

    /**
     * 2차 카테고리
     * - RecruitCategory Enum name 저장값
     */
    private String category;

    private String tag;
    private String department;
    private String title;
    private RecruitPostStatus status;
    private List<String> skills;
    private boolean appliedByMe;
    private long applicantCount;
    private Integer totalHeadcount;
    private LocalDate deadline;
    private LocalDateTime createdAt;
    private RecruitAuthorResponse author;

    public static RecruitSummaryResponse from(
            RecruitPost post,
            List<String> skills,
            boolean appliedByMe,
            RecruitAuthorResponse author
    ) {
        RecruitSummaryResponse response = new RecruitSummaryResponse();
        response.recruitId = post.getId();
        response.type = post.getType();
        response.category = post.getCategory();
        response.tag = post.getTag();
        response.department = post.getDepartment();
        response.title = post.getTitle();
        response.status = post.getStatus();
        response.skills = skills;
        response.appliedByMe = appliedByMe;
        response.applicantCount = post.getApplicantCount();
        response.totalHeadcount = (int) post.getTotalHeadcount();
        response.deadline = post.getDeadline();
        response.createdAt = post.getCreatedAt();
        response.author = author;
        return response;
    }

    public Long getRecruitId() { return recruitId; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getTag() { return tag; }
    public String getDepartment() { return department; }
    public String getTitle() { return title; }
    public RecruitPostStatus getStatus() { return status; }
    public List<String> getSkills() { return skills; }
    public boolean isAppliedByMe() { return appliedByMe; }
    public long getApplicantCount() { return applicantCount; }
    public Integer getTotalHeadcount() { return totalHeadcount; }
    public LocalDate getDeadline() { return deadline; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public RecruitAuthorResponse getAuthor() { return author; }

    /**
     * alias getter
     * - activityCategory == type
     */
    public String getActivityCategory() { return type; }

    /**
     * alias getter
     * - recruitCategory == category
     */
    public String getRecruitCategory() { return category; }
}