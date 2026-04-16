package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.BoardPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 게시글 좋아요 Repository
 *
 * 역할:
 * - 좋아요 삭제
 * - 게시글 1개의 좋아요 수 조회
 * - 특정 사용자의 좋아요 여부 확인
 * - 게시글 목록 응답용 좋아요 관련 배치 조회
 * - 게시글 삭제 시 연관 좋아요 정리
 */
public interface BoardPostLikeRepository extends JpaRepository<BoardPostLike, Long> {

    /**
     * 특정 사용자의 특정 게시글 좋아요 삭제
     */
    void deleteByBoardPostIdAndUserId(Long boardPostId, Long userId);

    /**
     * 게시글 1개의 좋아요 수 조회
     */
    long countByBoardPostId(Long boardPostId);

    /**
     * 특정 사용자가 특정 게시글을 좋아요 했는지 여부 확인
     */
    boolean existsByBoardPostIdAndUserId(Long boardPostId, Long userId);

    /**
     * 게시글 목록용 좋아요 수 배치 조회
     *
     * 반환 형식:
     * - row[0] = boardPostId
     * - row[1] = likeCount
     */
    @Query("""
        select l.boardPostId, count(l)
        from BoardPostLike l
        where l.boardPostId in :postIds
        group by l.boardPostId
        """)
    List<Object[]> countGroupByBoardPostIds(@Param("postIds") List<Long> postIds);

    /**
     * 특정 사용자가 좋아요 누른 게시글 ID 목록 조회
     *
     * - 목록 화면에서 likedByMe를 한 번에 계산하기 위해 사용
     */
    @Query("""
        select l.boardPostId
        from BoardPostLike l
        where l.userId = :userId
          and l.boardPostId in :postIds
        """)
    List<Long> findLikedPostIdsByUserIdAndBoardPostIds(
            @Param("userId") Long userId,
            @Param("postIds") List<Long> postIds
    );

    /**
     * 게시글 삭제 시 해당 게시글에 달린 좋아요 전체 삭제
     */
    void deleteByBoardPostId(Long boardPostId);
}