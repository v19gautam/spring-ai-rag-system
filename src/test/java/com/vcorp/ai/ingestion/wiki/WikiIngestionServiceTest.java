package com.vcorp.ai.ingestion.wiki;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WikiIngestionServiceTest {

    @Test
    public void testIngestWikiFiles() throws Exception {
        WikiIngestionService service = new WikiIngestionService();
        service.ingestWikiFiles();
    }
}
