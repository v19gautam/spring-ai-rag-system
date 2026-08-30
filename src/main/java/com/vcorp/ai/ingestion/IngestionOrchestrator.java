package com.vcorp.ai.ingestion;

import com.vcorp.ai.ingestion.db.DatabaseIngestionService;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import com.vcorp.ai.ingestion.pdf.PdfIngestionService;
import com.vcorp.ai.ingestion.wiki.WikiIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestionOrchestrator {

    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingestAll() throws Exception {
        List<IngestedDocument> ingestedDocuments = pdfIngestionService.ingestPdfs();
        ingestedDocuments.addAll(wikiIngestionService.ingestWikiFiles());
        ingestedDocuments.addAll(databaseIngestionService.ingestDatabaseContent());
        return ingestedDocuments;
    }
}
