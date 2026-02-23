-- --------------------------------------------------------------------------------------
-- DROP EXISTING TABLES (Useful for re-running the script during development)
-- --------------------------------------------------------------------------------------
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;

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
DELETE FROM articles;
DELETE FROM users;

-- --------------------------------------------------------------------------------------
-- 1. USERS TABLE
-- Passwords are set to 'password' using the mathematically verified BCrypt hash.
-- --------------------------------------------------------------------------------------
INSERT INTO users (id, name, user_name, email, password_hash, role) VALUES
('11111111-1111-1111-1111-111111111111', 'Admin User', 'admin_master', 'admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGKKPTOsMy746A/rA.p.', 'ROLE_ADMIN'),
('22222222-2222-2222-2222-222222222222', 'Audience User', 'reader_01', 'audience@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGKKPTOsMy746A/rA.p.', 'ROLE_AUDIENCE');

-- --------------------------------------------------------------------------------------
-- CATEGORIES TABLE
-- Note: We omit the 'id' and 'created_at' columns so Postgres handles them automatically.
-- --------------------------------------------------------------------------------------
INSERT INTO categories (id, name, slug, description) VALUES
('33333333-3333-3333-3333-333333333333','Educational', 'educational', 'Educational resources, guides, and tutorials.'),
('33333333-3333-3333-3333-333333333334','Research', 'research', 'In-depth academic and industry research papers.'),
('33333333-3333-3333-3333-333333333335','Science', 'science', 'Scientific discoveries, theories, and explorations.'),
('33333333-3333-3333-3333-333333333336','Exhibition', 'exhibition', 'Showcases, events, and gallery exhibitions.'),
('33333333-3333-3333-3333-333333333337','Insights', 'insights', 'Industry insights, analytics, and expert opinions.');

-- --------------------------------------------------------------------------------------
-- 2. ARTICLES TABLE
-- Status strictly follows the regex: ^(Open|Working|Published)$
-- Dates use strict ISO-8601 format to map perfectly to LocalDateTime
-- --------------------------------------------------------------------------------------
INSERT INTO articles (id, title, slug, summary, content, status, category_id, author_id, banner_url, youtube_link, pdf_url, published_at, created_at, updated_at) VALUES
(
    'a0000000-0000-0000-0000-000000000001',
    'The Rise of AI in Aviation',
    'rise-of-ai-aviation',
    'How machine learning is optimizing flight paths.',
    'Full article content detailing AI logistics, auto-pilot enhancements, and fuel optimization strategies...',
    'Published',
    '33333333-3333-3333-3333-333333333333',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/ai-aviation.jpg',
    'http://youtube.com/watch?v=ai123',
    NULL,
    '2026-02-15T08:30:00', '2026-02-10T14:00:00', '2026-02-15T08:30:00'
),
(
    'a0000000-0000-0000-0000-000000000002',
    'Top 10 Spring Boot Security Patterns',
    'top-10-spring-boot-security',
    'Best practices for securing REST APIs.',
    'In-depth guide on JWTs, filter chains, CORS, and password hashing in 2026...',
    'Published',
    '33333333-3333-3333-3333-333333333333',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/spring-security.jpg',
    NULL,
    'http://example.com/docs/security-cheatsheet.pdf',
    '2026-02-18T10:15:00', '2026-02-12T09:00:00', '2026-02-18T10:15:00'
),
(
    'a0000000-0000-0000-0000-000000000003',
    'Understanding PostgreSQL JSONB',
    'understanding-postgresql-jsonb',
    'Storing unstructured data in a relational database.',
    'Tutorial on querying and indexing JSONB columns efficiently...',
    'Published',
    '33333333-3333-3333-3333-333333333334',
    '11111111-1111-1111-1111-111111111111',
    NULL,
    'http://youtube.com/watch?v=pg456',
    NULL,
    '2026-02-19T14:45:00', '2026-02-19T10:00:00', '2026-02-19T14:45:00'
),
(
    'a0000000-0000-0000-0000-000000000004',
    'Next-Gen UI with React 19',
    'next-gen-ui-react-19',
    'Exploring the new compiler features.',
    'Content regarding hooks, performance, and server components...',
    'Published',
    '33333333-3333-3333-3333-333333333334',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/react19.jpg',
    NULL,
    NULL,
    '2026-02-20T09:00:00', '2026-02-18T16:20:00', '2026-02-20T09:00:00'
),
(
    'a0000000-0000-0000-0000-000000000005',
    'Mastering Docker Compose',
    'mastering-docker-compose',
    'Running full-stack apps locally.',
    'How to link Postgres, Redis, and Spring Boot containers together...',
    'Working',
    '33333333-3333-3333-3333-333333333335',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/docker.jpg',
    NULL,
    NULL,
    NULL, '2026-02-20T10:00:00', '2026-02-20T11:30:00'
),
(
    'a0000000-0000-0000-0000-000000000006',
    'Aviation Safety Protocols 2026',
    'aviation-safety-protocols-2026',
    'Updated FAA regulations for the new year.',
    'Draft covering the new compliance laws, maintenance schedules, and crew training...',
    'Working',
    '33333333-3333-3333-3333-333333333335',
    '11111111-1111-1111-1111-111111111111',
    NULL,
    NULL,
    'http://example.com/docs/faa-draft.pdf',
    NULL, '2026-02-20T11:00:00', '2026-02-20T12:15:00'
),
(
    'a0000000-0000-0000-0000-000000000007',
    'GraphQL vs REST in Enterprise',
    'graphql-vs-rest-enterprise',
    'Choosing the right API architecture.',
    'Detailed comparison of over-fetching, caching, and strongly typed schemas...',
    'Working',
    '33333333-3333-3333-3333-333333333336',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/api-design.jpg',
    'http://youtube.com/watch?v=api789',
    NULL,
    NULL, '2026-02-20T13:00:00', '2026-02-20T13:00:00'
),
(
    'a0000000-0000-0000-0000-000000000008',
    'The Future of Sustainable Aviation Fuel',
    'sustainable-aviation-fuel',
    'Breaking down the chemistry and cost of SAF.',
    'Outline for an upcoming investigative piece on carbon-neutral flights...',
    'Open',
    '33333333-3333-3333-3333-333333333336',
    '11111111-1111-1111-1111-111111111111',
    NULL,
    NULL,
    NULL,
    NULL, '2026-02-20T14:00:00', '2026-02-20T14:00:00'
),
(
    'a0000000-0000-0000-0000-000000000009',
    'Java 26 Features You Should Know',
    'java-26-features',
    'New syntax and performance updates.',
    'Notes for an upcoming blog post regarding pattern matching and virtual threads...',
    'Open',
    '33333333-3333-3333-3333-333333333337',
    '11111111-1111-1111-1111-111111111111',
    'http://example.com/banners/java26.jpg',
    NULL,
    NULL,
    NULL, '2026-02-20T15:00:00', '2026-02-20T15:00:00'
),
(
    'a0000000-0000-0000-0000-000000000010',
    'Building Microservices with Kubernetes',
    'building-microservices-kubernetes',
    'Deploying Spring Boot to a K8s cluster.',
    'Initial draft covering pods, services, deployments, and ingress controllers...',
    'Open',
    '33333333-3333-3333-3333-333333333337',
    '11111111-1111-1111-1111-111111111111',
    NULL,
    NULL,
    NULL,
    NULL, '2026-02-20T16:00:00', '2026-02-20T16:00:00'
);