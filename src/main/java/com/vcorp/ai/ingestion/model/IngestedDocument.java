package com.vcorp.ai.ingestion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class IngestedDocument {
    private String source; //PDF, WIKI, PDFs
    private String content;
    private Map<String, Object> metadata;
}
