package com.vcorp.ai.prompt;

import com.vcorp.ai.prompt.model.ChatPrompt;
import com.vcorp.ai.prompt.model.PromptContext;
import com.vcorp.ai.prompt.model.SystemInstructions;
import com.vcorp.ai.retrieval.RetrievalService;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptOrchestrator {

    private final RetrievalService retrievalService;
    private final ContextBuilder contextBuilder = new ContextBuilder();
    private final SystemPromptLoader systemPromptLoader = new SystemPromptLoader();
    private final GroundingPolicy groundingPolicy = new GroundingPolicy();

    public ChatPrompt build(String question) {
        RetrievalResult retrievalResult = retrievalService.retrieve(question);
        PromptContext promptContext = contextBuilder.build(retrievalResult);
        SystemInstructions systemInstructions = systemPromptLoader.load();
        String groundingRules = groundingPolicy.groundingRules(promptContext);

        return new ChatPrompt(systemInstructions, promptContext, groundingRules);
    }
}
