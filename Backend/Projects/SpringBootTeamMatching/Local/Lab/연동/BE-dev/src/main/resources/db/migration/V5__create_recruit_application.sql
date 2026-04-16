-- V5__create_recruit_application.sql
-- recruit_application: 모집글 신청
-- 정책:
-- - 취소는 row 삭제
-- - 중복 지원은 UNIQUE(recruit_post_id, user_id)
-- - status: APPLIED / ACCEPTED / REJECTED
-- - CLOSED 또는 deadline 지난 경우 apply/cancel/decide 불가(서비스에서 400)

CREATE TABLE IF NOT EXISTS recruit_application (
    application_id     BIGSERIAL PRIMARY KEY,

    recruit_post_id    BIGINT NOT NULL,
    user_id            BIGINT NOT NULL,

    status             VARCHAR(20) NOT NULL DEFAULT 'APPLIED',

    created_at         TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_recruit_application_post
        FOREIGN KEY (recruit_post_id)
        REFERENCES recruit_post(recruit_post_id)
        ON DELETE CASCADE
);

-- 중복 지원 방지(핵심)
ALTER TABLE recruit_application
    ADD CONSTRAINT uk_recruit_application_post_user
    UNIQUE (recruit_post_id, user_id);

-- 지원 상태 값 유효성
ALTER TABLE recruit_application
    ADD CONSTRAINT ck_recruit_application_status
    CHECK (status IN ('APPLIED', 'ACCEPTED', 'REJECTED'));

-- 작성자 전용 목록/페이지 조회 최적화
CREATE INDEX IF NOT EXISTS idx_recruit_application_post_created_at
    ON recruit_application(recruit_post_id, created_at DESC);

CREATE INDEX IF NOT EXISTS idx_recruit_application_user_id
    ON recruit_application(user_id);