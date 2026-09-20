package com.vcorp.ai.lifecycle;

import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KnowledgeIdentityTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeIdentityTest.class);

    @Test
    public void testSourceIdentity() {
        KnowledgeRequest knowledgeRequest = KnowledgeRequest.builder()
                        .sourceType("PDF")
                        .name("HR_Leave_Policy.pdf")
                .build();
        LOGGER.info("Identity: {}", KnowledgeIdentity.from(knowledgeRequest));
    }
}
