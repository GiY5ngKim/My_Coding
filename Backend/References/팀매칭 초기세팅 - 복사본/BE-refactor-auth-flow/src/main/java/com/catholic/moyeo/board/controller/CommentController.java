package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentController {

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Map<String, Object>> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Map<String, Object>>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(null);
    }
}
