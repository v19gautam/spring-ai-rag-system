package com.vcorp.ai.ingestion.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseIngestionServiceTest {

    @Autowired
    private DatabaseIngestionService service;

    @Test
    public void testDatabaseIngestionService() {
        service.ingestFaqs();
        service.ingestReleaseNotes();
        service.ingestAnnouncements();
    }
}
