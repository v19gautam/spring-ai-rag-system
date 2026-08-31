package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfPragmaticChunker {

    private static final int DEFAULT_CHUNK_SIZE = 500;
    private static final int DEFAULT_OVERLAP_SIZE = 100;

    private final FixedSizeChunker fixedSizeChunker;

    public List<Chunk> chunk(IngestedDocument document) {

        List<Chunk> chunks = fixedSizeChunker.chunk(document, DEFAULT_CHUNK_SIZE, DEFAULT_OVERLAP_SIZE);
        return chunks.stream()
                .map(this::enrichPdfMetadata)
                .collect(Collectors.toList());
    }

    private Chunk enrichPdfMetadata(Chunk chunk) {
        Map<String, Object> metadata = new HashMap<>(chunk.getMetadata());
        metadata.put("sourceType", "PDF");
        metadata.put("chunkStrategy", "PDF_PRAGMATIC_FIXED_SIZE");
        metadata.put("chunkSize", DEFAULT_CHUNK_SIZE);
        metadata.put("overlap", DEFAULT_OVERLAP_SIZE);
        return new Chunk(chunk.getSource(), chunk.getContent(), metadata, chunk.getChunkIndex());
    }
}
