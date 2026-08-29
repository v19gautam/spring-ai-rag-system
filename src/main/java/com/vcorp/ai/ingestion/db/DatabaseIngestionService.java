package com.vcorp.ai.ingestion.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DatabaseIngestionService {

    private static final Logger logger = Logger.getLogger(DatabaseIngestionService.class.getName());
    private final JdbcTemplate jdbcTemplate;

    public void ingestFaqs() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, question, answer, department, visibility FROM faqs");
        for (Map<String, Object> row : rows) {
            logger.info("--------------FAQ-------------");
            logger.info("Question: " + row.get("question"));
            logger.info("Answer: " + row.get("answer"));
        }
    }

    public void ingestReleaseNotes() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, version, summary, details, release_date FROM vcorp.public.release_notes");
        for (Map<String, Object> row : rows) {
            logger.info("--------------Release Notes-------------");
            logger.info("Version: " + row.get("version"));
            logger.info("Description: " + row.get("summary"));
        }
    }

    public void ingestAnnouncements() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, subject, id, subject, body, category, effective_from, effective_to, source_type FROM vcorp.public.announcements");
        for (Map<String, Object> row : rows) {
            logger.info("--------------Announcements-------------");
            logger.info("Title: " + row.get("subject"));
        }
    }

}
