package com.study.autocomplete.trie;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

public record Suggestion(String query, BigDecimal score) implements Serializable {

    public static final Comparator<Suggestion> BY_SCORE_DESC =
            Comparator.comparing(Suggestion::score).reversed().thenComparing(Suggestion::query);

    public Suggestion {
        Objects.requireNonNull(query, "query must not be null");
        Objects.requireNonNull(score, "score must not be null");
        if (query.isBlank()) {
            throw new IllegalArgumentException("query must not be blank");
        }
        if (score.signum() < 0) {
            throw new IllegalArgumentException("score must not be negative");
        }
    }

    public static Suggestion of(String query, long score) {
        return new Suggestion(query, BigDecimal.valueOf(score));
    }

    public Suggestion plus(BigDecimal amount) {
        return new Suggestion(query, score.add(amount));
    }
}
