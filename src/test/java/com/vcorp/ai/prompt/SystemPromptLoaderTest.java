package com.vcorp.ai.prompt;

import com.vcorp.ai.prompt.model.SystemInstructions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SystemPromptLoaderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemPromptLoaderTest.class);

    @Test
    public void loadSystemPromptFromFiles() {
        SystemInstructions systemInstructions = new SystemPromptLoader().load();
        LOGGER.info("-----Loaded System Instructions-----");
        LOGGER.info("\n {}", systemInstructions.getSystemInstructions());
    }
}
