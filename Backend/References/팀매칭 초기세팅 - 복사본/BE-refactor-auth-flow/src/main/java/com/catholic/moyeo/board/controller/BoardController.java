package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getPosts() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/posts")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/me")
    public ResponseEntity<Map<String, Object>> getMyPosts() {
        return ResponseEntity.ok(null);
    }
}
