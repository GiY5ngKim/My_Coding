package com.catholic.moyeo.member.dto;

public record MemberProfileResponse(
    Long memberId,
    String email,
    String contactEmail,
    Boolean emailVerified,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
