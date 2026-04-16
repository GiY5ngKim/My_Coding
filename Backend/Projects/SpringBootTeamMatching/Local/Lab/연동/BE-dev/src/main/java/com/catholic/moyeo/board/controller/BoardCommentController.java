package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.BoardCommentResponse;
import com.catholic.moyeo.board.dto.CommentCreateRequest;
import com.catholic.moyeo.board.dto.CommentUpdateRequest;
import com.catholic.moyeo.board.service.BoardCommentService;
import com.catholic.moyeo.board.service.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;
    private final CurrentUserProvider currentUserProvider;

    /**
     * 댓글 작성
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<BoardCommentResponse> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        Long me = currentUserProvider.getCurrentUserId();

        BoardCommentResponse response = boardCommentService.create(
                me,
                postId,
                request.getContent()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<BoardCommentResponse>> getComments(
            @PathVariable Long postId
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardCommentService.getComments(postId, me));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        Long me = currentUserProvider.getCurrentUserId();
        boardCommentService.delete(me, commentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<BoardCommentResponse> update(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        Long me = currentUserProvider.getCurrentUserId();

        BoardCommentResponse response = boardCommentService.update(
                me,
                commentId,
                request.getContent()
        );

        return ResponseEntity.ok(response);
    }
}