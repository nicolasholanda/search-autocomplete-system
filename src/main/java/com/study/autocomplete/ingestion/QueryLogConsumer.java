package com.study.autocomplete.ingestion;

import com.study.autocomplete.query.QueryLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QueryLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(QueryLogConsumer.class);

    private final QueryAggregator aggregator;

    public QueryLogConsumer(QueryAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @KafkaListener(topics = QueryLogProducer.TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(QueryLog queryLog) {
        aggregator.record(queryLog.query());
        log.debug("Aggregated query '{}' logged at {}", queryLog.query(), queryLog.occurredAt());
    }
}
