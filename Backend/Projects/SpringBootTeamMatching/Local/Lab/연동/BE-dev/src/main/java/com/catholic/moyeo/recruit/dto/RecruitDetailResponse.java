package com.catholic.moyeo.recruit.dto;

import com.catholic.moyeo.recruit.domain.RecruitPost;
import com.catholic.moyeo.recruit.domain.RecruitPostStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 모집글 상세 응답 DTO
 *
 * API 기준:
 * {
 *   "recruit": {
 *     "recruitId": ...,
 *     "type": ...,
 *     "category": ...,
 *     "tag": ...,
 *     "department": ...,
 *     "title": ...,
 *     "content": ...,
 *     "skills": [...],
 *     "status": ...,
 *     "totalHeadcount": ...,
 *     "deadline": ...,
 *     "author": {
 *       "memberId": ...,
 *       "nickname": ...,
 *       "departmentName": ...
 *     },
 *     "createdAt": ...,
 *     "updatedAt": ...
 *   },
 *   "appliedByMe": ...,
 *   "applicantCount": ...
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
 * - author 정보는 recruit_post 컬럼이 아니라 별도 Member 조회 결과를 서비스에서 주입한다.
 * - profileImageUrl은 현재 범위에서 제외한다.
 * - department는 모집글 작성 시 사용자가 선택적으로 입력한 표시용 문자열이다.
 * - department는 분류/검색/필터 용도로 사용하지 않는다.
 */
public class RecruitDetailResponse {

    private Recruit recruit;
    private boolean appliedByMe;
    private long applicantCount;

    public static RecruitDetailResponse from(
            RecruitPost post,
            RecruitAuthorResponse author,
            List<String> skills,
            boolean appliedByMe
    ) {
        RecruitDetailResponse response = new RecruitDetailResponse();

        Recruit recruit = new Recruit();
        recruit.recruitId = post.getId();
        recruit.type = post.getType();
        recruit.category = post.getCategory();
        recruit.tag = post.getTag();
        recruit.department = post.getDepartment();
        recruit.title = post.getTitle();
        recruit.content = post.getContent();
        recruit.skills = skills;
        recruit.status = post.getStatus();
        recruit.totalHeadcount = (int) post.getTotalHeadcount();
        recruit.deadline = post.getDeadline();
        recruit.author = author;
        recruit.createdAt = post.getCreatedAt();
        recruit.updatedAt = post.getUpdatedAt();

        response.recruit = recruit;
        response.appliedByMe = appliedByMe;
        response.applicantCount = post.getApplicantCount();

        return response;
    }

    public Recruit getRecruit() { return recruit; }
    public boolean isAppliedByMe() { return appliedByMe; }
    public long getApplicantCount() { return applicantCount; }

    /**
     * 상세 응답의 recruit 본문
     */
    public static class Recruit {
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
        private String content;
        private List<String> skills;
        private RecruitPostStatus status;
        private Integer totalHeadcount;
        private LocalDate deadline;
        private RecruitAuthorResponse author;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Long getRecruitId() { return recruitId; }
        public String getType() { return type; }
        public String getCategory() { return category; }
        public String getTag() { return tag; }
        public String getDepartment() { return department; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public List<String> getSkills() { return skills; }
        public RecruitPostStatus getStatus() { return status; }
        public Integer getTotalHeadcount() { return totalHeadcount; }
        public LocalDate getDeadline() { return deadline; }
        public RecruitAuthorResponse getAuthor() { return author; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }

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
}