package com.vcorp.ai.lifecycle;

import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import com.vcorp.ai.lifecycle.model.SourceType;

public class KnowledgeIdentity {

    public static String from(KnowledgeRequest request) {

        if(request.getSourceType().equals(SourceType.PDF)) {
            return "PDF#" + request.getName();
        }
        if(request.getSourceType().equals(SourceType.WIKI)) {
            return "WIKI#" + request.getName();
        }
        if(request.getSourceType().equals(SourceType.DATABASE)) {
            return "DB#" + request.getName();
        }
        return "";
    }
}
