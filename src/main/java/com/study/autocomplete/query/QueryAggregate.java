package com.study.autocomplete.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public record QueryAggregate(String query, BigDecimal frequency) implements Serializable {

    public QueryAggregate {
        Objects.requireNonNull(query, "query must not be null");
        Objects.requireNonNull(frequency, "frequency must not be null");
        if (frequency.signum() < 0) {
            throw new IllegalArgumentException("frequency must not be negative");
        }
    }

    public static QueryAggregate of(String query, long frequency) {
        return new QueryAggregate(query, BigDecimal.valueOf(frequency));
    }

    public QueryAggregate merge(QueryAggregate other) {
        if (!query.equals(other.query())) {
            throw new IllegalArgumentException("cannot merge aggregates of different queries");
        }
        return new QueryAggregate(query, frequency.add(other.frequency()));
    }
}
