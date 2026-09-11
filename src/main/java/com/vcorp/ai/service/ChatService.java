package com.vcorp.ai.service;

import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.dto.ChatRequest;
import com.vcorp.ai.dto.ChatResponse;
import com.vcorp.ai.prompt.PromptOrchestrator;
import com.vcorp.ai.prompt.model.ChatPrompt;
import com.vcorp.ai.retrieval.RetrievalService;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final PromptOrchestrator promptOrchestrator;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getPrompt();
        ChatPrompt chatPrompt = promptOrchestrator.build(userMessage);

        String llmInput = chatPrompt.getGroundingRules()
                + "\n\n" + chatPrompt.getPromptContext().getContext()
                + "\n\n" + userMessage;

        String aiResponse = chatClient.prompt()
                .system(chatPrompt.getSystemInstructions().getSystemInstructions())
                .user(llmInput)
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
