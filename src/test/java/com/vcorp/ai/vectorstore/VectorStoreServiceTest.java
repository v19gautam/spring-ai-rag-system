package com.vcorp.ai.vectorstore;

import com.vcorp.ai.chunking.ChunkingOrchestrator;
import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class VectorStoreServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VectorStoreServiceTest.class);

    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Autowired
    private ChunkVectorStoreService chunkVectorStoreService;

    @Test
    public void testStore() throws Exception{
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        List<Chunk> combinedChunks = new ArrayList<>();

        for (IngestedDocument ingestedDocument : ingestedDocuments) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(ingestedDocument);
            combinedChunks.addAll(chunks);
        }
        chunkVectorStoreService.store(combinedChunks);
    }
}
