package com.vcorp.ai.prompt;

import com.vcorp.ai.prompt.model.PromptContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroundingRulesTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroundingRulesTest.class);

    @Test
    public void testEmptyContextRule() {
        GroundingPolicy groundingPolicy = new GroundingPolicy();
        String groundingResponse = groundingPolicy.groundingRules(new PromptContext(""));
        LOGGER.info("=====Empty context rule=====\n");
        LOGGER.info(groundingResponse);
    }

    @Test
    public void testNonEmptyContextRule() {
        GroundingPolicy groundingPolicy = new GroundingPolicy();
        String groundingResponse = groundingPolicy.groundingRules(new PromptContext(
                "According to the new work from home policy, employees are allowed to work from home for 2 days a week."));
        LOGGER.info("=====Non-empty context rule=====\n");
        LOGGER.info(groundingResponse);
    }
}
