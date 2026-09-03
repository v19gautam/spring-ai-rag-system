package com.vcorp.ai.embeddings;

import com.vcorp.ai.chunking.model.Chunk;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmbeddedChunk {
    private final Chunk chunk;
    private final float[] vector;
}
