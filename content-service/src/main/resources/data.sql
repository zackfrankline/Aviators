-- Drop tables in reverse order of dependencies to avoid foreign key errors (if you add them later)
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS categories;

-- 1. Create Categories Table
CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP
);

-- 2. Create Articles Table
CREATE TABLE articles (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    summary VARCHAR(255),
    content VARCHAR(12000) NOT NULL,
    status VARCHAR(50) NOT NULL,
    category_id UUID,
    author_id UUID NOT NULL,
    banner_url VARCHAR(255),
    youtube_link VARCHAR(255),
    pdf_url VARCHAR(255),
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- =====================================================================================
-- 1. INSERT CATEGORIES
-- We use fixed UUIDs here so we can reference them as Foreign Keys in the articles below.
-- =====================================================================================

INSERT INTO categories (id, name, slug, description, created_at)
VALUES
    ('c1111111-1111-1111-1111-111111111111', 'Propulsion Engine', 'propulsion-engine', 'All about aerospace propulsion systems and mechanics.', CURRENT_TIMESTAMP),
    ('c2222222-2222-2222-2222-222222222222', 'Software Architecture', 'software-architecture', 'Deep dives into microservices, Docker, and Spring Boot.', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;
-- 'ON CONFLICT DO NOTHING' prevents errors if you restart the container without wiping volumes


-- =====================================================================================
-- 2. INSERT ARTICLES
-- =====================================================================================

-- Article 1: Published (Linked to Category 1)
INSERT INTO articles (
    id, title, slug, summary, content, status, category_id, author_id, banner_url, youtube_link, pdf_url, published_at, created_at, updated_at
) VALUES (
    'a1111111-1111-1111-1111-111111111111',
    'The Future of Ion Thrusters',
    'future-of-ion-thrusters',
    'A brief look into deep space propulsion efficiency.',
    'This is the full 12,000 character content of the article regarding Ion Thrusters...',
    'Published',
    'c1111111-1111-1111-1111-111111111111', -- Matches "Propulsion Engine" category ID
    '11111111-1111-1111-1111-111111111111', -- Dummy User ID (representing a user in jwt-service)
    'https://example.com/images/ion-thruster.jpg',
    'https://youtube.com/watch?v=example1',
    null,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;

-- Article 2: Working/Draft (Linked to Category 2)
INSERT INTO articles (
    id, title, slug, summary, content, status, category_id, author_id, banner_url, youtube_link, pdf_url, published_at, created_at, updated_at
) VALUES (
    'a2222222-2222-2222-2222-222222222222',
    'Microservices with Spring Boot',
    'microservices-spring-boot',
    'How to decouple your monolithic application.',
    'This is the full content explaining JWTs and stateless authentication...',
    'Working',
    'c2222222-2222-2222-2222-222222222222', -- Matches "Software Architecture" category ID
    '11111111-1111-1111-1111-111111111111', -- Dummy User ID
    null,
    null,
    null,
    null, -- published_at is null because status is 'Working'
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;