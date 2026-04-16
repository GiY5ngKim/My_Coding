package com.catholic.moyeo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards/posts")
@RequiredArgsConstructor
public class BookmarkController {

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Map<String, Object>> addBookmark(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}/bookmark")
    public ResponseEntity<Map<String, Object>> removeBookmark(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<Map<String, Object>> getBookmarks() {
        return ResponseEntity.ok(null);
    }
}
