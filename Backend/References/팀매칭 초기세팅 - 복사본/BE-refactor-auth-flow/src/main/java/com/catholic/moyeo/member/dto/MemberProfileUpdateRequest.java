package com.catholic.moyeo.member.dto;

public record MemberProfileUpdateRequest(
    String nickname,
    String profileImageUrl,
    String role,
    String contactEmail,
    String phone
) {}
