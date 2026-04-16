-- 1) department 테이블 생성 (엔티티 Department에 맞춤)
CREATE TABLE department (
                            department_id BIGSERIAL PRIMARY KEY,
                            department_name VARCHAR(100) NOT NULL
);

-- 2) app_user에 Member 엔티티의 추가 컬럼 반영
ALTER TABLE app_user
    ADD COLUMN nickname VARCHAR(30) NOT NULL DEFAULT '',
    ADD COLUMN role VARCHAR(20),
    ADD COLUMN intro VARCHAR(200),
    ADD COLUMN github_url VARCHAR(255),
    ADD COLUMN profile_image_url VARCHAR(255),
    ADD COLUMN department_id BIGINT;

-- 3) FK(외래키) 추가: app_user.department_id -> department.department_id
ALTER TABLE app_user
    ADD CONSTRAINT fk_app_user_department
        FOREIGN KEY (department_id)
            REFERENCES department(department_id);