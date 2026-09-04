package com.vcorp.ai.retrieval.model;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalResult {
    private List<Chunk> chunks;
}
