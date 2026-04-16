package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 게시글 댓글 Repository
 *
 * 역할:
 * - 특정 게시글의 댓글 목록 조회
 * - 특정 게시글의 댓글 수 조회
 * - 게시글 목록 응답용 댓글 수 배치 조회
 * - 게시글 삭제 시 연관 댓글 정리
 */
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    /**
     * 게시글 상세 화면용 댓글 목록 조회
     *
     * 정렬:
     * - 최신 댓글부터 보여주기 위해 createdAt 내림차순 정렬
     * - 같은 시각이면 id 내림차순으로 한 번 더 정렬해 순서를 안정화한다.
     */
    List<BoardComment> findByBoardPostIdOrderByCreatedAtDescIdDesc(Long boardPostId);

    /**
     * 게시글 1개의 댓글 수 조회
     */
    long countByBoardPostId(Long boardPostId);

    /**
     * 게시글 목록용 댓글 수 배치 조회
     *
     * 반환 형식:
     * - row[0] = boardPostId
     * - row[1] = commentCount
     */
    @Query("""
        select c.boardPostId, count(c)
        from BoardComment c
        where c.boardPostId in :postIds
        group by c.boardPostId
        """)
    List<Object[]> countGroupByBoardPostIds(@Param("postIds") List<Long> postIds);

    /**
     * 게시글 삭제 시 해당 게시글에 달린 댓글 전체 삭제
     */
    void deleteByBoardPostId(Long boardPostId);
}