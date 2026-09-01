package com.study.autocomplete.web.dto;

import com.study.autocomplete.trie.Suggestion;
import java.math.BigDecimal;

public record SuggestionItem(String query, BigDecimal score) {

    public static SuggestionItem from(Suggestion suggestion) {
        return new SuggestionItem(suggestion.query(), suggestion.score());
    }
}
