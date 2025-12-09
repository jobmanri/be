DELETE FROM feed_comments;
DELETE FROM feeds;
DELETE FROM communities;
DELETE FROM profiles;
DELETE FROM users;
DELETE FROM companies;

INSERT INTO companies (id, name, career_link, logo_url, description)
VALUES (1, 'company', 'company.com', 'logo.png', 'desc');

INSERT INTO users (id, email, password) VALUES
    (1, 'author1@test.com', 'pw'),
    (2, 'author2@test.com', 'pw'),
    (3, 'author3@test.com', 'pw');

INSERT INTO profiles (id, name, nickname, user_id) VALUES
    (1, 'user1', 'nick1', 1),
    (2, 'user2', 'nick2', 2),
    (3, 'user3', 'nick3', 3);

INSERT INTO communities (id, name, job_group, company_id) VALUES
    (1, 'backend', 'dev', 1),
    (2, 'frontend', 'dev', 1);

INSERT INTO feeds (id, title, content, community_id, author_id) VALUES
    (1, 'title1', 'content1', 1, 1),
    (2, 'title2', 'content2', 1, 2),
    (3, 'title3', 'content3', 2, 3);

INSERT INTO feed_comments (id, comment, author_id, feed_id) VALUES
    (1, 'c1', 2, 1),
    (2, 'c2', 3, 1),
    (3, 'c3', 1, 2);
