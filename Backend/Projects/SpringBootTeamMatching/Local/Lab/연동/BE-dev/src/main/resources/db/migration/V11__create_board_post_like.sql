CREATE TABLE board_post_like (
                                 board_post_like_id BIGSERIAL PRIMARY KEY,
                                 board_post_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT now(),

                                 CONSTRAINT uk_board_post_like UNIQUE (board_post_id, user_id)
);