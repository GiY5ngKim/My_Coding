package com.catholic.moyeo.board.service;

import com.catholic.moyeo.board.domain.BoardPostLike;
import com.catholic.moyeo.board.dto.BoardPostLikeResponse;
import com.catholic.moyeo.board.repository.BoardPostLikeRepository;
import com.catholic.moyeo.board.repository.BoardPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 게시글 좋아요 서비스
 *
 * 정책:
 * - 게시글이 없으면 404
 * - 좋아요 추가/취소는 각각 별도 메서드로 처리
 * - 결과 응답으로 liked 상태와 현재 likeCount를 반환
 *
 * 이유:
 * - POST 하나로 토글하면 네트워크 재시도/중복 요청 시 상태가 뒤집힐 수 있다.
 * - POST/DELETE를 분리하면 의도가 명확하고 디버깅도 쉬워진다.
 */
@Service
@RequiredArgsConstructor
public class BoardPostLikeService {

    private final BoardPostRepository boardPostRepository;
    private final BoardPostLikeRepository boardPostLikeRepository;

    /**
     * 게시글 좋아요 추가
     *
     * - 이미 좋아요가 있는 경우 중복 저장하지 않는다.
     * - 최종 상태는 항상 liked=true
     */
    @Transactional
    public BoardPostLikeResponse addLike(Long userId, Long postId) {
        ensurePostExists(postId);

        boolean alreadyLiked = boardPostLikeRepository.existsByBoardPostIdAndUserId(postId, userId);

        if (!alreadyLiked) {
            boardPostLikeRepository.save(new BoardPostLike(postId, userId));
        }

        long likeCount = boardPostLikeRepository.countByBoardPostId(postId);
        return BoardPostLikeResponse.of(true, likeCount);
    }

    /**
     * 게시글 좋아요 취소
     *
     * - 좋아요가 없어도 에러로 처리하지 않는다.
     * - 최종 상태는 항상 liked=false
     */
    @Transactional
    public BoardPostLikeResponse removeLike(Long userId, Long postId) {
        ensurePostExists(postId);

        boardPostLikeRepository.deleteByBoardPostIdAndUserId(postId, userId);

        long likeCount = boardPostLikeRepository.countByBoardPostId(postId);
        return BoardPostLikeResponse.of(false, likeCount);
    }

    /**
     * 게시글 존재 여부 확인
     */
    private void ensurePostExists(Long postId) {
        boardPostRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }
}