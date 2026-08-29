package com.vcorp.ai.ingestion.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class PdfIngestionService {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(PdfIngestionService.class);
    public static final String PDF_DIRECTORY = "data/pdfs";

    public void ingestPdf() throws Exception{
        File[] pdfFiles = new File(PDF_DIRECTORY).listFiles();
        for (File pdfFile : pdfFiles) {
            ingestSinglePdf(pdfFile);
        }
    }

    private void ingestSinglePdf(File pdfFile) throws IOException {
        log.info("Ingesting PDF file: {}", pdfFile.getName());

        try (PDDocument pdfDocument = PDDocument.load(pdfFile)){
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdfDocument);
            log.info("Extracted text from PDF: {}", pdfFile.getName());
            log.info(text);
        }
    }
}
