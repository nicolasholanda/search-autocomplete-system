package com.study.autocomplete.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.study.autocomplete.config.AutocompleteProperties;
import com.study.autocomplete.query.QueryNormalizer;
import com.study.autocomplete.store.TrieStore;
import com.study.autocomplete.trie.Suggestion;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {

    private final TrieStore trieStore;
    private final QueryNormalizer normalizer;
    private final Cache<String, List<Suggestion>> suggestionCache;
    private final int maxPrefixLength;
    private final int suggestionsPerPrefix;

    public SuggestionService(TrieStore trieStore,
                             QueryNormalizer normalizer,
                             Cache<String, List<Suggestion>> suggestionCache,
                             AutocompleteProperties properties) {
        this.trieStore = trieStore;
        this.normalizer = normalizer;
        this.suggestionCache = suggestionCache;
        this.maxPrefixLength = properties.maxPrefixLength();
        this.suggestionsPerPrefix = properties.suggestionsPerPrefix();
    }

    public List<Suggestion> suggest(String rawPrefix, int limit) {
        String prefix = truncate(normalizer.normalize(rawPrefix));
        if (prefix.isEmpty()) {
            return List.of();
        }
        int effectiveLimit = limit <= 0 ? suggestionsPerPrefix : Math.min(limit, suggestionsPerPrefix);
        List<Suggestion> cached = suggestionCache.get(prefix, trieStore::suggest);
        return cached.size() <= effectiveLimit ? cached : cached.subList(0, effectiveLimit);
    }

    public List<Suggestion> suggest(String rawPrefix) {
        return suggest(rawPrefix, suggestionsPerPrefix);
    }

    public void invalidateCache() {
        suggestionCache.invalidateAll();
    }

    private String truncate(String prefix) {
        return prefix.length() > maxPrefixLength ? prefix.substring(0, maxPrefixLength) : prefix;
    }
}
