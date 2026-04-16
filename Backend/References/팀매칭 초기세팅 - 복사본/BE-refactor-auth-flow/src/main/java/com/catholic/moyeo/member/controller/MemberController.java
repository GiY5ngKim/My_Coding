package com.catholic.moyeo.member.controller;

import com.catholic.moyeo.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    
    @GetMapping("/me")
    public ResponseEntity<MemberProfileResponse> getMyProfile() {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/me")
    public ResponseEntity<MemberProfileResponse> updateMyProfile(@RequestBody MemberProfileUpdateRequest request) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<MemberListResponse>>> getMembers() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailResponse> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/me/profile-image")
    public ResponseEntity<Map<String, String>> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(Map.of("profileImageUrl", "url"));
    }
}
