DROP TABLE IF EXISTS users;


-- --------------------------------------------------------------------------------------
-- CREATE TABLES
-- --------------------------------------------------------------------------------------

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50)
);

CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    slug VARCHAR(120) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE articles (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    summary VARCHAR(255),
    content VARCHAR(12000) NOT NULL,
    status VARCHAR(50) NOT NULL,
    category_id UUID NOT NULL,
    author_id UUID NOT NULL,
    banner_url VARCHAR(255),
    youtube_link VARCHAR(255),
    pdf_url VARCHAR(255),
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_article_author FOREIGN KEY (author_id) REFERENCES users(id)
);

-- --------------------------------------------------------------------------------------
-- CLEAR EXISTING DATA (To prevent duplicate key errors on reload)
-- --------------------------------------------------------------------------------------
DELETE FROM users;

-- --------------------------------------------------------------------------------------
-- 1. USERS TABLE
-- Passwords are set to 'password' using the mathematically verified BCrypt hash.
-- --------------------------------------------------------------------------------------
INSERT INTO users (id, name, user_name, email, password_hash, role) VALUES
('11111111-1111-1111-1111-111111111111', 'Admin User', 'admin_master', 'admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGKKPTOsMy746A/rA.p.', 'ROLE_ADMIN'),
('22222222-2222-2222-2222-222222222222', 'Audience User', 'reader_01', 'audience@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGKKPTOsMy746A/rA.p.', 'ROLE_AUDIENCE');


