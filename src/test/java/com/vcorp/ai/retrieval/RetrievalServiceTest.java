package com.vcorp.ai.retrieval;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RetrievalServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetrievalServiceTest.class);

    @Autowired
    private RetrievalService retrievalService;

    @Test
    public void testRetrieve() {
        RetrievalResult result = retrievalService.retrieve("What is the leave carry forward policy?");
        LOGGER.info("Retrieval result - chunks found: {}", result.getChunks().size());
        for (Chunk chunk : result.getChunks()) {
            LOGGER.info("Metadata: {}", chunk.getMetadata());
            LOGGER.info("Content: {}", chunk.getContent());
        }
    }
}
