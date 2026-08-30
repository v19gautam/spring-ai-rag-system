package com.vcorp.ai.ingestion.db;

import com.vcorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DatabaseIngestionService {

    private static final Logger logger = Logger.getLogger(DatabaseIngestionService.class.getName());
    private final JdbcTemplate jdbcTemplate;

    public List<IngestedDocument> ingestDatabaseContent() {
        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        ingestedDocuments.addAll(ingestFaqs());
        ingestedDocuments.addAll(ingestReleaseNotes());
        ingestedDocuments.addAll(ingestAnnouncements());
        return ingestedDocuments;
    }

    public List<IngestedDocument> ingestFaqs() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, question, answer, department, visibility FROM faqs");

        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            logger.info("--------------FAQ-------------");
            logger.info("Question: " + row.get("question"));
            logger.info("Answer: " + row.get("answer"));

            String content = "Question: " + row.get("question") + "\n" + "Answer:" + row.get("answer");
            IngestedDocument ingestedDocument = new IngestedDocument(
                    "DB", content,
                    Map.of(
                            "table", "faqs",
                            "id", row.get("id"),
                            "department", row.get("department")!=null ? row.get("department") : "",
                            "visibility", row.get("visibility")!=null ? row.get("visibility") : ""
                    )
            );
            ingestedDocuments.add(ingestedDocument);
        }
        return ingestedDocuments;
    }

    public List<IngestedDocument> ingestReleaseNotes() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, version, summary, details, release_date FROM vcorp.public.release_notes");

        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            logger.info("--------------Release Notes-------------");
            logger.info("Version: " + row.get("version"));
            logger.info("Description: " + row.get("summary"));

            String content = "Version: " + row.get("version") + "\n"
                    + "Summary: " + row.get("summary") + "\n"
                    + "Details: " + row.get("details");

            IngestedDocument ingestedDocument = new IngestedDocument(
                    "DB", content,
                    Map.of("table", "release_notes",
                            "id", row.get("id"),
                            "version", row.get("version")!=null ? row.get("version") : "",
                            "release_date", row.get("release_date") != null ? row.get("release_date") : ""
                    ));
            ingestedDocuments.add(ingestedDocument);
        }
        return ingestedDocuments;
    }

    public List<IngestedDocument> ingestAnnouncements() {
        List<Map<String, Object>> rows = jdbcTemplate.
                queryForList("SELECT id, subject, id, subject, body, category, effective_from, effective_to, source_type FROM vcorp.public.announcements");

        List<IngestedDocument> ingestedDocuments = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            logger.info("--------------Announcements-------------");
            logger.info("Title: " + row.get("subject"));

            String content = "Subject: " + row.get("subject") + "\n"
                    + "Body: " + row.get("body");
            IngestedDocument ingestedDocument = new IngestedDocument(
                    "DB", content,
                    Map.of("table", "announcements",
                            "id", row.get("id"),
                            "category", row.get("category")!=null ? row.get("category") : "",
                            "effective_from", row.get("effective_from") != null ? row.get("effective_from") : "",
                            "effective_to", row.get("effective_to") != null ? row.get("effective_to") : "",
                            "source_type", row.get("source_type")!=null ? row.get("source_type") : ""
                    ));
            ingestedDocuments.add(ingestedDocument);
        }
        return ingestedDocuments;
    }

}
