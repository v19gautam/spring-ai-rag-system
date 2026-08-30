package com.vcorp.ai.ingestion.pdf;

import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PdfIngestionService {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(PdfIngestionService.class);
    public static final String PDF_DIRECTORY = "data/pdfs";

    public List<IngestedDocument> ingestPdfs() throws Exception{
        File[] pdfFiles = new File(PDF_DIRECTORY).listFiles();
        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        for (File pdfFile : pdfFiles) {
            ingestedDocuments.add(ingestSinglePdf(pdfFile));
        }
        return ingestedDocuments;
    }

    private IngestedDocument ingestSinglePdf(File pdfFile) throws IOException {
        log.info("Ingesting PDF file: {}", pdfFile.getName());

        try (PDDocument pdfDocument = PDDocument.load(pdfFile)){
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdfDocument);
            log.info("Extracted text from PDF: {}", pdfFile.getName());
            log.info(text);

            return new IngestedDocument("PDF", text, Map.of("fileName", pdfFile.getName()));
        }
    }
}
