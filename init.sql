-- ========== EXTENSIONS ==========
CREATE EXTENSION IF NOT EXISTS vector;

-- ========== KNOWLEDGE BASE TABLES ==========

-- FAQs table
CREATE TABLE IF NOT EXISTS faqs
(
    id         SERIAL PRIMARY KEY,
    question   TEXT NOT NULL,
    answer     TEXT NOT NULL,
    department VARCHAR(50),
    visibility VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Release notes table
CREATE TABLE IF NOT EXISTS release_notes
(
    id           SERIAL PRIMARY KEY,
    version      VARCHAR(20),
    summary      TEXT,
    details      TEXT,
    release_date DATE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Announcements (Email-style communications)
CREATE TABLE IF NOT EXISTS announcements
(
    id             SERIAL PRIMARY KEY,
    subject        TEXT NOT NULL,
    body           TEXT NOT NULL,
    category       VARCHAR(50),
    effective_from DATE,
    effective_to   DATE,
    source_type    VARCHAR(20),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========== RAG/EMBEDDING TABLES ==========

-- Table for storing embeddings of knowledge base content
CREATE TABLE IF NOT EXISTS knowledge_embeddings
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    source_type VARCHAR(50) NOT NULL, -- 'faq', 'release_note', 'announcement'
    source_id   INT         NOT NULL, -- References the actual table ID
    chunk_text  TEXT        NOT NULL, -- Chunked content from the source
    embedding vector(1536),           -- OpenAI embedding (1536 dimensions)
    metadata JSONB,                   -- Additional context (title, department, etc.)
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for fast vector similarity search
CREATE INDEX IF NOT EXISTS idx_knowledge_embeddings_vector
    ON knowledge_embeddings
    USING ivfflat (embedding vector_cosine_ops);

CREATE INDEX IF NOT EXISTS idx_knowledge_embeddings_source
    ON knowledge_embeddings(source_type, source_id);

CREATE INDEX IF NOT EXISTS idx_knowledge_embeddings_metadata
    ON knowledge_embeddings USING gin(metadata);