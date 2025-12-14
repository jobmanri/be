-- Create companies table
CREATE TABLE companies
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    career_link VARCHAR(500) NOT NULL,
    logo_url    VARCHAR(500) NOT NULL,
    description TEXT         NOT NULL
);

-- Create communities table
CREATE TABLE communities
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    job_group  VARCHAR(255) NOT NULL,
    company_id BIGINT
);

-- Create profiles table
CREATE TABLE profiles
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    user_id  BIGINT
);

-- Create users table
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50) DEFAULT 'NORMAL',
    profile_id BIGINT       NOT NULL
);

-- Create feeds table
CREATE TABLE feeds
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    community_id BIGINT       NOT NULL,
    author_id    BIGINT       NOT NULL
);

-- Create comments table
CREATE TABLE comments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    feed_id         BIGINT        NOT NULL,
    author_id       BIGINT        NOT NULL,
    author_nickname VARCHAR(255)  NOT NULL,
    content         VARCHAR(1000) NOT NULL
);

-- Create private_messages table
CREATE TABLE private_messages
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id              BIGINT        NOT NULL,
    receiver_id            BIGINT        NOT NULL,
    content                VARCHAR(1000) NOT NULL,
    read_at                DATETIME,
    deleted_at_by_sender   DATETIME,
    deleted_at_by_receiver DATETIME,
    created_at             DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at             DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

-- Create indexes for better query performance
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_feeds_author ON feeds (author_id);
CREATE INDEX idx_feeds_community ON feeds (community_id);
CREATE INDEX idx_comments_feed ON comments (feed_id);
CREATE INDEX idx_comments_author ON comments (author_id);
CREATE INDEX idx_private_messages_sender ON private_messages (sender_id);
CREATE INDEX idx_private_messages_receiver ON private_messages (receiver_id);
CREATE INDEX idx_communities_company ON communities (company_id);
