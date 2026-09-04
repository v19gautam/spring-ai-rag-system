package com.vcorp.ai.retrieval;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RetrievalService {

    private final VectorStore vectorStore;

    public RetrievalService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public RetrievalResult retrieve(String query) {
        log.info("Retrieving requested for query: {}", query);

        //TODO: Implement retrieval logic
        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(5)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        log.info("Retrieved {} documents", documents.size());

        List<Chunk> chunks = documents.stream()
                .map(this::toChunk)
                .toList();

        return new RetrievalResult(chunks);
    }

    private Chunk toChunk(Document document) {
        String content = document.getText();
        Map<String, Object> metadata = new HashMap<>(document.getMetadata());

        int chunkIndex = 0;
        Object chunkIndexValue = metadata.get("chunkIndex");
        if (chunkIndexValue instanceof Integer) {
            chunkIndex = (Integer) chunkIndexValue;
        }

        return new Chunk(
                (String) metadata.get("source"),
                content,
                metadata,
                chunkIndex);
    }
}
