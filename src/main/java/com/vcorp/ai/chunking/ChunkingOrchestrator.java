package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChunkingOrchestrator {

    private final WikiSemanticChunker wikiSemanticChunker;
    private final DatabaseChunker databaseChunker;
    private final PdfPragmaticChunker pdfPragmaticChunker;

    public List<Chunk> chunk(IngestedDocument ingestedDocument) throws Exception {
        return switch (ingestedDocument.getSource()) {
            case "WIKI" -> wikiSemanticChunker.chunk(ingestedDocument);
            case "DB" -> databaseChunker.chunk(ingestedDocument);
            case "PDF" -> pdfPragmaticChunker.chunk(ingestedDocument);
            default -> throw new IllegalArgumentException("Invalid source type: " + ingestedDocument.getSource());
        };
    }
}
