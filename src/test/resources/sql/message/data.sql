SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE private_messages;
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

-- Private messages
-- 1) user 1 → user 2 메시지 (삭제되지 않음 → partner 관계 생성)
INSERT INTO private_messages (sender_id, receiver_id, content, read_at, deleted_at_by_sender,
                              deleted_at_by_receiver)
VALUES (1, 2, 'message1', NULL, NULL, NULL)
     , (1, 2, 'message2', NULL, NULL, NULL)
     , (2, 1, 'message3', NULL, NULL, NULL)
     , (1, 3, 'message4', NULL, NULL, NULL);;