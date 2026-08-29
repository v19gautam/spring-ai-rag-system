package com.vcorp.ai.ingestion.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class PdfIngestionServiceTest {
    @Autowired
    PdfIngestionService pdfIngestionService;

    @Test
    public void testIngestSinglePdf() throws Exception {
//        File pdfFile = new File("src/test/resources/sample.pdf");
        pdfIngestionService.ingestPdf();
    }
}
