package com.vcorp.ai.retrieval;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class RetrievalService {

    private final VectorStore vectorStore;
    private final ChunkRankingComparator chunkRankingComparator = new ChunkRankingComparator();

    public RetrievalService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public RetrievalResult retrieve(String query) {
        log.info("Retrieval requested for query: {}", query);

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(10)
                .similarityThreshold(0.75)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        log.info("Retrieved {} documents", documents.size());

        List<Chunk> chunks = documents.stream()
                .filter(this::isAllowedByMetadata)
                .map(this::toChunk)
                .sorted(chunkRankingComparator)
                .toList();

        return new RetrievalResult(chunks);
    }

    private boolean isAllowedByMetadata(Document document) {
        Map<String, Object> metadata = document.getMetadata();
        String source = (String) metadata.get("source");
        if(!source.equals("DB")) {
            return true;
        }

        String table = (String) metadata.get("table");
        return switch (table) {
            case "announcements" -> isActiveAnnouncement(metadata);
            case "faqs" -> isPublicFaq(metadata);
//            case "release_notes" -> true; // Included in the default case
            default -> true;
        };

    }

    private boolean isActiveAnnouncement(Map<String, Object> metadata) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        String startDateStr = (String) metadata.get("effective_from");
        String endDateStr = (String) metadata.get("effective_to");

        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = !endDateStr.isEmpty() ? LocalDate.parse(endDateStr, formatter) : today.plusDays(1);

        return !today.isAfter(endDate) && !today.isBefore(startDate);
    }

    private boolean isPublicFaq(Map<String, Object> metadata) {
        return !metadata.get("visibility").toString().equals("RESTRICTED");
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
