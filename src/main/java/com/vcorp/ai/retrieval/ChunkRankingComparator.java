package com.vcorp.ai.retrieval;

import com.vcorp.ai.chunking.model.Chunk;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class ChunkRankingComparator implements Comparator<Chunk> {

    @Override
    public int compare(Chunk c1, Chunk c2) {

        //Priority:
        //1. Source
        int sourceComparison = Integer.compare(getSourcePriority(c1), getSourcePriority(c2));
        if (sourceComparison != 0) {
            return sourceComparison;
        }
        //2. DB table
        if (isDbChunk(c1) && isDbChunk(c2)) {
            int tableComparison = Integer.compare(getTablePriority(c1), getTablePriority(c2));
            if (tableComparison != 0) {
                return tableComparison;
            }
        }
        //3. Recency
        LocalDate relevantDateC1 = getRelevantDate(c1);
        LocalDate relevantDateC2 = getRelevantDate(c2);
        if (relevantDateC1 != null && relevantDateC2 != null) {
            return relevantDateC2.compareTo(relevantDateC1);
        }

        return 0;
    }

    private static LocalDate getRelevantDate(Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        if (!metadata.get("source").equals("DB")) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        String table = metadata.get("table").toString();

        if (table.equals("release_notes")) {
            return LocalDate.parse(chunk.getMetadata().get("releaseDate").toString(), formatter);
        }

        if (table.equals("announcements")) {
            return LocalDate.parse(chunk.getMetadata().get("effective_from").toString(), formatter);
        }
        return null;
    }

    private static int getTablePriority(Chunk chunk) {
        String tableName = chunk.getMetadata().get("table").toString();
        return switch (tableName) {
            case "PDF" -> 1;
            case "DB" -> 2;
            case "WIKI" -> 3;
            default -> 4;
        };
    }

    private boolean isDbChunk(Chunk c1) {
        return c1.getMetadata().get("source").toString().equals("DB");
    }

    private int getSourcePriority(Chunk chunk) {
        String source = chunk.getMetadata().get("source").toString();
        return switch (source) {
            case "PDF" -> 1;
            case "DB" -> 2;
            case "WIKI" -> 3;
            default -> 4;
        };
    }


}
