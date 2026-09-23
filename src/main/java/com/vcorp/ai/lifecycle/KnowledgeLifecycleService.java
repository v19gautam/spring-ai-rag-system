package com.vcorp.ai.lifecycle;

import com.vcorp.ai.chunking.ChunkingOrchestrator;
import com.vcorp.ai.chunking.model.Chunk;
import com.vcorp.ai.ingestion.IngestionOrchestrator;
import com.vcorp.ai.ingestion.model.IngestedDocument;
import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import com.vcorp.ai.vectorstore.ChunkVectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeLifecycleService {

    private final ChunkVectorStoreService chunkVectorStoreService;
    private final IngestionOrchestrator ingestionOrchestrator;
    private final ChunkingOrchestrator chunkingOrchestrator;

    public void ingest(KnowledgeRequest request) throws Exception {
        String identity = KnowledgeIdentity.from(request);
        chunkVectorStoreService.deleteByIdentity(identity);

        List<IngestedDocument> documents = ingestionOrchestrator.ingest(request);
        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument document : documents) {
            chunksToStore.addAll(chunkingOrchestrator.chunk(document));
        }
        chunkVectorStoreService.store(chunksToStore);
    }

    public void delete(KnowledgeRequest request) {
        String identity = KnowledgeIdentity.from(request);
        chunkVectorStoreService.deleteByIdentity(identity);
    }

    public void ingestAll() throws Exception {
        deleteAll();

        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();
        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument document : documents) {
            chunksToStore.addAll(chunkingOrchestrator.chunk(document));
        }
        chunkVectorStoreService.store(chunksToStore);
    }

    public void deleteAll() {
        chunkVectorStoreService.deleteAll();
    }
}
