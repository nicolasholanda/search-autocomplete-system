package com.study.autocomplete.ingestion;

import com.study.autocomplete.query.QueryAggregate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class QueryAggregator {

    private final Map<String, BigDecimal> counters = new ConcurrentHashMap<>();

    public void record(String query) {
        counters.merge(query, BigDecimal.ONE, BigDecimal::add);
    }

    public List<QueryAggregate> snapshot() {
        return counters.entrySet().stream()
                .map(entry -> new QueryAggregate(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<QueryAggregate> drain() {
        List<QueryAggregate> aggregates = snapshot();
        counters.clear();
        return aggregates;
    }

    public int size() {
        return counters.size();
    }
}
