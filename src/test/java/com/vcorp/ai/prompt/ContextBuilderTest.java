package com.vcorp.ai.prompt;

import com.vcorp.ai.prompt.model.PromptContext;
import com.vcorp.ai.retrieval.RetrievalService;
import com.vcorp.ai.retrieval.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContextBuilderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextBuilderTest.class);

    @Autowired
    RetrievalService retrievalService;

    @Test
    void testContextBuilder() {
        RetrievalResult result = retrievalService.retrieve("What is the work from home policy?");

        PromptContext promptContext = new ContextBuilder().build(result);

        LOGGER.info("Loaded Context: \n");
        LOGGER.info(promptContext.getContext());
    }
}
