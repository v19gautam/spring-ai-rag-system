package com.vcorp.ai.prompt;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.prompt.model.PromptContext;
import com.vcorp.ai.retrieval.model.RetrievalResult;

public class ContextBuilder {

    public PromptContext build(RetrievalResult retrievalResult) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Chunk chunk : retrievalResult.getChunks()) {
            sb.append("Context ").append(index++).append(":\n");
            sb.append(chunk.getContent()).append("\n\n");
        }
        return new PromptContext(sb.toString().trim());
    }
}
