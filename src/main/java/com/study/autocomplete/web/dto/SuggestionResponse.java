package com.study.autocomplete.web.dto;

import com.study.autocomplete.trie.Suggestion;
import java.util.List;

public record SuggestionResponse(String prefix, int total, List<SuggestionItem> suggestions) {

    public static SuggestionResponse of(String prefix, List<Suggestion> suggestions) {
        List<SuggestionItem> items = suggestions.stream().map(SuggestionItem::from).toList();
        return new SuggestionResponse(prefix, items.size(), items);
    }
}
