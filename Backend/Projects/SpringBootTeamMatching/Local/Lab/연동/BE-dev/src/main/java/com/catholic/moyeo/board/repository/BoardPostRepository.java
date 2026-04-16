package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.BoardPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 게시글 조회/검색 Repository
 *
 * 역할:
 * - 전체 게시글 목록 조회
 * - keyword 기반 제목/본문 검색
 * - 특정 사용자의 게시글 목록 조회
 * - 특정 사용자가 북마크한 게시글 목록 조회
 * - 특정 사용자가 좋아요한 게시글 목록 조회
 *
 * 주의:
 * - keyword가 null인 경우는 Service에서 분기 처리한다.
 *   (JPQL에서 :keyword is null 과 LIKE 검색을 한 쿼리에 같이 넣으면
 *    PostgreSQL에서 타입 추론 문제가 발생할 수 있다.)
 * - 정렬은 Service에서 Pageable에 기본 정렬(createdAt desc)을 보정하여 넘긴다.
 */
public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

    /**
     * 전체 게시글 조회
     *
     * - 검색어 없이 전체 목록만 조회할 때 사용
     * - 정렬은 Pageable 기준으로 적용
     */
    Page<BoardPost> findAll(Pageable pageable);

    /**
     * 게시글 검색
     *
     * 동작:
     * - keyword는 null이 아닌 정상 문자열만 들어온다고 가정
     * - 제목/본문 LIKE 검색 수행
     *
     * 주의:
     * - null 분기 처리는 Service에서 끝낸 뒤 이 메서드를 호출해야 한다.
     */
    @Query("""
        select p
        from BoardPost p
        where lower(p.title) like lower(concat('%', :keyword, '%'))
           or lower(p.content) like lower(concat('%', :keyword, '%'))
        """)
    Page<BoardPost> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 내 게시글 조회
     *
     * - authorUserId 기준으로 조회
     * - 정렬은 Pageable 기준으로 적용
     */
    Page<BoardPost> findByAuthorUserId(Long authorUserId, Pageable pageable);

    /**
     * 내가 북마크한 게시글 조회
     *
     * - userId 기준으로 북마크 테이블과 조인하여 조회
     * - 정렬은 Pageable 기준으로 적용
     */
    @Query("""
        select p
        from BoardPost p
        join BoardPostBookmark b on b.boardPostId = p.id
        where b.userId = :userId
        """)
    Page<BoardPost> findBookmarkedPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * 내가 좋아요한 게시글 조회
     *
     * - userId 기준으로 좋아요 테이블과 조인하여 조회
     * - 정렬은 Pageable 기준으로 적용
     */
    @Query("""
        select p
        from BoardPost p
        join BoardPostLike l on l.boardPostId = p.id
        where l.userId = :userId
        """)
    Page<BoardPost> findLikedPostsByUserId(@Param("userId") Long userId, Pageable pageable);
}