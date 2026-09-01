package com.study.autocomplete.repository;

import com.study.autocomplete.query.QueryAggregate;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record TrieSnapshot(String version, Instant builtAt, List<QueryAggregate> aggregates)
        implements Serializable {

    public TrieSnapshot {
        Objects.requireNonNull(version, "version must not be null");
        Objects.requireNonNull(builtAt, "builtAt must not be null");
        aggregates = List.copyOf(Objects.requireNonNull(aggregates, "aggregates must not be null"));
    }

    public static TrieSnapshot of(List<QueryAggregate> aggregates) {
        Instant now = Instant.now();
        return new TrieSnapshot(String.valueOf(now.toEpochMilli()), now, aggregates);
    }
}
