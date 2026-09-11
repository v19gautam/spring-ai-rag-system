package com.vcorp.ai.prompt;

import com.vcorp.ai.prompt.model.PromptContext;

public class GroundingPolicy {

    public String groundingRules(PromptContext promptContext) {

        if (promptContext == null || promptContext.getContext().isBlank()) {
            return """
                    You do not have sufficient information to answer the question.
                    Respond clearly that you do not know the answer.
                    Do not guess or infer.
                    """;
        }
        return """
                Answer the question based ONLY on the provided context.
                If the answer is not in the context, respond clearly that you do not know the answer.
                Do not use your prior knowledge.
                """;
    }
}
