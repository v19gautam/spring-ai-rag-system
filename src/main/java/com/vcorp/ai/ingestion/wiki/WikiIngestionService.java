package com.vcorp.ai.ingestion.wiki;

import com.vcorp.ai.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class WikiIngestionService {
    private static final Logger LOGGER = Logger.getLogger(WikiIngestionService.class.getName());
    private static final String WIKI_DIRECTORY = "data/wiki";

    public List<IngestedDocument> ingestWikiFiles() throws Exception {
        File[] wikiFiles = new File(WIKI_DIRECTORY).listFiles();
        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        for (File wikiFile : wikiFiles) {
            ingestedDocuments.add(ingestSingleFile(wikiFile));
        }
        return ingestedDocuments;
    }

    private IngestedDocument ingestSingleFile(File wikiFile) throws IOException {
//        LOGGER.info("Ingesting file: " + wikiFile.getName());

        String content = Files.readString(wikiFile.toPath());

//        LOGGER.info("File content: " + wikiFile.getName());
//        LOGGER.info(content);

        return new IngestedDocument("WIKI", content, Map.of("fileName", wikiFile.getName()));
    }
}
