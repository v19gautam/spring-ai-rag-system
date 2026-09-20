package com.vcorp.ai.lifecycle;

import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeLifecycleService {

    public void ingest(KnowledgeRequest request) {
        String identity = KnowledgeIdentity.from(request);
    }

    public void delete(KnowledgeRequest request) {
        String identity = KnowledgeIdentity.from(request);
    }
}
