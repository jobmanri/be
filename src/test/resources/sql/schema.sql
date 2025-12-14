-- Drop tables if they exist to start with a clean slate
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS feeds;
DROP TABLE IF EXISTS communities;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS private_messages;

-- Table for Companies
CREATE TABLE companies
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    career_link VARCHAR(255) NOT NULL,
    logo_url    VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- Table for Users
CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Table for Profiles
CREATE TABLE profiles
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    user_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Table for Communities
CREATE TABLE communities
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    job_group  VARCHAR(255) NOT NULL,
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies (id)
);

-- Table for Feeds
CREATE TABLE feeds
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    community_id BIGINT,
    author_id    BIGINT,
    FOREIGN KEY (community_id) REFERENCES communities (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);

-- Table for Comments
CREATE TABLE comments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    content         TEXT NOT NULL,
    author_id       BIGINT,
    author_nickname VARCHAR(255),
    feed_id         BIGINT,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (feed_id) REFERENCES feeds (id)
);

-- Table for Private Messages
CREATE TABLE private_messages
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id              BIGINT NOT NULL,
    receiver_id            BIGINT NOT NULL,
    content                TEXT   NOT NULL,
    read_at                DATETIME,
    deleted_at_by_sender   DATETIME,
    deleted_at_by_receiver DATETIME,
    created_at             DATETIME DEFAULT CURRENT_TIMESTAMP
);
