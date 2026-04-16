package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.BoardCreateRequest;
import com.catholic.moyeo.board.dto.BoardDetailResponse;
import com.catholic.moyeo.board.dto.BoardListResponse;
import com.catholic.moyeo.board.dto.BoardPostBookmarkResponse;
import com.catholic.moyeo.board.dto.BoardUpdateRequest;
import com.catholic.moyeo.board.service.BoardService;
import com.catholic.moyeo.board.service.CurrentUserProvider;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 API
 *
 * 명세 대응:
 * - GET    /api/boards/posts
 * - GET    /api/boards/posts/bookmarks
 * - GET    /api/boards/posts/likes
 * - GET    /api/boards/posts/me
 * - GET    /api/boards/posts/{id}
 * - POST   /api/boards/posts
 * - POST   /api/boards/posts/{id}/bookmark
 * - PUT    /api/boards/posts/{id}
 * - DELETE /api/boards/posts/{id}
 * - DELETE /api/boards/posts/{id}/bookmark
 */
@RestController
@RequestMapping("/api/boards/posts")
public class BoardController {

    private final BoardService boardService;
    private final CurrentUserProvider currentUserProvider;

    public BoardController(BoardService boardService, CurrentUserProvider currentUserProvider) {
        this.boardService = boardService;
        this.currentUserProvider = currentUserProvider;
    }

    /**
     * 게시글 목록 조회
     *
     * - keyword가 없으면 전체 목록
     * - keyword가 있으면 제목/본문 검색
     */
    @GetMapping
    public ResponseEntity<BoardListResponse> listPosts(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.listPosts(keyword, pageable, me));
    }

    /**
     * 내가 북마크한 게시글 목록 조회
     */
    @GetMapping("/bookmarks")
    public ResponseEntity<BoardListResponse> listBookmarkedPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.listBookmarkedPosts(me, pageable));
    }

    /**
     * 내가 좋아요한 게시글 목록 조회
     */
    @GetMapping("/likes")
    public ResponseEntity<BoardListResponse> listLikedPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.listLikedPosts(me, pageable));
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> getPost(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.getPost(id, me));
    }

    /**
     * 게시글 작성
     */
    @PostMapping
    public ResponseEntity<BoardDetailResponse> createPost(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        BoardDetailResponse response = boardService.createPost(me, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시글 북마크 추가
     *
     * - 현재 bookmarked 상태를 반환한다.
     * - 프론트가 추가 조회 없이 버튼 상태를 즉시 갱신할 수 있게 한다.
     */
    @PostMapping("/{id}/bookmark")
    public ResponseEntity<BoardPostBookmarkResponse> addBookmark(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        BoardPostBookmarkResponse response = boardService.addBookmark(me, id);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody BoardUpdateRequest request
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.updatePost(me, id, request));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        boardService.deletePost(me, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 게시글 북마크 삭제
     *
     * - 현재 bookmarked 상태를 반환한다.
     * - 북마크가 없는 상태여도 에러로 처리하지 않고 bookmarked=false를 반환한다.
     */
    @DeleteMapping("/{id}/bookmark")
    public ResponseEntity<BoardPostBookmarkResponse> removeBookmark(@PathVariable Long id) {
        Long me = currentUserProvider.getCurrentUserId();
        BoardPostBookmarkResponse response = boardService.removeBookmark(me, id);
        return ResponseEntity.ok(response);
    }

    /**
     * 내가 작성한 게시글 목록 조회
     */
    @GetMapping("/me")
    public ResponseEntity<BoardListResponse> listMyPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long me = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(boardService.listMyPosts(me, pageable));
    }
}