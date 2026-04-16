package com.catholic.moyeo.recruit.dto;

/**
 * apply / cancel 공통 응답
 *
 * API 기준:
 * - appliedByMe: 현재 로그인 사용자가 이 모집글에 "지원 row"를 가지고 있는지
 * - applicantCount: recruit_post.applicant_count (작성자 포함 현재 참여 인원)
 */
public class ApplyStatusResponse {

    private boolean appliedByMe;
    private long applicantCount;

    public static ApplyStatusResponse of(boolean appliedByMe, long applicantCount) {
        ApplyStatusResponse r = new ApplyStatusResponse();
        r.appliedByMe = appliedByMe;
        r.applicantCount = applicantCount;
        return r;
    }

    public boolean isAppliedByMe() { return appliedByMe; }
    public long getApplicantCount() { return applicantCount; }
}