package com.catholic.moyeo.recruit.repository;

import com.catholic.moyeo.recruit.domain.RecruitPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * recruit_post 접근 레이어
 *
 * 정책(중요):
 * - 목록 검색/필터는 서비스에서 Specification(Criteria)로 조립한다.
 * - 승인/취소 등 applicant_count 조정이 발생하는 경쟁 구간은
 *   PESSIMISTIC_WRITE lock으로 보호한다.
 *
 * 카테고리 2단계 정책:
 * - DB 컬럼명은 기존 호환을 위해 유지한다.
 *   type     -> ActivityCategory(1차 필터)
 *   category -> RecruitCategory(2차 필터)
 *
 * - 목록 조건식은 Repository 메서드 분기 대신
 *   서비스의 Specification 로직 하나로만 유지한다.
 *
 * NOTE(팀 공유):
 * - search() default 메서드는 현재 서비스 구현에서 사용하지 않으므로 제거했다.
 *   목록 조건식은 서비스의 Specification 로직 하나로만 유지한다.
 */
public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long>, JpaSpecificationExecutor<RecruitPost> {

    /**
     * 내가 작성한 모집글 목록 조회
     */
    Page<RecruitPost> findByAuthorUserId(Long authorUserId, Pageable pageable);

    /**
     * 승인/거절/취소 등 applicant_count 변경이 발생하는 시점의 잠금 조회
     *
     * 사용처:
     * - 지원 승인/거절
     * - ACCEPTED 취소
     * - 정원 마감 정합성 유지
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from RecruitPost p where p.id = :id")
    Optional<RecruitPost> findByIdForUpdate(@Param("id") Long id);
}