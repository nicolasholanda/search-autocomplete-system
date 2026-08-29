package com.study.autocomplete.query;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public record QueryLog(String query, Instant occurredAt) implements Serializable {

    public QueryLog {
        Objects.requireNonNull(query, "query must not be null");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public static QueryLog now(String query) {
        return new QueryLog(query, Instant.now());
    }
}
