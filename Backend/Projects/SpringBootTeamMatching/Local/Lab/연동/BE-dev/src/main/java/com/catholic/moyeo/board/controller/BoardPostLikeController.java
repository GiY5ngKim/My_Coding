package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.BoardPostLikeResponse;
import com.catholic.moyeo.board.service.BoardPostLikeService;
import com.catholic.moyeo.board.service.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 좋아요 Controller
 *
 * 정책:
 * - POST  : 좋아요 추가
 * - DELETE: 좋아요 취소
 *
 * 응답:
 * - 현재 liked 상태와 likeCount를 함께 반환한다.
 * - 프론트가 추가 조회 없이 버튼 상태와 좋아요 수를 즉시 갱신할 수 있게 한다.
 */
@RestController
@RequestMapping("/api/boards/posts")
@RequiredArgsConstructor
public class BoardPostLikeController {

    private final BoardPostLikeService boardPostLikeService;
    private final CurrentUserProvider currentUserProvider;

    /**
     * 게시글 좋아요 추가
     *
     * - 이미 좋아요가 눌린 상태여도 에러로 처리하지 않고 그대로 liked=true를 반환한다.
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<BoardPostLikeResponse> addLike(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        BoardPostLikeResponse response = boardPostLikeService.addLike(me, id);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 좋아요 취소
     *
     * - 좋아요가 없는 상태여도 에러로 처리하지 않고 그대로 liked=false를 반환한다.
     */
    @DeleteMapping("/{id}/like")
    public ResponseEntity<BoardPostLikeResponse> removeLike(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        BoardPostLikeResponse response = boardPostLikeService.removeLike(me, id);
        return ResponseEntity.ok(response);
    }
}