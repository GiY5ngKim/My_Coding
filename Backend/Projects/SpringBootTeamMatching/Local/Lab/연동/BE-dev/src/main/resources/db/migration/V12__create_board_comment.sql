CREATE TABLE board_comment (
                               board_comment_id BIGSERIAL PRIMARY KEY,
                               board_post_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               content TEXT NOT NULL,
                               created_at TIMESTAMP NOT NULL DEFAULT now()
);