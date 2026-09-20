package com.vcorp.ai.lifecycle;

import com.vcorp.ai.lifecycle.model.KnowledgeRequest;

public class KnowledgeIdentity {

    public static String from(KnowledgeRequest request) {

        if(request.getSourceType().equals("PDF")) {
            return "PDF#" + request.getName();
        }
        if(request.getSourceType().equals("WIKI")) {
            return "WIKI#" + request.getName();
        }
        if(request.getSourceType().equals("DB")) {
            return "DB#" + request.getName();
        }
        return "";
    }
}
