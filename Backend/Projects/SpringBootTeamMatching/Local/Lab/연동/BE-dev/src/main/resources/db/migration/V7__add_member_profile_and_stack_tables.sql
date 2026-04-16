ALTER TABLE app_user

    ADD COLUMN phone_number VARCHAR(20);

CREATE TABLE tech_stack (
                            tech_stack_id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_tech_stack (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 tech_stack_id BIGINT NOT NULL,
                                 CONSTRAINT fk_user_tech_stack_user
                                     FOREIGN KEY (user_id)
                                         REFERENCES app_user(user_id)
                                         ON DELETE CASCADE,
                                 CONSTRAINT fk_user_tech_stack_tech_stack
                                     FOREIGN KEY (tech_stack_id)
                                         REFERENCES tech_stack(tech_stack_id)
                                         ON DELETE CASCADE,
                                 CONSTRAINT uk_user_tech_stack_unique UNIQUE (user_id, tech_stack_id)
);

CREATE TABLE user_activity_category (
                                        id BIGSERIAL PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        activity_category VARCHAR(50) NOT NULL,
                                        CONSTRAINT fk_user_activity_category_user
                                            FOREIGN KEY (user_id)
                                                REFERENCES app_user(user_id)
                                                ON DELETE CASCADE,
                                        CONSTRAINT uk_user_activity_category_unique UNIQUE (user_id, activity_category)
);