package com.catholic.moyeo.recruit.service;

import com.catholic.moyeo.common.domain.ActivityCategory;
import com.catholic.moyeo.common.domain.RecruitCategory;
import com.catholic.moyeo.recruit.domain.RecruitApplication;
import com.catholic.moyeo.recruit.domain.RecruitApplicationStatus;
import com.catholic.moyeo.recruit.domain.RecruitPost;
import com.catholic.moyeo.recruit.domain.RecruitPostStatus;
import com.catholic.moyeo.recruit.dto.ApplicationDecisionRequest;
import com.catholic.moyeo.recruit.dto.ApplicationResponse;
import com.catholic.moyeo.recruit.dto.ApplyStatusResponse;
import com.catholic.moyeo.recruit.dto.MyAppliedRecruitResponse;
import com.catholic.moyeo.recruit.dto.RecruitAuthorResponse;
import com.catholic.moyeo.recruit.dto.RecruitCreateRequest;
import com.catholic.moyeo.recruit.dto.RecruitDetailResponse;
import com.catholic.moyeo.recruit.dto.RecruitStatusUpdateRequest;
import com.catholic.moyeo.recruit.dto.RecruitSummaryResponse;
import com.catholic.moyeo.recruit.dto.RecruitUpdateRequest;
import com.catholic.moyeo.recruit.repository.RecruitApplicationRepository;
import com.catholic.moyeo.recruit.repository.RecruitPostRepository;
import com.catholic.moyeo.security.AuthUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecruitService {

    /**
     * [식별자]
     * - memberId == user_id == app_user_id (공유 PK)
     * - 인증 주체는 AuthUtil.currentMemberId()로 얻는다.
     *
     * [카운트 정의]
     * - applicantCount == recruit_post.applicant_count
     *   = 작성자 포함 현재 참여 인원
     *   = 1(작성자) + ACCEPTED 인원 수
     *
     * - recruit_application row 수와는 다르다.
     *   apply(지원)만으로 applicantCount는 증가하지 않는다.
     *
     * [행위 가능 조건]
     * - OPEN 이고 deadline 미경과인 경우에만 apply/cancel/decide 가능
     * - CLOSED 또는 deadline 경과면 행위 불가(400)
     *
     * [멱등]
     * - apply: 이미 지원 row가 있으면 200 OK + appliedByMe=true
     * - cancel: 지원 row가 없으면 200 OK + appliedByMe=false
     * - 중복 insert(DataIntegrityViolationException)도 멱등으로 흡수
     *
     * [취소]
     * - 취소는 row 삭제
     * - ACCEPTED 취소(삭제)면 applicantCount - 1
     *
     * [자동 마감]
     * - applicantCount == totalHeadcount 에 도달하면 status를 CLOSED로 자동 전환
     *
     * [CSV 저장]
     * - DB: required_skills CSV
     * - API: List<String> skills
     *
     * [Member 정보 조회]
     * - 목록/상세 응답에는 author { memberId, nickname, departmentName }가 포함된다.
     * - 지원자 목록/승인·거절 응답에는
     *   applicant { memberId, nickname, contactEmail }가 포함된다.
     * - Recruit 모듈은 memberId(userId)만 보관하므로,
     *   RecruitMemberReader를 통해 Member 정보를 조회해서 조합한다.
     *
     * [카테고리 2단계 정책]
     * - activityCategory: 1차 필터 (ActivityCategory)
     * - recruitCategory: 2차 필터 (RecruitCategory)
     *
     * - 현재 DTO 필드명은 기존 호환을 위해 유지한다.
     *   create/update 요청에서는
     *   req.type     -> activityCategory
     *   req.category -> recruitCategory
     *
     * - 현재 DB 컬럼명도 기존 호환을 위해 유지한다.
     *   type 컬럼     -> activityCategory 저장
     *   category 컬럼 -> recruitCategory 저장
     *
     * [표시용 선택값]
     * - department는 사용자가 원할 때만 입력하는 표시용 문자열이다.
     * - 분류/검색/필터에는 사용하지 않는다.
     *
     * NOTE:
     * - 현재 목록 조회 시 author를 각 게시글마다 개별 조회하므로 N+1 가능성이 있다.
     * - 현재 지원자 목록 조회 시 applicant도 각 row마다 개별 조회하므로 N+1 가능성이 있다.
     * - 트래픽 증가 시 batch 조회나 projection 최적화를 검토한다.
     */

    private final RecruitPostRepository postRepo;
    private final RecruitApplicationRepository appRepo;
    private final RecruitMemberReader memberReader;

    public RecruitService(
            RecruitPostRepository postRepo,
            RecruitApplicationRepository appRepo,
            RecruitMemberReader memberReader
    ) {
        this.postRepo = postRepo;
        this.appRepo = appRepo;
        this.memberReader = memberReader;
    }

    // =========================
    // Create
    // =========================

    /**
     * 모집글 생성
     *
     * 정책:
     * - 작성자는 항상 현재 로그인 사용자다.
     * - activityCategory / recruitCategory는 여기서 정규화한다.
     * - skills는 DB에 CSV 형태로 저장한다.
     * - department는 선택 입력값이며, 저장 전에 trim 처리한다.
     * - 생성 직후 상세 응답 포맷으로 반환한다.
     *
     * 주의:
     * - 현재 DTO 필드명은 기존 호환을 위해 유지한다.
     *   req.type       -> activityCategory
     *   req.category   -> recruitCategory
     *   req.department -> department
     */
    @Transactional
    public RecruitDetailResponse create(RecruitCreateRequest req) {
        Long me = AuthUtil.currentMemberId();

        String normalizedActivityCategory = normalizeActivityCategoryOrThrow(req.getType());
        String normalizedRecruitCategory = normalizeRecruitCategoryOrThrow(req.getCategory());

        if (req.getTotalHeadcount() == null || req.getTotalHeadcount() <= 0) {
            throw new IllegalArgumentException("totalHeadcount must be positive");
        }

        String csvSkills = joinSkillCsv(req.getSkills());

        RecruitPost post = new RecruitPost(
                me,
                normalizedActivityCategory,
                normalizedRecruitCategory,
                trimToNull(req.getTag()),
                trimToNull(req.getDepartment()),
                req.getTitle(),
                req.getContent(),
                csvSkills,
                req.getTotalHeadcount().shortValue(),
                req.getDeadline()
        );

        RecruitPost saved = postRepo.save(post);

        return RecruitDetailResponse.from(
                saved,
                getAuthor(saved.getAuthorUserId()),
                splitSkillCsv(saved.getRequiredSkills()),
                false
        );
    }

    // =========================
    // Read - list
    // =========================

    /**
     * 모집글 목록 조회
     *
     * 필터 규칙:
     * - activityCategory / recruitCategory / status / keyword / skillsCsv 모두 선택적이다.
     * - keyword는 title/content OR 검색이다.
     * - skillsCsv는 requiredSkills CSV 문자열에 대한 LIKE 기반 검색이다.
     *
     * 주의:
     * - appliedByMe 계산을 위해 현재 사용자 기준 application 존재 여부를 확인한다.
     * - author 정보는 응답 조합 시점에 조회한다.
     * - department는 필터에 사용하지 않고, 응답 표시값으로만 내려준다.
     */
    @Transactional(readOnly = true)
    public Page<RecruitSummaryResponse> list(
            String activityCategory,
            String recruitCategory,
            String status,
            String keyword,
            String skillsCsv,
            Pageable pageable
    ) {
        String normalizedActivityCategory = hasText(activityCategory)
                ? normalizeActivityCategoryOrThrow(activityCategory)
                : null;
        String normalizedRecruitCategory = hasText(recruitCategory)
                ? normalizeRecruitCategoryOrThrow(recruitCategory)
                : null;
        RecruitPostStatus postStatus = parsePostStatusOrNull(status);
        List<String> skills = parseSkillsCsv(skillsCsv);

        Specification<RecruitPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (normalizedActivityCategory != null) {
                predicates.add(cb.equal(root.get("activityCategory"), normalizedActivityCategory));
            }
            if (normalizedRecruitCategory != null) {
                predicates.add(cb.equal(root.get("recruitCategory"), normalizedRecruitCategory));
            }
            if (postStatus != null) {
                predicates.add(cb.equal(root.get("status"), postStatus));
            }
            if (hasText(keyword)) {
                String like = "%" + keyword.trim() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("title"), like),
                        cb.like(root.get("content"), like)
                ));
            }
            if (skills != null && !skills.isEmpty()) {
                List<Predicate> orLikes = new ArrayList<>();
                for (String skill : skills) {
                    orLikes.add(cb.like(root.get("requiredSkills"), "%" + skill + "%"));
                }
                predicates.add(cb.or(orLikes.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<RecruitPost> page = postRepo.findAll(spec, pageable);
        Long me = AuthUtil.currentMemberId();

        return page.map(post -> {
            boolean appliedByMe = appRepo.existsByRecruitPostIdAndUserId(post.getId(), me);
            return RecruitSummaryResponse.from(
                    post,
                    splitSkillCsv(post.getRequiredSkills()),
                    appliedByMe,
                    getAuthor(post.getAuthorUserId())
            );
        });
    }

    /**
     * 모집글 단건 상세 조회
     *
     * 주의:
     * - author는 항상 조합해서 내려준다.
     * - appliedByMe는 현재 사용자 기준으로 계산한다.
     * - department는 표시용 값이므로 post에서 그대로 내려준다.
     */
    @Transactional(readOnly = true)
    public RecruitDetailResponse get(Long recruitId) {
        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        Long me = AuthUtil.currentMemberId();
        boolean appliedByMe = appRepo.existsByRecruitPostIdAndUserId(recruitId, me);

        return RecruitDetailResponse.from(
                post,
                getAuthor(post.getAuthorUserId()),
                splitSkillCsv(post.getRequiredSkills()),
                appliedByMe
        );
    }

    // =========================
    // Update / Delete / Status
    // =========================

    /**
     * 모집글 수정
     *
     * 정책:
     * - 작성자만 수정 가능
     * - null 필드는 수정하지 않는다.
     * - totalHeadcount는 현재 applicantCount보다 작게 줄일 수 없다.
     * - 정원 변경 후 가득 찼다면 자동 마감한다.
     * - department는 선택 입력값이며, 들어오면 trim 후 저장한다.
     *
     * 주의:
     * - 현재 DTO 필드명은 기존 호환을 위해 유지한다.
     *   req.type       -> activityCategory
     *   req.category   -> recruitCategory
     *   req.department -> department
     */
    @Transactional
    public RecruitDetailResponse update(Long recruitId, RecruitUpdateRequest req) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureAuthor(post, me);

        if (req.getType() != null) {
            post.setActivityCategory(normalizeActivityCategoryOrThrow(req.getType()));
        }
        if (req.getCategory() != null) {
            post.setRecruitCategory(normalizeRecruitCategoryOrThrow(req.getCategory()));
        }
        if (req.getTag() != null) {
            post.setTag(trimToNull(req.getTag()));
        }
        if (req.getDepartment() != null) {
            post.setDepartment(trimToNull(req.getDepartment()));
        }
        if (req.getTitle() != null) {
            post.setTitle(req.getTitle());
        }
        if (req.getContent() != null) {
            post.setContent(req.getContent());
        }
        if (req.getSkills() != null) {
            post.setRequiredSkills(joinSkillCsv(req.getSkills()));
        }
        if (req.getDeadline() != null) {
            post.setDeadline(req.getDeadline());
        }
        if (req.getTotalHeadcount() != null) {
            if (req.getTotalHeadcount() <= 0) {
                throw new IllegalArgumentException("totalHeadcount must be positive");
            }
            if (req.getTotalHeadcount() < post.getApplicantCount()) {
                throw new IllegalArgumentException("totalHeadcount cannot be less than applicantCount");
            }
            post.setTotalHeadcount(req.getTotalHeadcount().shortValue());
            autoCloseIfFull(post);
        }

        boolean appliedByMe = appRepo.existsByRecruitPostIdAndUserId(recruitId, me);

        return RecruitDetailResponse.from(
                post,
                getAuthor(post.getAuthorUserId()),
                splitSkillCsv(post.getRequiredSkills()),
                appliedByMe
        );
    }

    /**
     * 모집글 삭제
     *
     * 정책:
     * - 작성자만 삭제 가능
     * - 연관 데이터 정리는 엔티티 매핑/DB 제약 정책을 따른다.
     */
    @Transactional
    public void delete(Long recruitId) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureAuthor(post, me);
        postRepo.delete(post);
    }

    /**
     * 모집 상태 수동 변경
     *
     * 정책:
     * - 작성자만 변경 가능
     * - 허용값은 OPEN / CLOSED
     * - 상태만 바꾸며 나머지 필드는 유지한다.
     */
    @Transactional
    public RecruitDetailResponse updateStatus(Long recruitId, RecruitStatusUpdateRequest req) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureAuthor(post, me);

        RecruitPostStatus next = parseRequiredPostStatus(req.getStatus());
        post.setStatus(next);

        boolean appliedByMe = appRepo.existsByRecruitPostIdAndUserId(recruitId, me);

        return RecruitDetailResponse.from(
                post,
                getAuthor(post.getAuthorUserId()),
                splitSkillCsv(post.getRequiredSkills()),
                appliedByMe
        );
    }

    // =========================
    // Apply / Cancel
    // =========================

    /**
     * 모집 지원
     *
     * 정책:
     * - OPEN + deadline 미경과 상태에서만 가능
     * - 작성자는 자신의 모집글에 지원할 수 없다.
     * - 이미 지원한 경우 멱등 처리
     * - 지원 row 생성만 수행하며 applicantCount는 증가하지 않는다.
     */
    @Transactional
    public ApplyStatusResponse apply(Long recruitId) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureOpenForUserAction(post);

        if (post.getAuthorUserId().equals(me)) {
            throw new IllegalArgumentException("Author cannot apply to own recruit");
        }

        if (appRepo.existsByRecruitPostIdAndUserId(recruitId, me)) {
            return ApplyStatusResponse.of(true, post.getApplicantCount());
        }

        try {
            appRepo.save(new RecruitApplication(recruitId, me));
        } catch (DataIntegrityViolationException ignored) {
            // unique 충돌도 멱등으로 흡수
        }

        return ApplyStatusResponse.of(true, post.getApplicantCount());
    }

    /**
     * 지원 취소
     *
     * 정책:
     * - OPEN + deadline 미경과 상태에서만 가능
     * - 지원 row가 없으면 멱등 처리
     * - ACCEPTED 상태 지원 취소 시 applicantCount를 감소시킨다.
     *
     * 주의:
     * - ACCEPTED 취소 시에는 모집글을 비관적 락으로 다시 조회해 count를 안전하게 조정한다.
     */
    @Transactional
    public ApplyStatusResponse cancelApply(Long recruitId) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureOpenForUserAction(post);

        Optional<RecruitApplication> optionalApp = appRepo.findByRecruitPostIdAndUserId(recruitId, me);
        if (optionalApp.isEmpty()) {
            return ApplyStatusResponse.of(false, post.getApplicantCount());
        }

        RecruitApplication app = optionalApp.get();
        long applicantCountAfter = post.getApplicantCount();

        if (app.getStatus() == RecruitApplicationStatus.ACCEPTED) {
            RecruitPost locked = postRepo.findByIdForUpdate(recruitId)
                    .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));
            locked.decreaseApplicantCount();
            applicantCountAfter = locked.getApplicantCount();
        }

        appRepo.delete(app);

        return ApplyStatusResponse.of(false, applicantCountAfter);
    }

    // =========================
    // Applications
    // =========================

    /**
     * 작성자 관점 지원자 목록 조회
     *
     * 정책:
     * - 작성자만 조회 가능
     * - applicant 정보는 memberReader로 조합한다.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationResponse> listApplicationsAsAuthor(Long recruitId, Pageable pageable) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findById(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureAuthor(post, me);

        return appRepo.findByRecruitPostId(recruitId, pageable)
                .map(app -> {
                    ApplicationResponse.Applicant applicant = getApplicant(app.getUserId());
                    return toApplicationResponse(app, applicant);
                });
    }

    /**
     * 지원 승인/거절
     *
     * 정책:
     * - 작성자만 가능
     * - OPEN + deadline 미경과 상태에서만 가능
     * - ACCEPTED 전환 시 applicantCount 증가
     * - ACCEPTED -> REJECTED 전환 시 applicantCount 감소
     * - 정원 도달 시 자동 마감
     *
     * 주의:
     * - 모집글은 count/status 동시성 제어를 위해 for update 조회를 사용한다.
     * - 동일 상태로 재요청되면 멱등하게 현재 상태를 그대로 반환한다.
     */
    @Transactional
    public ApplicationResponse decideApplication(Long recruitId, Long applicationId, ApplicationDecisionRequest req) {
        Long me = AuthUtil.currentMemberId();

        RecruitPost post = postRepo.findByIdForUpdate(recruitId)
                .orElseThrow(() -> new IllegalArgumentException("Recruit not found: " + recruitId));

        ensureAuthor(post, me);
        ensureOpenForUserAction(post);

        RecruitApplication app = appRepo.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        if (!app.getRecruitPostId().equals(recruitId)) {
            throw new IllegalArgumentException("Application does not belong to recruit");
        }

        RecruitApplicationStatus next = parseDecisionStatus(req.getStatus());
        RecruitApplicationStatus current = app.getStatus();

        if (current == next) {
            ApplicationResponse.Applicant applicant = getApplicant(app.getUserId());
            return toApplicationResponse(app, applicant);
        }

        if (current != RecruitApplicationStatus.ACCEPTED && next == RecruitApplicationStatus.ACCEPTED) {
            if (post.getApplicantCount() >= post.getTotalHeadcount()) {
                autoCloseIfFull(post);
                throw new IllegalStateException("Recruit is full");
            }

            post.increaseApplicantCount();
            autoCloseIfFull(post);

        } else if (current == RecruitApplicationStatus.ACCEPTED && next != RecruitApplicationStatus.ACCEPTED) {
            post.decreaseApplicantCount();
        }

        app.setStatus(next);

        ApplicationResponse.Applicant applicant = getApplicant(app.getUserId());
        return toApplicationResponse(app, applicant);
    }

    // =========================
    // My pages
    // =========================

    /**
     * 내가 작성한 모집글 목록
     *
     * 주의:
     * - 내가 쓴 글이므로 appliedByMe는 항상 false로 내려간다.
     * - 필요 시 프론트에서 별도 의미를 부여하지 않도록 맞춰둔 값이다.
     */
    @Transactional(readOnly = true)
    public Page<RecruitSummaryResponse> myPosts(Pageable pageable) {
        Long me = AuthUtil.currentMemberId();

        return postRepo.findByAuthorUserId(me, pageable)
                .map(post -> RecruitSummaryResponse.from(
                        post,
                        splitSkillCsv(post.getRequiredSkills()),
                        false,
                        getAuthor(post.getAuthorUserId())
                ));
    }

    /**
     * 내가 지원한 모집글 목록
     *
     * 주의:
     * - 먼저 application 페이지를 조회한 뒤 post를 일괄 조회해 매핑한다.
     * - application은 있는데 post가 없으면 데이터 정합성 문제로 간주한다.
     */
    @Transactional(readOnly = true)
    public Page<MyAppliedRecruitResponse> myApplied(Pageable pageable) {
        Long me = AuthUtil.currentMemberId();

        Page<RecruitApplication> apps = appRepo.findByUserId(me, pageable);

        Set<Long> postIds = apps.getContent().stream()
                .map(RecruitApplication::getRecruitPostId)
                .collect(Collectors.toSet());

        Map<Long, RecruitPost> postMap = postIds.isEmpty()
                ? Collections.emptyMap()
                : postRepo.findAllById(postIds).stream()
                .collect(Collectors.toMap(RecruitPost::getId, post -> post));

        return apps.map(app -> {
            RecruitPost post = postMap.get(app.getRecruitPostId());
            if (post == null) {
                throw new IllegalStateException("Recruit post missing");
            }

            return MyAppliedRecruitResponse.from(
                    post,
                    splitSkillCsv(post.getRequiredSkills()),
                    app.getStatus()
            );
        });
    }

    // =========================
    // helpers
    // =========================

    /**
     * 작성자 권한 검사
     *
     * 예외:
     * - 비작성자는 403 성격의 AccessDeniedException
     */
    private void ensureAuthor(RecruitPost post, Long me) {
        if (!post.getAuthorUserId().equals(me)) {
            throw new AccessDeniedException("Author only");
        }
    }

    /**
     * 사용자 행위 가능 상태 검사
     *
     * 적용 대상:
     * - apply / cancel / decide
     *
     * 검사 순서:
     * 1) 현재 status가 OPEN 인지
     * 2) deadline 이 지났는지
     * 3) applicantCount 기준으로 자동 마감 대상인지
     *
     * 주의:
     * - autoCloseIfFull 호출로 인해 이 메서드 내에서 상태가 CLOSED로 바뀔 수 있다.
     */
    private void ensureOpenForUserAction(RecruitPost post) {
        if (post.getStatus() != RecruitPostStatus.OPEN) {
            throw new IllegalStateException("Recruit is not open");
        }

        LocalDate deadline = post.getDeadline();
        if (deadline != null && deadline.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Recruit is not open");
        }

        autoCloseIfFull(post);
        if (post.getStatus() != RecruitPostStatus.OPEN) {
            throw new IllegalStateException("Recruit is not open");
        }
    }

    /**
     * 정원 충족 시 자동 마감
     *
     * 주의:
     * - reopen 로직은 여기서 처리하지 않는다.
     * - 상태를 OPEN으로 되돌리는 것은 명시적 상태 변경 API 책임이다.
     */
    private void autoCloseIfFull(RecruitPost post) {
        if (post.getApplicantCount() >= post.getTotalHeadcount()) {
            post.setStatus(RecruitPostStatus.CLOSED);
        }
    }

    /**
     * 선택적 status 파싱
     *
     * 규칙:
     * - 값이 없으면 null
     * - 값이 있으면 필수 status 파싱 로직 사용
     */
    private RecruitPostStatus parsePostStatusOrNull(String status) {
        if (!hasText(status)) {
            return null;
        }
        return parseRequiredPostStatus(status);
    }

    /**
     * 모집글 상태 파싱
     *
     * 허용값:
     * - OPEN
     * - CLOSED
     */
    private RecruitPostStatus parseRequiredPostStatus(String status) {
        if (!hasText(status)) {
            throw new IllegalArgumentException("status is required");
        }

        String normalized = status.trim().toUpperCase(Locale.ROOT);
        if (!normalized.equals("OPEN") && !normalized.equals("CLOSED")) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        return RecruitPostStatus.valueOf(normalized);
    }

    /**
     * 지원 의사결정 상태 파싱
     *
     * 허용값:
     * - ACCEPTED
     * - REJECTED
     *
     * 주의:
     * - PENDING 등 다른 값은 이 API에서 받지 않는다.
     */
    private RecruitApplicationStatus parseDecisionStatus(String status) {
        if (!hasText(status)) {
            throw new IllegalArgumentException("status is required");
        }

        String normalized = status.trim().toUpperCase(Locale.ROOT);
        if (!normalized.equals("ACCEPTED") && !normalized.equals("REJECTED")) {
            throw new IllegalArgumentException("Only ACCEPTED/REJECTED allowed");
        }

        return RecruitApplicationStatus.valueOf(normalized);
    }

    /**
     * activityCategory 정규화
     *
     * 정책:
     * - ActivityCategory Enum 허용값만 받는다.
     * - 영문 Enum name / 한글 label 모두 허용한다.
     * - 저장 시에는 Enum name 대문자 값으로 정규화한다.
     */
    private String normalizeActivityCategoryOrThrow(String activityCategory) {
        return ActivityCategory.from(activityCategory).name();
    }

    /**
     * recruitCategory 정규화
     *
     * 정책:
     * - RecruitCategory Enum 허용값만 받는다.
     * - 영문 Enum name / 한글 label 모두 허용한다.
     * - 저장 시에는 Enum name 대문자 값으로 정규화한다.
     */
    private String normalizeRecruitCategoryOrThrow(String recruitCategory) {
        return RecruitCategory.from(recruitCategory).name();
    }

    /**
     * 요청 파라미터용 CSV -> List 파싱
     *
     * 사용처:
     * - 목록 조회 skillsCsv 필터
     *
     * 정책:
     * - 공백 제거
     * - 빈 문자열 제거
     * - 중복 제거(입력 순서 유지)
     */
    private List<String> parseSkillsCsv(String csv) {
        if (!hasText(csv)) {
            return null;
        }

        String[] arr = csv.split(",");
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String raw : arr) {
            if (raw == null) {
                continue;
            }
            String trimmed = raw.trim();
            if (!trimmed.isEmpty()) {
                set.add(trimmed);
            }
        }

        return set.isEmpty() ? null : new ArrayList<>(set);
    }

    /**
     * API List<String> -> DB CSV 변환
     *
     * 정책:
     * - null/blank 제거
     * - 중복 제거(입력 순서 유지)
     * - 결과가 비면 null 저장
     */
    private String joinSkillCsv(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return null;
        }

        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String raw : skills) {
            if (raw == null) {
                continue;
            }
            String trimmed = raw.trim();
            if (!trimmed.isEmpty()) {
                set.add(trimmed);
            }
        }

        return set.isEmpty() ? null : String.join(",", set);
    }

    /**
     * DB CSV -> 응답용 List<String> 변환
     *
     * 정책:
     * - 응답에서는 빈 리스트를 기본값으로 사용한다.
     */
    private List<String> splitSkillCsv(String csv) {
        if (!hasText(csv)) {
            return List.of();
        }

        String[] arr = csv.split(",");
        List<String> out = new ArrayList<>(arr.length);
        for (String raw : arr) {
            String trimmed = raw.trim();
            if (!trimmed.isEmpty()) {
                out.add(trimmed);
            }
        }
        return out;
    }

    /**
     * 작성자 정보 조회
     *
     * 정책:
     * - 목록/상세 응답의 author 필드는 여기서 통일해 채운다.
     * - 현재 포함 필드:
     *   memberId, nickname, departmentName
     */
    private RecruitAuthorResponse getAuthor(Long memberId) {
        return memberReader.getAuthor(memberId);
    }

    /**
     * 지원자 정보 조회
     *
     * 정책:
     * - 지원자 목록/승인·거절 응답의 applicant 필드는 여기서 통일해 채운다.
     * - 현재 포함 필드:
     *   memberId, nickname, contactEmail
     */
    private ApplicationResponse.Applicant getApplicant(Long memberId) {
        return memberReader.getApplicant(memberId);
    }

    /**
     * Application 엔티티 + applicant 조회 결과를 API 응답 DTO로 조립한다.
     */
    private ApplicationResponse toApplicationResponse(
            RecruitApplication app,
            ApplicationResponse.Applicant applicant
    ) {
        return ApplicationResponse.of(
                app.getId(),
                applicant.getMemberId(),
                applicant.getNickname(),
                applicant.getContactEmail(),
                app.getStatus(),
                app.getCreatedAt()
        );
    }

    /**
     * null / blank 공통 체크
     */
    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * 문자열 trim 후 비어 있으면 null 처리
     *
     * 사용처:
     * - 선택값(tag, department 등)의 저장 전 정리
     */
    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}