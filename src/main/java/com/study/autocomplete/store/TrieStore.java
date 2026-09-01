package com.study.autocomplete.store;

import com.study.autocomplete.config.AutocompleteProperties;
import com.study.autocomplete.trie.Suggestion;
import com.study.autocomplete.trie.Trie;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrieStore {

    private static final Logger log = LoggerFactory.getLogger(TrieStore.class);

    private final AtomicReference<Trie> current;
    private final AtomicReference<Instant> loadedAt = new AtomicReference<>(Instant.EPOCH);

    public TrieStore(AutocompleteProperties properties) {
        this.current = new AtomicReference<>(new Trie(properties.maxPrefixLength()));
    }

    public List<Suggestion> suggest(String prefix) {
        return current.get().suggest(prefix);
    }

    public void swap(Trie replacement) {
        Trie previous = current.getAndSet(replacement);
        loadedAt.set(Instant.now());
        log.info("Trie swapped: {} entries replaced by {} entries", previous.size(), replacement.size());
    }

    public Trie snapshot() {
        return current.get();
    }

    public Instant loadedAt() {
        return loadedAt.get();
    }

    public boolean isEmpty() {
        return current.get().size() == 0;
    }
}
