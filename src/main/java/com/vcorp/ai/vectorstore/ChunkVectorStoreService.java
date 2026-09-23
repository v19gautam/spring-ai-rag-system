package com.vcorp.ai.vectorstore;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
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

    public void deleteAll(){
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        Filter.Expression filter = filterBuilder.gte("chunkIndex", -1).build();

        SearchRequest searchRequest = SearchRequest.builder().query(" ")
                .filterExpression(filter)
                .topK(10).build();

        while (!vectorStore.similaritySearch(searchRequest).isEmpty()) {
            vectorStore.delete(filter);
        }
    }

    public void deleteByIdentity(String identity){
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
//        String sanitizedIdentity = escapeRediSearchTag(identity);
        Filter.Expression filter = filterBuilder.eq("identity", identity).build();
//        String textFilter = String.format("identity == '%s'", identity);
//        System.out.println("Executing Filter Expression: " + textFilter);
//        System.out.println("Generated Filter Expression: " + sanitizedIdentity);
        List<Document> existingDocs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(" ")
                .filterExpression(filter)
                .topK(1)
                .build());
        if (existingDocs.isEmpty()) {
            vectorStore.delete(filter);
        }
    }
    private String escapeRediSearchTag(String value) {
        if (value == null) return "";
        // Escapes special characters for RediSearch TAG queries
        return value.replaceAll("([\\-\\_\\.\\/\\@\\:\\{\\}\\s])", "\\\\$1");
    }
}
