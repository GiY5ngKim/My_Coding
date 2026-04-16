CREATE TABLE board_post_image (
                                  board_post_image_id BIGSERIAL PRIMARY KEY,
                                  post_id BIGINT NOT NULL,
                                  image_url TEXT NOT NULL
);