ALTER TABLE app_user
    DROP CONSTRAINT IF EXISTS fk_app_user_department;

ALTER TABLE app_user
    DROP COLUMN IF EXISTS department_id;

DROP TABLE IF EXISTS department;