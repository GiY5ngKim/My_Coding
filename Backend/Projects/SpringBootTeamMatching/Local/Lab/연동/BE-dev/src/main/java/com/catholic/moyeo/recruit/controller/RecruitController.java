package com.catholic.moyeo.recruit.controller;

import com.catholic.moyeo.recruit.dto.ApplicationDecisionRequest;
import com.catholic.moyeo.recruit.dto.ApplicationListResponse;
import com.catholic.moyeo.recruit.dto.ApplicationResponse;
import com.catholic.moyeo.recruit.dto.ApplyStatusResponse;
import com.catholic.moyeo.recruit.dto.MyAppliedRecruitListResponse;
import com.catholic.moyeo.recruit.dto.MyAppliedRecruitResponse;
import com.catholic.moyeo.recruit.dto.RecruitCreateRequest;
import com.catholic.moyeo.recruit.dto.RecruitDetailResponse;
import com.catholic.moyeo.recruit.dto.RecruitListResponse;
import com.catholic.moyeo.recruit.dto.RecruitStatusUpdateRequest;
import com.catholic.moyeo.recruit.dto.RecruitSummaryResponse;
import com.catholic.moyeo.recruit.dto.RecruitUpdateRequest;
import com.catholic.moyeo.recruit.service.RecruitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Recruit API Controller
 *
 * ==============================
 * [Recruit API 핵심 정책]
 * ==============================
 *
 * 1) 검색 호출 정책
 * - 목록 검색은 프론트에서 "검색 버튼 클릭 시"만 API를 호출한다.
 * - 그러나 백엔드는 항상 query filter를 지원하도록 구현한다.
 *
 *
 * 2) 값 고정 정책 (activityCategory / recruitCategory / status)
 * - activityCategory(1차 필터), recruitCategory(2차 필터)
 *   - DB에는 VARCHAR로 저장한다.
 *   - DTO에서는 String으로 받고,
 *   - 서비스에서 허용 값 검증 및 정규화를 수행한다.
 *   - 허용되지 않은 값은 400 Bad Request로 처리한다.
 *
 * - status
 *   - RecruitPostStatus Enum(OPEN, CLOSED)으로 관리한다.
 *   - Controller에서는 String 입력을 받고,
 *   - 서비스에서 Enum으로 파싱/검증 후 처리한다.
 *
 *
 * 3) tag 정책
 * - tag는 표시용 필드이다.
 * - 목록 검색/필터 query parameter에서는 사용하지 않는다.
 *
 *
 * 4) required_skills 저장 정책
 * - DB에는 CSV 문자열(VARCHAR)로 저장한다.
 *   예: "Java,Spring,Redis"
 *
 * - API에서는 List<String> 형태로 입력/응답한다.
 *
 * - 목록 검색에서는 skills 파라미터를 CSV 문자열로 받는다.
 *   예: skills=Java,Spring
 *
 *
 * 5) apply / cancel 멱등성
 * - apply
 *   - 이미 지원한 상태에서 apply 재호출 → 200 OK
 *
 * - cancel
 *   - 지원하지 않은 상태에서 cancel 재호출 → 200 OK
 *
 * - 중복 지원 방지는
 *   - 서비스 체크 + DB UNIQUE(recruit_post_id, user_id)로 2중 방어한다.
 *
 *
 * 6) 지원 취소 정책
 * - 지원 취소는 status 변경이 아니라 row 삭제로 처리한다.
 * - soft delete는 사용하지 않는다.
 *
 * - ACCEPTED 상태도 취소 시 row 삭제 가능하다.
 *   (단, applicant_count 감소는 서비스에서 처리한다.)
 *
 *
 * 7) 모집 상태 정책
 * - OPEN + deadline 유효 상태에서만 아래 행위가 가능하다.
 *
 *   - apply (지원)
 *   - cancel (지원 취소)
 *   - decide (승인 / 거절)
 *
 * - CLOSED 상태이거나 deadline이 지난 경우
 *   → 모든 행위는 400 Bad Request로 처리한다.
 *
 *
 * ==============================
 * [API 응답 구조 정책]
 * ==============================
 *
 * 1) Page 응답 정책
 * - Spring Page<T> 객체를 그대로 API로 노출하지 않는다.
 * - API 명세에 맞춰 wrapper DTO를 사용한다.
 *
 *   예)
 *   {
 *     "recruits": [...],
 *     "pageInfo": {...}
 *   }
 *
 *
 * 2) 지원 승인/거절 기준
 * - 승인/거절 대상은 "member"가 아니라
 *   recruit_application 레코드(applicationId)이다.
 *
 * - 즉 작성자는
 *   applicationId 기준으로 ACCEPTED / REJECTED 결정을 수행한다.
 *
 *
 * ==============================
 * [카운트 정책]
 * ==============================
 *
 * applicant_count 정의
 *
 * - "작성자 포함 현재 참여 인원"
 *
 * 즉
 *   applicant_count = 1(작성자) + ACCEPTED 인원 수
 *
 * - apply(지원)만으로는 증가하지 않는다.
 * - ACCEPTED 승인 시에만 증가한다.
 *
 * - applicant_count == total_headcount
 *   → 서비스에서 자동으로 status = CLOSED 전환한다.
 *
 *
 * ==============================
 * [카테고리 2단계 필터 정책]
 * ==============================
 *
 * 1) activityCategory
 * - 1차 필터
 * - ActivityCategory Enum 기반
 * - 예: PROJECT, CONTEST, STUDY, ACADEMIC
 *
 * 2) recruitCategory
 * - 2차 필터
 * - RecruitCategory Enum 기반
 * - 예: PLAN, DEVELOP, DESIGN, MARKETING, ETC
 *
 * 3) 레거시 호환
 * - 기존 프론트/테스트 호환을 위해
 *   목록 조회에서는 type/activityCategory, category/recruitCategory를
 *   당분간 함께 받는다.
 * - 생성/수정 DTO도 현재는 기존 필드명(type, category)을 유지하되,
 *   의미만 각각 1차/2차 카테고리로 해석한다.
 */
@RestController
@RequestMapping("/api/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    /**
     * 모집글 목록 조회 + 필터/검색
     *
     * Query:
     * - activityCategory: (optional) 1차 필터
     * - recruitCategory: (optional) 2차 필터
     * - type: (optional, legacy alias of activityCategory)
     * - category: (optional, legacy alias of recruitCategory)
     * - status: (optional) OPEN / CLOSED
     * - keyword: (optional)
     * - skills: (optional) CSV 문자열
     * - page, size
     *
     * Response:
     * - recruits[]
     * - pageInfo { totalElements, totalPages, page, size }
     */
    @GetMapping
    public ResponseEntity<RecruitListResponse> list(
            @RequestParam(required = false) String activityCategory,
            @RequestParam(required = false) String recruitCategory,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String skills,
            Pageable pageable
    ) {
        String resolvedActivityCategory = firstNonBlank(activityCategory, type);
        String resolvedRecruitCategory = firstNonBlank(recruitCategory, category);

        Page<RecruitSummaryResponse> page = recruitService.list(
                resolvedActivityCategory,
                resolvedRecruitCategory,
                status,
                keyword,
                skills,
                pageable
        );

        return ResponseEntity.ok(RecruitListResponse.from(page));
    }

    /**
     * 모집글 단건 조회
     *
     * Response:
     * {
     *   recruit { ... author {...} ... },
     *   appliedByMe,
     *   applicantCount
     * }
     */
    @GetMapping("/{recruitId}")
    public ResponseEntity<RecruitDetailResponse> get(@PathVariable("recruitId") Long recruitId) {
        return ResponseEntity.ok(recruitService.get(recruitId));
    }

    /**
     * 모집글 생성 (로그인 필요)
     *
     * 정책:
     * - author는 현재 로그인 사용자로 서버에서 설정한다.
     * - status는 OPEN으로 시작한다.
     * - applicantCount 초기값은 서버 정책에 따라 설정한다.
     *
     * 주의:
     * - 현재 DTO 필드명은 기존 호환을 위해 유지한다.
     *   req.type     -> activityCategory(1차 필터)
     *   req.category -> recruitCategory(2차 필터)
     *
     * Response:
     * - 모집글 상세 조회 응답과 동일 shape
     */
    @PostMapping
    public ResponseEntity<RecruitDetailResponse> create(@RequestBody @Valid RecruitCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recruitService.create(req));
    }

    /**
     * 모집글 수정 (작성자만)
     *
     * 정책:
     * - null 필드는 미수정
     * - totalHeadcount를 applicantCount보다 작게 줄이려 하면 400
     *
     * 주의:
     * - 현재 DTO 필드명은 기존 호환을 위해 유지한다.
     *   req.type     -> activityCategory(1차 필터)
     *   req.category -> recruitCategory(2차 필터)
     */
    @PatchMapping("/{recruitId}")
    public ResponseEntity<RecruitDetailResponse> update(
            @PathVariable("recruitId") Long recruitId,
            @RequestBody @Valid RecruitUpdateRequest req
    ) {
        return ResponseEntity.ok(recruitService.update(recruitId, req));
    }

    /**
     * 모집글 삭제 (작성자만)
     */
    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Void> delete(@PathVariable("recruitId") Long recruitId) {
        recruitService.delete(recruitId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 모집글 상태 변경 (작성자만)
     *
     * Request body:
     * - status: OPEN | CLOSED
     *
     * Response:
     * - 모집글 상세 조회 응답과 동일 shape
     */
    @PatchMapping("/{recruitId}/status")
    public ResponseEntity<RecruitDetailResponse> updateStatus(
            @PathVariable("recruitId") Long recruitId,
            @RequestBody @Valid RecruitStatusUpdateRequest req
    ) {
        return ResponseEntity.ok(recruitService.updateStatus(recruitId, req));
    }

    /**
     * 모집글 지원 (멱등)
     *
     * Response:
     * - appliedByMe
     * - applicantCount
     */
    @PostMapping("/{recruitId}/apply")
    public ResponseEntity<ApplyStatusResponse> apply(@PathVariable("recruitId") Long recruitId) {
        return ResponseEntity.ok(recruitService.apply(recruitId));
    }

    /**
     * 모집글 지원 취소 (멱등)
     *
     * Response:
     * - appliedByMe
     * - applicantCount
     */
    @DeleteMapping("/{recruitId}/apply")
    public ResponseEntity<ApplyStatusResponse> cancelApply(@PathVariable("recruitId") Long recruitId) {
        return ResponseEntity.ok(recruitService.cancelApply(recruitId));
    }

    /**
     * 지원자 목록 조회 (작성자만)
     *
     * Response:
     * - applications[]
     * - pageInfo { totalElements, totalPages, page, size }
     */
    @GetMapping("/{recruitId}/applications")
    public ResponseEntity<ApplicationListResponse> listApplications(
            @PathVariable("recruitId") Long recruitId,
            Pageable pageable
    ) {
        Page<ApplicationResponse> page = recruitService.listApplicationsAsAuthor(recruitId, pageable);
        return ResponseEntity.ok(ApplicationListResponse.from(page));
    }

    /**
     * 지원 승인 / 거절 (작성자만)
     *
     * Request body:
     * - status: ACCEPTED | REJECTED
     *
     * Response:
     * - application { ... } 구조에 맞는 DTO
     */
    @PatchMapping("/{recruitId}/applications/{applicationId}")
    public ResponseEntity<ApplicationResponse> decide(
            @PathVariable("recruitId") Long recruitId,
            @PathVariable("applicationId") Long applicationId,
            @RequestBody @Valid ApplicationDecisionRequest req
    ) {
        return ResponseEntity.ok(recruitService.decideApplication(recruitId, applicationId, req));
    }

    /**
     * 내가 작성한 모집글 목록
     *
     * Response:
     * - recruits[]
     * - pageInfo { totalElements, totalPages, page, size }
     */
    @GetMapping("/me")
    public ResponseEntity<RecruitListResponse> myPosts(Pageable pageable) {
        Page<RecruitSummaryResponse> page = recruitService.myPosts(pageable);
        return ResponseEntity.ok(RecruitListResponse.from(page));
    }

    /**
     * 내가 지원한 모집글 목록
     *
     * Response:
     * - recruits[]
     * - pageInfo { totalElements, totalPages, page, size }
     */
    @GetMapping("/applied")
    public ResponseEntity<MyAppliedRecruitListResponse> myApplied(Pageable pageable) {
        Page<MyAppliedRecruitResponse> page = recruitService.myApplied(pageable);
        return ResponseEntity.ok(MyAppliedRecruitListResponse.from(page));
    }

    /**
     * 앞에서 전달된 값 중 첫 번째 non-blank 값을 반환한다.
     *
     * 사용처:
     * - 신규 파라미터(activityCategory, recruitCategory) 우선 사용
     * - 레거시 alias(type, category)는 보조 수단으로만 사용
     */
    private String firstNonBlank(String preferred, String legacy) {
        if (preferred != null && !preferred.trim().isEmpty()) {
            return preferred;
        }
        if (legacy != null && !legacy.trim().isEmpty()) {
            return legacy;
        }
        return null;
    }
}