package com.catholic.moyeo.recruit.dto;

import com.catholic.moyeo.recruit.domain.RecruitApplicationStatus;

import java.time.LocalDateTime;

/**
 * 지원자 목록 아이템 / 지원 승인·거절 응답 DTO
 *
 * API 명세:
 * {
 *   applicationId,
 *   applicant {
 *     memberId,
 *     nickname,
 *     contactEmail
 *   },
 *   status,
 *   createdAt
 * }
 */
public class ApplicationResponse {

    private Long applicationId;
    private Applicant applicant;
    private RecruitApplicationStatus status;
    private LocalDateTime createdAt;

    public static ApplicationResponse of(
            Long applicationId,
            Long memberId,
            String nickname,
            String contactEmail,
            RecruitApplicationStatus status,
            LocalDateTime createdAt
    ) {
        ApplicationResponse r = new ApplicationResponse();
        r.applicationId = applicationId;
        r.applicant = new Applicant(memberId, nickname, contactEmail);
        r.status = status;
        r.createdAt = createdAt;
        return r;
    }

    public Long getApplicationId() { return applicationId; }
    public Applicant getApplicant() { return applicant; }
    public RecruitApplicationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static class Applicant {
        private Long memberId;
        private String nickname;
        private String contactEmail;

        public Applicant(Long memberId, String nickname, String contactEmail) {
            this.memberId = memberId;
            this.nickname = nickname;
            this.contactEmail = contactEmail;
        }

        public Long getMemberId() { return memberId; }
        public String getNickname() { return nickname; }
        public String getContactEmail() { return contactEmail; }
    }
}