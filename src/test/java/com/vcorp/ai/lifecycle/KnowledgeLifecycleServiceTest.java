package com.vcorp.ai.lifecycle;

import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import com.vcorp.ai.lifecycle.model.SourceType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KnowledgeLifecycleServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeLifecycleServiceTest.class);

    @Autowired
    KnowledgeLifecycleService knowledgeLifecycleService;

    @Test
    public void ingestPdfTest() throws Exception {
        KnowledgeRequest request = KnowledgeRequest.builder()
                .sourceType(SourceType.PDF)
                .name("HR_Leave_Policy.pdf")
                .build();
        knowledgeLifecycleService.ingest(request);
    }

    @Test
    public void ingestAllTest() throws Exception {
        knowledgeLifecycleService.ingestAll();
    }

    @Test
    public void deleteAllTest() {
        knowledgeLifecycleService.deleteAll();
    }

    @Test
    public void deleteByIdentityTest() {
        LOGGER.info("Delete");
    }

}
