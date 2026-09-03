package com.study.autocomplete.ingestion;

import com.study.autocomplete.query.QueryLog;
import com.study.autocomplete.query.QueryNormalizer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryLogProducer {

    public static final String TOPIC = "search.query.log";

    private final KafkaTemplate<String, QueryLog> kafkaTemplate;
    private final QueryNormalizer normalizer;

    public QueryLogProducer(KafkaTemplate<String, QueryLog> kafkaTemplate, QueryNormalizer normalizer) {
        this.kafkaTemplate = kafkaTemplate;
        this.normalizer = normalizer;
    }

    public void publish(String rawQuery) {
        String normalized = normalizer.normalize(rawQuery);
        if (normalized.isEmpty()) {
            return;
        }
        kafkaTemplate.send(TOPIC, normalized, QueryLog.now(normalized));
    }
}
