CREATE TABLE app_user (
                          user_id BIGSERIAL PRIMARY KEY,
                          provider VARCHAR(20) NOT NULL,
                          provider_sub VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          email_verified BOOLEAN NOT NULL,
                          created_at TIMESTAMP DEFAULT now(),
                          updated_at TIMESTAMP DEFAULT now()
);

ALTER TABLE app_user
    ADD CONSTRAINT uk_app_user_email UNIQUE (email);

ALTER TABLE app_user
    ADD CONSTRAINT uk_app_user_provider_sub UNIQUE (provider, provider_sub);
