package com.study.autocomplete.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TopKCacheBuilder {

    private final int limit;

    public TopKCacheBuilder(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be positive");
        }
        this.limit = limit;
    }

    public void apply(Trie trie) {
        build(trie.root(), new StringBuilder());
    }

    private List<Suggestion> build(TrieNode node, StringBuilder path) {
        List<Suggestion> candidates = new ArrayList<>();
        if (node.isTerminal()) {
            candidates.add(new Suggestion(path.toString(), node.score()));
        }
        for (Map.Entry<Character, TrieNode> entry : node.children().entrySet()) {
            path.append(entry.getKey());
            candidates.addAll(build(entry.getValue(), path));
            path.deleteCharAt(path.length() - 1);
        }
        List<Suggestion> top = candidates.stream()
                .sorted(Suggestion.BY_SCORE_DESC)
                .limit(limit)
                .toList();
        node.topK(top);
        return top;
    }
}
