package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
public class DatabaseChunkerTest {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DatabaseChunkerTest.class);

    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private DatabaseChunker databaseChunker;

    @Test
    public void testDatabaseChunker() throws Exception {
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        List<IngestedDocument> dbDocuments = ingestedDocuments.stream()
                .filter(d -> d.getSource().contains("DB"))
                .toList();

        for (IngestedDocument ingestedDocument : dbDocuments) {
            List<Chunk> chunks = databaseChunker.chunk(ingestedDocument);
            Chunk chunk = chunks.get(0);
            logger.info("-----------Database Chunk----------");
            logger.info("Source: {}", chunk.getSource());
            logger.info("Chunk Index: {}", chunk.getChunkIndex());
            logger.info("Metadata: {}", chunk.getMetadata());
            logger.info("Content: {}", chunk.getContent());
        }
    }
}
