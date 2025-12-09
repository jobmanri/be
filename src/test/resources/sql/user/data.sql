SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE profiles;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;

-- Users
INSERT INTO users (id, email, password)
VALUES (1, 'u1@test.com', 'pw'),
       (2, 'u2@test.com', 'pw'),
       (3, 'u3@test.com', 'pw');

-- Profiles (JOIN 의 필수 조건: p.user_id = partner_id)
INSERT INTO profiles (id, name, nickname, user_id)
VALUES (1, 'User One', 'u1', 1),
       (2, 'User Two', 'u2', 2),
       (3, 'User Three', 'u3', 3);