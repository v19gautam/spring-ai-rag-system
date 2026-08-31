package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WikiSemanticChunker {

    public List<Chunk> chunk(IngestedDocument document) {
        List<Chunk> chunks = new ArrayList<>();

        String content = document.getContent();
        //Split content by markdown headings (##, ###, etc)
        String[] sections = content.split("\n(?=#+\\s)");
        int chunkIndex = 0;

        for (String section : sections) {
            String trimmed = section.trim();
            if (trimmed.isEmpty()){
                continue;
            }

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("chunkIndex", chunkIndex);
            metadata.put("chunkType", "WIKI_SECTION");

            Chunk chunk = new Chunk(
                    document.getSource(),
                    trimmed,
                    metadata,
                    chunkIndex
            );
            chunks.add(chunk);
            chunkIndex++;
        }

        return chunks;
    }
}
