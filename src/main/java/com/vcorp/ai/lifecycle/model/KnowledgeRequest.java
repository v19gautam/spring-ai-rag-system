package com.vcorp.ai.lifecycle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class KnowledgeRequest {
    private final String sourceType;
    private final String name;
}
