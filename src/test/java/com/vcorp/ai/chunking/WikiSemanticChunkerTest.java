package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.chunking.model.WikiSemanticChunker;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WikiSemanticChunkerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiSemanticChunkerTest.class);

    @Autowired
    IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    FixedSizeChunker fixedSizeChunker;

    @Autowired
    WikiSemanticChunker wikiSemanticChunker;

    @Test
    public void testWikiSemanticChunker() throws Exception {
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        //Filter and get the first WIKI doc
        IngestedDocument wikiDoc = ingestedDocuments.stream().
                filter(d -> d.getSource().contains("WIKI"))
                .findFirst().orElseThrow();

        LOGGER.info("-----Fixed Size Chunker-----");
        List<Chunk> chunks = fixedSizeChunker.chunk(wikiDoc, 500, 100);
        printChunks(wikiDoc, chunks);

        LOGGER.info("-----Wiki Semantic Chunker-----");
        List<Chunk> wikiChunks = wikiSemanticChunker.chunk(wikiDoc);
        printChunks(wikiDoc, wikiChunks);
    }

    private static void printChunks(IngestedDocument ingestedDocument, List<Chunk> chunks) {
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
