package com.catholic.moyeo.recruit.controller;

import com.catholic.moyeo.recruit.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
public class RecruitController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecruits() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{recruitId}")
    public ResponseEntity<Map<String, Object>> getRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRecruit(@RequestBody RecruitRequest request) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{recruitId}")
    public ResponseEntity<Map<String, Object>> updateRecruit(@PathVariable Long recruitId, @RequestBody RecruitRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Void> deleteRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recruitId}/status")
    public ResponseEntity<Map<String, Object>> updateRecruitStatus(@PathVariable Long recruitId, @RequestBody RecruitStatusRequest request) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMyRecruits() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/applied")
    public ResponseEntity<Map<String, Object>> getAppliedRecruits() {
        return ResponseEntity.ok(null);
    }
}
