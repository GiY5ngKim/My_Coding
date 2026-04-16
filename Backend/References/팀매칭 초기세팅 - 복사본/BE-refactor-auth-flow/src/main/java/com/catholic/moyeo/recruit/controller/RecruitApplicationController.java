package com.catholic.moyeo.recruit.controller;

import com.catholic.moyeo.recruit.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recruits/{recruitId}")
@RequiredArgsConstructor
public class RecruitApplicationController {

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/apply")
    public ResponseEntity<Map<String, Object>> cancelApplication(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/applications")
    public ResponseEntity<Map<String, Object>> getApplications(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/applications/{applicationId}")
    public ResponseEntity<Map<String, Object>> processApplication(@PathVariable Long recruitId, @PathVariable Long applicationId, @RequestBody ApplicationStatusRequest request) {
        return ResponseEntity.ok(null);
    }
}
