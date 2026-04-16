package com.catholic.moyeo.member.dto;

public record MemberDetailResponse(
    Long memberId,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
