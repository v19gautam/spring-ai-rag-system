package com.vcorp.ai.ingestion.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PdfIngestionServiceTest {
    @Autowired
    PdfIngestionService pdfIngestionService;

    @Test
    public void testIngestSinglePdf() throws Exception {
//        File pdfFile = new File("src/test/resources/sample.pdf");
        pdfIngestionService.ingestPdfs();
    }
}
