package com.vcorp.ai.embedding;

import com.vcorp.ai.chunking.ChunkingOrchestrator;
import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.embeddings.ChunkEmbeddingService;
import com.vcorp.ai.embeddings.EmbeddedChunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EmbeddingInspectionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddingInspectionTest.class);

    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Autowired
    ChunkEmbeddingService chunkEmbeddingService;

    @Test
    public void testEmbeddingInspection() throws Exception {
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        IngestedDocument ingestedDocument = ingestedDocuments.get(0);
        List<Chunk> chunks = chunkingOrchestrator.chunk(ingestedDocument);
        for (Chunk chunk : chunks) {
            EmbeddedChunk embeddedChunk = chunkEmbeddingService.embedChunk(chunk);
            LOGGER.info("----------Embedding inspection test----------");
            LOGGER.info("Metadata: {}", chunk.getMetadata());
            LOGGER.info("Content: {}", chunk.getContent());
            LOGGER.info("Embedding length: {}", embeddedChunk.getVector().length);
        }
    }
}
