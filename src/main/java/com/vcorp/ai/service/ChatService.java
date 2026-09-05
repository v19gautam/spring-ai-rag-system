package com.vcorp.ai.service;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.dto.ChatRequest;
import com.vcorp.ai.dto.ChatResponse;
import com.vcorp.ai.retrieval.RetrievalService;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final RetrievalService retrievalService;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getPrompt();

        RetrievalResult retrievalResult = retrievalService.retrieve(userMessage);
        String context = buildContext(retrievalResult);
        String aiResponse = chatClient.prompt()
                .system(context)
                .user(request.getPrompt())
                .call().content();
        return new ChatResponse(aiResponse);
    }

    private String buildContext(RetrievalResult retrievalResult) {
        StringBuilder sb = new StringBuilder();
        for (Chunk chunk : retrievalResult.getChunks()) {
            sb.append(chunk.getContent()).append("\n\n");
        }
        return sb.toString();
    }
}
