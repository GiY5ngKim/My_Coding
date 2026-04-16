package com.catholic.moyeo.member.dto;

import com.catholic.moyeo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberCardResponse {

    private Long memberId;
    private String nickname;
    private String role;
    private String intro;
    private String githubUrl;
    private String profileImageUrl;
    private List<String> techStacks;
    private List<String> activityCategories;
    private boolean bookmarked;

    public static MemberCardResponse from(Member member,
                                          List<String> techStacks,
                                          List<String> activityCategories,
                                          boolean bookmarked) {
        return MemberCardResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .role(member.getRole())
                .intro(member.getIntro())
                .githubUrl(member.getGithubUrl())
                .profileImageUrl(member.getProfileImageUrl())
                .techStacks(techStacks)
                .activityCategories(activityCategories)
                .bookmarked(bookmarked)
                .build();
    }
}