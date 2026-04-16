package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.BoardPostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 북마크 Repository
 *
 * 역할:
 * - 특정 게시글에 대해 현재 사용자가 북마크했는지 조회
 * - 게시글 북마크 추가/삭제
 * - 게시글 삭제 시 연관 북마크 정리
 * - 게시글 목록 응답용 북마크 여부 배치 조회
 *
 * 참고:
 * - BoardPostLikeRepository의 메서드 패턴과 최대한 맞춘다.
 */
public interface BoardPostBookmarkRepository extends JpaRepository<BoardPostBookmark, Long> {

    /**
     * 특정 사용자의 특정 게시글 북마크 1건 조회
     */
    Optional<BoardPostBookmark> findByBoardPostIdAndUserId(Long boardPostId, Long userId);

    /**
     * 특정 사용자의 특정 게시글 북마크 삭제
     */
    void deleteByBoardPostIdAndUserId(Long boardPostId, Long userId);

    /**
     * 게시글 삭제 시 해당 게시글에 달린 북마크 전체 삭제
     */
    void deleteByBoardPostId(Long boardPostId);

    /**
     * 특정 사용자가 특정 게시글을 북마크했는지 여부 확인
     */
    boolean existsByBoardPostIdAndUserId(Long boardPostId, Long userId);

    /**
     * 특정 사용자가 북마크한 게시글 ID 목록 조회
     *
     * - 목록 화면에서 bookmarkedByMe를 한 번에 계산하기 위해 사용
     */
    @Query("""
        select b.boardPostId
        from BoardPostBookmark b
        where b.userId = :userId
          and b.boardPostId in :postIds
        """)
    List<Long> findBookmarkedPostIdsByUserIdAndBoardPostIds(
            @Param("userId") Long userId,
            @Param("postIds") List<Long> postIds
    );
}