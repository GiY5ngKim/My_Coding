package com.catholic.moyeo.common.controller;

import com.catholic.moyeo.common.service.FileUploadService;
import com.catholic.moyeo.member.service.MemberService;
import com.catholic.moyeo.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final MemberService memberService;

    @PostMapping("/profile-image")
    public ResponseEntity<Map<String, String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file
    ) {
        String url = fileUploadService.uploadOriginal(file, "profile");

        Long me = AuthUtil.currentMemberId();
        memberService.updateProfileImage(me, url);

        return ResponseEntity.ok(Map.of("url", url));
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        String url = fileUploadService.uploadOriginal(file, "board");
        return ResponseEntity.ok(Map.of("url", url));
    }
}