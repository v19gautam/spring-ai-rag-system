package com.vcorp.ai.prompt;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.prompt.model.PromptContext;
import com.vcorp.ai.retrieval.model.RetrievalResult;

import java.util.Map;

public class ContextBuilder {

    public PromptContext build(RetrievalResult retrievalResult) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Chunk chunk : retrievalResult.getChunks()) {
            sb.append("Context ").append(index++).append(":\n");
            appendCitation(sb, chunk);
            sb.append(chunk.getContent()).append("\n\n");
        }
        return new PromptContext(sb.toString().trim());
    }

    private void appendCitation(StringBuilder sb, Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        String source = (String) metadata.get("source");

        switch (source) {
            case "PDF":
            case "WIKI":
                sb.append("[")
                        .append(source).append(": ")
                        .append(metadata.get("fileName"))
                        .append("]\n");
                break;
            case "DB":
                sb.append("[DB: ")
                        .append(metadata.get("table"))
                        .append(" #").append(metadata.get("id"))
                        .append("]\n");
                break;
        }

        String table = (String) metadata.get("table");
        String chunkIndex = (String) metadata.get("chunkIndex");
        sb.append(source).append(" ").append(table).append(" ").append(chunkIndex).append("\n");

    }
}
