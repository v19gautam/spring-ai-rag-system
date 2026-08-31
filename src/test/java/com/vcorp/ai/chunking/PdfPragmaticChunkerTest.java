package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PdfPragmaticChunkerTest {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PdfPragmaticChunkerTest.class);

    @Autowired
    IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private PdfPragmaticChunker pdfPragmaticChunker;

    @Test
    public void testPdfPragmaticChunker() throws Exception{
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();

        IngestedDocument ingestedDocument = ingestedDocuments.stream()
                .filter(d -> d.getSource().contains("PDF"))
                .findFirst()
                .orElseThrow();

        List<Chunk> chunks = pdfPragmaticChunker.chunk(ingestedDocument);
        printChunks(ingestedDocument, chunks);
    }

    private static void printChunks(IngestedDocument ingestedDocument, List<Chunk> chunks) {
        LOGGER.info("-------------");
        LOGGER.info("Source: {}", ingestedDocument.getSource());
        LOGGER.info("Total chunks: {}", chunks.size());
        LOGGER.info("-------------");

        LOGGER.info("Chunks:");
        for (Chunk chunk : chunks) {
            LOGGER.info("Chunk: {}", chunk.getChunkIndex());
            LOGGER.info("Metadata: {}", chunk.getMetadata());
            LOGGER.info(chunk.getContent());
        }
        LOGGER.info("-------------");
    }
}
