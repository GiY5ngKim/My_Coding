-- V4__create_recruit_post.sql
-- recruit_post: 모집글 (API 기준)
-- 정책:
-- - required_skills는 CSV 문자열(VARCHAR). API에서는 List<String>로 받고 서버가 join/split
-- - applicant_count는 "작성자 포함 현재 참여 인원" (기본 1)
--   = 1(작성자) + ACCEPTED 인원 수
-- - applicant_count == total_headcount 도달 시 서비스에서 status를 CLOSED로 자동 전환
-- - deadline이 지나면 서비스에서 행위 차단(400). (스케줄러/배치는 선택)
-- NOTE: updated_at은 DB 트리거 없이 JPA(@PreUpdate/@PrePersist)에서 갱신한다.

CREATE TABLE IF NOT EXISTS recruit_post (
    recruit_post_id    BIGSERIAL PRIMARY KEY,
    author_user_id     BIGINT NOT NULL,

    type               VARCHAR(50) NOT NULL,
    category           VARCHAR(50) NOT NULL,
    tag                VARCHAR(50),

    title              VARCHAR(120) NOT NULL,
    content            TEXT NOT NULL,

    required_skills    VARCHAR(255),

    status             VARCHAR(20) NOT NULL DEFAULT 'OPEN',

    total_headcount    SMALLINT NOT NULL,
    applicant_count    SMALLINT NOT NULL DEFAULT 1,

    deadline           DATE,

    created_at         TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 조회 성능용(필수급)
CREATE INDEX IF NOT EXISTS idx_recruit_post_author_user_id
    ON recruit_post(author_user_id);

CREATE INDEX IF NOT EXISTS idx_recruit_post_status
    ON recruit_post(status);

CREATE INDEX IF NOT EXISTS idx_recruit_post_type
    ON recruit_post(type);

CREATE INDEX IF NOT EXISTS idx_recruit_post_category
    ON recruit_post(category);

-- keyword 검색(간단용)
CREATE INDEX IF NOT EXISTS idx_recruit_post_title
    ON recruit_post(title);

-- deadline 조회/배치용
CREATE INDEX IF NOT EXISTS idx_recruit_post_deadline
    ON recruit_post(deadline);

-- 상태 값 유효성(문자열이지만 최소 방어)
ALTER TABLE recruit_post
    ADD CONSTRAINT ck_recruit_post_status
    CHECK (status IN ('OPEN', 'CLOSED'));

-- headcount 최소 1
ALTER TABLE recruit_post
    ADD CONSTRAINT ck_recruit_post_total_headcount_min
    CHECK (total_headcount >= 1);

-- applicant_count 최소 1(작성자 포함), 그리고 total_headcount 초과 금지
ALTER TABLE recruit_post
    ADD CONSTRAINT ck_recruit_post_applicant_count_range
    CHECK (applicant_count >= 1 AND applicant_count <= total_headcount);