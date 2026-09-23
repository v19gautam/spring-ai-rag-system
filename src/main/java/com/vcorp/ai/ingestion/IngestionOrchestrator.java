package com.vcorp.ai.ingestion;

import com.vcorp.ai.ingestion.db.DatabaseIngestionService;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import com.vcorp.ai.ingestion.pdf.PdfIngestionService;
import com.vcorp.ai.ingestion.wiki.WikiIngestionService;
import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import com.vcorp.ai.lifecycle.model.SourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestionOrchestrator {

    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingest(KnowledgeRequest knowledgeRequest) throws Exception {
        SourceType source = knowledgeRequest.getSourceType();
        if (source.equals(SourceType.PDF)) {
            return pdfIngestionService.ingest(knowledgeRequest.getName());
        }
        if (source.equals(SourceType.WIKI)) {
            return wikiIngestionService.ingest(knowledgeRequest.getName());
        }
        if (source.equals(SourceType.DATABASE)) {
            return databaseIngestionService.ingest(knowledgeRequest.getName());
        }
        return Collections.emptyList();
    }

    public List<IngestedDocument> ingestAll() throws Exception {
        List<IngestedDocument> ingestedDocuments = pdfIngestionService.ingestPdfs();
        ingestedDocuments.addAll(wikiIngestionService.ingestWikiFiles());
        ingestedDocuments.addAll(databaseIngestionService.ingestDatabaseContent());
        return ingestedDocuments;
    }
}
