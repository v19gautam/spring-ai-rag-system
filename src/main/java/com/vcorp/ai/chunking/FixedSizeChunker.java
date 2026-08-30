package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FixedSizeChunker {

    public List<Chunk> chunk(IngestedDocument ingestedDocument, int chunkSize) {
        return chunk(ingestedDocument, chunkSize, 0);
    }
    public List<Chunk> chunk(IngestedDocument ingestedDocument, int chunkSize, int overlap) {
        List<Chunk> chunks = new ArrayList<>();
        String content = ingestedDocument.getContent();
        int start = 0, chunkIndex = 0;
        while (start < content.length()) {
            String chunkContent = content.substring(start, Math.min(start + chunkSize, content.length()));

            Map<String, Object> metadata = new HashMap<>(ingestedDocument.getMetadata());
            metadata.put("chunk_index", chunkIndex);
            Chunk chunk = new Chunk(ingestedDocument.getSource(),
                    chunkContent,
                    metadata,
                    chunkIndex++);
            chunks.add(chunk);
            if (start + chunkSize - overlap < content.length()) {
                start += chunkSize - overlap;
            } else {
                start += chunkSize;
            }
        }
        return chunks;
    }
}
