package com.vcorp.ai.ingestion;

import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IngestionOrchestratorTest {

    @Autowired
    IngestionOrchestrator ingestionOrchestrator;
    @Test
    public void testIngestAll() throws Exception {
        List<IngestedDocument> ingestedDocuments = ingestionOrchestrator.ingestAll();
        System.out.println("Total ingested documents: " + ingestedDocuments.size());
        System.out.println("--------------");
        for (IngestedDocument ingestedDocument : ingestedDocuments) {
            System.out.println("Source: " + ingestedDocument.getSource());
            System.out.println("Content: " + ingestedDocument.getContent());
            System.out.println("--------------");
        }
    }
}
