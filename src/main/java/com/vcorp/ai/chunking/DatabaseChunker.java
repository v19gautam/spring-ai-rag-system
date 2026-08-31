package com.vcorp.ai.chunking;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseChunker {
    public List<Chunk> chunk(IngestedDocument document) {
        return List.of(
                new Chunk(document.getSource(),
                        document.getContent(),
                        document.getMetadata(),
                        0
                )
        );
    }
}
