// 회원가입 후 계정생성

package com.catholic.moyeo.member.dto;

import com.catholic.moyeo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyProfileResponse {

    private Long memberId;
    private String email; // 로그인용 이메일
    private String contactEmail; // 연락용 이메일
    private boolean emailVerified;
    private String nickname;
    private String role;
    private String intro;
    private String githubUrl;
    private String profileImageUrl;


    private List<String> techStacks;
    private List<String> activityCategories;
    private String phoneNumber;

    public static MyProfileResponse from(Member member,
                                         List<String> techStacks,
                                         List<String> activityCategories) {
        return MyProfileResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .contactEmail(member.getContactEmail())
                .emailVerified(member.isEmailVerified())
                .nickname(member.getNickname())
                .role(member.getRole())
                .intro(member.getIntro())
                .githubUrl(member.getGithubUrl())
                .profileImageUrl(member.getProfileImageUrl())
                .techStacks(techStacks)
                .activityCategories(activityCategories)
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

}