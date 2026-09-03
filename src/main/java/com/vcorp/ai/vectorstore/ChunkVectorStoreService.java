package com.vcorp.ai.vectorstore;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChunkVectorStoreService {
    private final VectorStore vectorStore;

    public ChunkVectorStoreService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(List<Chunk> chunks){
        List<Document> documents = chunks.stream().map(chunk -> {
            Map<String, Object> metadata = new HashMap<>(chunk.getMetadata());
            metadata.put("source", chunk.getSource());
            metadata.put("chunkIndex", chunk.getChunkIndex());
            return new Document(chunk.getContent(), metadata);
        }).toList();

        vectorStore.add(documents);
    }
}
