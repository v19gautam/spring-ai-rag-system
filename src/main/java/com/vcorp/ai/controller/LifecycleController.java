package com.vcorp.ai.controller;

import com.vcorp.ai.lifecycle.KnowledgeLifecycleService;
import com.vcorp.ai.lifecycle.model.KnowledgeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lifecycle")
@RequiredArgsConstructor
public class LifecycleController {

    private final KnowledgeLifecycleService knowledgeLifecycleService;

    @PostMapping("/ingest")
    public ResponseEntity<Object> ingest(@RequestBody KnowledgeRequest request) throws Exception {
        knowledgeLifecycleService.ingest(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ingest_all")
    public ResponseEntity<Object> ingestAll() throws Exception{
        knowledgeLifecycleService.ingestAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody KnowledgeRequest request) {
        knowledgeLifecycleService.delete(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<Object> deleteAll() {
        knowledgeLifecycleService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
