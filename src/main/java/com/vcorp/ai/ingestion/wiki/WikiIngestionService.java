package com.vcorp.ai.ingestion.wiki;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

@Service
public class WikiIngestionService {
    private static final Logger LOGGER = Logger.getLogger(WikiIngestionService.class.getName());
    private static final String WIKI_DIRECTORY = "data/wiki";

    public void ingestWikiFiles() throws Exception {
        File[] wikiFiles = new File(WIKI_DIRECTORY).listFiles();

        for (File wikiFile : wikiFiles) {
            ingestSingleFile(wikiFile);
        }
    }

    private void ingestSingleFile(File wikiFile) throws IOException {
        LOGGER.info("Ingesting file: " + wikiFile.getName());

        String content = Files.readString(wikiFile.toPath());

        LOGGER.info("File content: " + wikiFile.getName());
        LOGGER.info(content);
    }
}
