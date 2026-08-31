package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ChunkingOrchestratorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkingOrchestratorTest.class);

    @Autowired
    IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Test
    public void testChunkingOrchestrator() throws Exception{
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        for (IngestedDocument ingestedDocument : ingestedDocuments) {
            List<Chunk> chunks = chunkingOrchestrator.chunkAll(ingestedDocument);

            LOGGER.info("SOURCE: {}", ingestedDocument.getSource());
            LOGGER.info("CHUNKS: {}", chunks.size());

            for (Chunk chunk : chunks) {
                LOGGER.info("CHUNK INDEX: {}", chunk.getChunkIndex());
                LOGGER.info("METADATA: {}", chunk.getMetadata());
                LOGGER.info("CONTENT: {}", chunk.getContent());
            }
        }
    }
}
