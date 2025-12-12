-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS private_messages;
DROP TABLE IF EXISTS feed_comments;
DROP TABLE IF EXISTS feeds;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS communities;
DROP TABLE IF EXISTS companies;

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
    company_id BIGINT,
    CONSTRAINT fk_communities_company FOREIGN KEY (company_id) REFERENCES companies (id)
);

-- Create profiles table (without foreign key initially due to circular reference)
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
    profile_id BIGINT       NOT NULL,
    CONSTRAINT fk_users_profile FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

-- Add foreign key constraint to profiles table after users table is created
ALTER TABLE profiles
    ADD CONSTRAINT fk_profiles_user FOREIGN KEY (user_id) REFERENCES users (id);

-- Create feeds table
CREATE TABLE feeds
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    community_id BIGINT,
    author_id    BIGINT,
    CONSTRAINT fk_feeds_community FOREIGN KEY (community_id) REFERENCES communities (id),
    CONSTRAINT fk_feeds_author FOREIGN KEY (author_id) REFERENCES users (id)
);

-- Create feed_comments table
CREATE TABLE feed_comments
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment   VARCHAR(1000) NOT NULL,
    author_id BIGINT,
    feed_id   BIGINT,
    CONSTRAINT fk_feed_comments_author FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT fk_feed_comments_feed FOREIGN KEY (feed_id) REFERENCES feeds (id)
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
    created_at             DATETIME      NOT NULL,
    CONSTRAINT fk_private_messages_sender FOREIGN KEY (sender_id) REFERENCES users (id),
    CONSTRAINT fk_private_messages_receiver FOREIGN KEY (receiver_id) REFERENCES users (id)
);

-- Create indexes for better query performance
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_feeds_author ON feeds (author_id);
CREATE INDEX idx_feeds_community ON feeds (community_id);
CREATE INDEX idx_feed_comments_feed ON feed_comments (feed_id);
CREATE INDEX idx_feed_comments_author ON feed_comments (author_id);
CREATE INDEX idx_private_messages_sender ON private_messages (sender_id);
CREATE INDEX idx_private_messages_receiver ON private_messages (receiver_id);
CREATE INDEX idx_communities_company ON communities (company_id);
