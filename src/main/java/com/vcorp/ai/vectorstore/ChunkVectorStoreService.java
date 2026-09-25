package com.vcorp.ai.vectorstore;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
        String escapedIdentity = escapeRedisDelimiter(identity);
        SearchRequest searchRequest = SearchRequest.builder()
                .query(" ")
                .filterExpression(filterBuilder.eq("identity", escapedIdentity).build())
                .build();
        List<Document> matchingDocuments = vectorStore.similaritySearch(searchRequest);
        log.info("Found {} matching documents for identity {}", matchingDocuments.size(), identity);
        if (!matchingDocuments.isEmpty()) {
            vectorStore.delete(filterBuilder.eq("identity", escapedIdentity).build());
        }
    }
    private String escapeRedisDelimiter(String value) {
        if (value == null) return "";
        // Escapes special characters for RedisSearch TAG queries
        return value.replaceAll("([\\-./@:{}\\s])", "_");
    }
}
