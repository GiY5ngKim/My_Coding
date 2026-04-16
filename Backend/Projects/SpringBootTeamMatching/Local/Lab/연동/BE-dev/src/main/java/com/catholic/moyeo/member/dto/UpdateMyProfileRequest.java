package com.catholic.moyeo.member.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateMyProfileRequest {

    @Size(max = 30, message = "nickname must be <= 30")
    private String nickname;

    private String profileImageUrl;

    @Size(max = 20, message = "role must be <= 20")
    private String role;

    @Size(max = 255, message = "contactEmail must be <= 255")
    private String contactEmail;

    @Size(max = 20, message = "phoneNumber must be <= 20")
    private String phoneNumber;

    @Size(max = 255, message = "githubUrl must be <= 255")
    private String githubUrl;

    @Size(max = 200, message = "intro must be <= 200")
    private String intro;

    private List<String> techStacks;

    private List<String> activityCategories;
}