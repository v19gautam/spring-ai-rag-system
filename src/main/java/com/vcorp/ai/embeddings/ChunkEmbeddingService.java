package com.vcorp.ai.embeddings;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChunkEmbeddingService {

    private final EmbeddingModel embeddingModel;
    public EmbeddedChunk embedChunk(Chunk chunk) {
        float[] vector = embeddingModel.embed(chunk.getContent());
        return new EmbeddedChunk(chunk, vector);
    }
}
