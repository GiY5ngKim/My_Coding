package com.catholic.moyeo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards/posts")
@RequiredArgsConstructor
public class LikeController {

    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> addLike(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> removeLike(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/likes")
    public ResponseEntity<Map<String, Object>> getLikes() {
        return ResponseEntity.ok(null);
    }
}
