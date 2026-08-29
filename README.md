# VCORP AI – Spring AI RAG System

A production-grade **Retrieval-Augmented Generation (RAG)** system built with Spring Boot that enables semantic search and intelligent retrieval over a knowledge base using vector embeddings.

## Overview

This project demonstrates a complete RAG pipeline integrated with a Spring Boot backend, showcasing how to build AI-powered applications that ground LLM responses with factual, searchable knowledge bases. The system enables natural language queries to retrieve relevant information with source attribution.

## Features

- **Vector Embeddings**: Automatic embedding generation using OpenAI's text-embedding-3-small model
- **Semantic Search**: Fast similarity search over knowledge base using pgvector and cosine distance
- **LLM Integration**: GPT-4 powered responses grounded in retrieved context (no hallucinations)
- **Source Attribution**: Retrieval results include original source references and metadata
- **Production Ready**: Containerized with Docker, includes connection pooling, transaction management
- **Knowledge Base Support**: Ingest and embed FAQs, release notes, announcements, and custom documents

## Tech Stack

- **Backend**: Spring Boot 4.1.0, Spring Data JPA, Hibernate ORM
- **AI/ML**: Spring AI 2.0.0, OpenAI API
- **Database**: PostgreSQL 15 + pgvector extension for vector storage
- **Infrastructure**: Docker, docker-compose, Maven
- **Testing**: JUnit 5, Spring Boot Test

## Architecture

```
Spring Boot Application
    ↓
[Knowledge Embedding Service] → Chunks & embeds knowledge base content
    ↓
PostgreSQL + pgvector
    ├─ knowledge_embeddings table (vectors + metadata)
    └─ knowledge base tables (FAQs, announcements, etc.)
    ↓
[Knowledge Assistant Service] → Semantic search + LLM orchestration
    ↓
OpenAI API (embeddings + chat completion)
    ↓
REST API Endpoint: /api/knowledge/search
```

## Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17+
- Maven 3.9+
- OpenAI API key

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/spring-ai-rag-system.git
   cd spring-ai-rag-system
   ```

2. **Configure environment**
   ```bash
   cp .env.example .env
   # Edit .env with your OpenAI API key
   export OPENAI_API_KEY="sk-your-key-here"
   ```

3. **Build the application**
   ```bash
   mvn clean package -DskipTests
   ```

4. **Start with Docker**
   ```bash
   docker-compose up --build
   ```

5. **Verify setup**
   ```bash
   curl http://localhost:8080/api/knowledge/search \
     -X POST \
     -H "Content-Type: application/json" \
     -d '{"query":"How do I reset my VPN password?"}'
   ```

## API Endpoints

### Semantic Knowledge Search
```
POST /api/knowledge/search

Request:
{
  "query": "What are the latest security updates?"
}

Response:
{
  "answer": "Based on your knowledge base, the latest security updates...",
  "sources": [
    {
      "sourceType": "release_note",
      "sourceId": 4,
      "title": "Security patch release",
      "relevanceScore": 0.92
    }
  ]
}
```

## Development

### Running Tests
```bash
mvn test
```

### Database Connection (IntelliJ)
- Host: localhost
- Port: 5433
- Database: vcorp
- User: vcorpuser
- Password: vcorp123secure

### IntelliJ Database Tool
Right-click database → New → Query Console to execute SQL against the running Docker PostgreSQL instance.

## Project Structure

```
spring-ai-rag-system/
├── src/main/java/com/vcorp/ai/
│   ├── entity/
│   │   ├── KnowledgeEmbedding.java
│   │   └── Faq.java
│   ├── service/
│   │   ├── KnowledgeEmbeddingService.java
│   │   └── KnowledgeAssistantService.java
│   ├── repository/
│   │   └── KnowledgeEmbeddingRepository.java
│   ├── controller/
│   │   └── KnowledgeSearchController.java
│   └── VcorpAiBackendApplication.java
├── src/main/resources/
│   └── application.properties
├── Dockerfile
├── docker-compose.yml
├── init.sql
├── pom.xml
└── README.md
```

## How RAG Works

1. **Ingestion**: Knowledge base documents are chunked and embedded into 1536-dimensional vectors
2. **Storage**: Vectors stored in PostgreSQL pgvector extension with metadata
3. **Retrieval**: User query → embedded → semantic similarity search (cosine distance)
4. **Grounding**: Top-K relevant chunks → passed to LLM as context
5. **Generation**: LLM generates response using only provided context (reduces hallucinations)
6. **Attribution**: Original sources returned with confidence scores

## Configuration

### `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/vcorp
spring.datasource.username=vcorpuser
spring.datasource.password=vcorp123secure
spring.ai.openai.api-key=${SPRING_AI_OPENAI_API_KEY}
spring.ai.openai.embedding.options.model=text-embedding-3-small
spring.ai.openai.chat.options.model=gpt-4-mini
```

## Performance Considerations

- **Vector Index**: IVFFlat index optimized for cosine similarity search
- **Connection Pooling**: HikariCP with configurable pool size (default: 10 max connections)
- **Lazy Fetching**: Optimized JPA queries to prevent N+1 problems
- **Caching**: Spring cache abstraction ready for embedding cache

## Future Enhancements

- [ ] Document chunk overlap for context continuity
- [ ] Hybrid search (keyword + semantic)
- [ ] Multi-model support (Claude, Llama, etc.)
- [ ] Reranking for retrieval quality
- [ ] Conversation memory for multi-turn QA
- [ ] Admin dashboard for knowledge base management
- [ ] Streaming responses for real-time chat

## Learning Resources

This project is built following:
- Spring AI official documentation
- OpenAI API best practices
- PostgreSQL pgvector documentation
- RAG architecture patterns from industry leaders

## Contributing

Feel free to fork, improve, and submit PRs!

## License

MIT License - see LICENSE file for details

## Author

Built as a portfolio project demonstrating:
- Production-grade Spring Boot development
- Vector database integration (pgvector)
- LLM/AI application architecture
- Docker containerization & DevOps
- Backend engineering best practices

---

**Questions?** Open an issue or reach out!