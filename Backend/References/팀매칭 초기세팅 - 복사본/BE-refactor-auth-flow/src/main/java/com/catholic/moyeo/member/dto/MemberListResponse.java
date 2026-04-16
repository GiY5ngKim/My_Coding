package com.catholic.moyeo.member.dto;

public record MemberListResponse(
    Long memberId,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
