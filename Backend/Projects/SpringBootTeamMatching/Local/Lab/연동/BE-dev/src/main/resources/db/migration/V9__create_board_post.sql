CREATE TABLE IF NOT EXISTS board_post (
    board_post_id BIGSERIAL PRIMARY KEY,
    author_user_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_board_post_author
        FOREIGN KEY (author_user_id) REFERENCES app_user(user_id)
);

CREATE INDEX IF NOT EXISTS idx_board_post_author_user_id
    ON board_post(author_user_id);

CREATE INDEX IF NOT EXISTS idx_board_post_created_at
    ON board_post(created_at DESC);