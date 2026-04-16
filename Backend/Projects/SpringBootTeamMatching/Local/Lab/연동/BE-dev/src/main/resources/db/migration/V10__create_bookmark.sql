-- user_bookmark 테이블 생성

CREATE TABLE user_bookmark (
                               id BIGSERIAL PRIMARY KEY,

                               user_id BIGINT NOT NULL,
                               target_user_id BIGINT NOT NULL,

                               CONSTRAINT fk_user_bookmark_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES app_user(user_id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_user_bookmark_target
                                   FOREIGN KEY (target_user_id)
                                       REFERENCES app_user(user_id)
                                       ON DELETE CASCADE,

                               CONSTRAINT uk_user_target
                                   UNIQUE (user_id, target_user_id)
);