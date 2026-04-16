package com.catholic.moyeo.member.controller;

import com.catholic.moyeo.member.dto.MemberDetailResponse;
import com.catholic.moyeo.member.dto.MemberListResponse;
import com.catholic.moyeo.member.dto.MyProfileResponse;
import com.catholic.moyeo.member.dto.UpdateMyProfileRequest;
import com.catholic.moyeo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // 내 프로필 조회
    @GetMapping("/me")
    public MyProfileResponse getMyProfile(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.getMyProfile(memberId);
    }

    // 내 프로필 수정
    @PatchMapping("/me")
    public MyProfileResponse updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateMyProfileRequest request
    ) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.updateMyProfile(memberId, request);
    }

    // 팀원 목록 조회
    @GetMapping
    public MemberListResponse getMembers(
            Authentication authentication,
            @RequestParam(required = false) String techStack,
            @RequestParam(required = false) String activityCategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return memberService.getMembers(userId, techStack, activityCategory, page, size);
    }

    // 팀원 상세 조회
    @GetMapping("/{memberId}")
    public MemberDetailResponse getMemberDetail(@PathVariable Long memberId) {
        return memberService.getMemberDetail(memberId);
    }

    //북마크 추가
    @PostMapping("/{memberId}/bookmark")
    public void addBookmark(
            Authentication authentication,
            @PathVariable Long memberId
    ) {
        Long userId = (Long) authentication.getPrincipal();
        memberService.addBookmark(userId, memberId);
    }
    //북마크 삭제
    @DeleteMapping("/{memberId}/bookmark")
    public void removeBookmark(
            Authentication authentication,
            @PathVariable Long memberId
    ) {
        Long userId = (Long) authentication.getPrincipal();
        memberService.removeBookmark(userId, memberId);
    }
    //내가 찜한 목록
    @GetMapping("/bookmarks")
    public MemberListResponse getMyBookmarks(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return memberService.getMyBookmarks(userId);
    }
}