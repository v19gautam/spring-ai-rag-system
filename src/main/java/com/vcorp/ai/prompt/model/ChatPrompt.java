package com.vcorp.ai.prompt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPrompt {
    private final SystemInstructions systemInstructions;
    private final PromptContext promptContext;
    private final String groundingRules;
}
