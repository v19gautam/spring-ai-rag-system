package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FixedSizeChunkerTest {
    @Autowired
    IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    FixedSizeChunker chunker;

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedSizeChunkerTest.class);

    @Test
    public void testChunking() throws Exception {
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        IngestedDocument ingestedDocument = ingestedDocuments.get(0);

        List<Chunk> chunks = chunker.chunk(ingestedDocument, 500);
        LOGGER.info("-------------");
        LOGGER.info("Source: {}", ingestedDocument.getSource());
        LOGGER.info("Original length: {}", ingestedDocument.getContent().length());
        LOGGER.info("Total chunks: {}", chunks.size());
        LOGGER.info("-------------");

        LOGGER.info("Chunks:");
        for (Chunk chunk : chunks) {
            LOGGER.info("Chunk: {}", chunk.getChunkIndex());
            LOGGER.info(chunk.getContent());
        }
        LOGGER.info("-------------");
    }
}
