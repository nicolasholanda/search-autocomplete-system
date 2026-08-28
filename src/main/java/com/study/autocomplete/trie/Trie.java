package com.study.autocomplete.trie;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie implements Serializable {

    private final TrieNode root = new TrieNode();
    private final int maxPrefixLength;
    private int size;

    public Trie(int maxPrefixLength) {
        if (maxPrefixLength <= 0) {
            throw new IllegalArgumentException("maxPrefixLength must be positive");
        }
        this.maxPrefixLength = maxPrefixLength;
    }

    public void insert(String query, BigDecimal score) {
        String normalized = truncate(query);
        if (normalized.isEmpty()) {
            return;
        }
        TrieNode current = root;
        for (char character : normalized.toCharArray()) {
            current = current.childOrCreate(character);
        }
        if (!current.isTerminal()) {
            current.markTerminal();
            size++;
        }
        current.addScore(score);
    }

    public TrieNode find(String prefix) {
        String normalized = truncate(prefix);
        TrieNode current = root;
        for (char character : normalized.toCharArray()) {
            current = current.childOf(character);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    public List<Suggestion> collect(String prefix, int limit) {
        TrieNode start = find(prefix);
        if (start == null) {
            return List.of();
        }
        List<Suggestion> collected = new ArrayList<>();
        walk(start, new StringBuilder(truncate(prefix)), collected);
        return collected.stream()
                .sorted(Suggestion.BY_SCORE_DESC)
                .limit(limit)
                .toList();
    }

    public List<Suggestion> suggest(String prefix) {
        TrieNode start = find(prefix);
        return start == null ? List.of() : start.topK();
    }

    private void walk(TrieNode node, StringBuilder path, List<Suggestion> collected) {
        if (node.isTerminal()) {
            collected.add(new Suggestion(path.toString(), node.score()));
        }
        for (Map.Entry<Character, TrieNode> entry : node.children().entrySet()) {
            path.append(entry.getKey());
            walk(entry.getValue(), path, collected);
            path.deleteCharAt(path.length() - 1);
        }
    }

    public TrieNode root() {
        return root;
    }

    public int size() {
        return size;
    }

    public int maxPrefixLength() {
        return maxPrefixLength;
    }

    private String truncate(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.strip();
        return trimmed.length() > maxPrefixLength ? trimmed.substring(0, maxPrefixLength) : trimmed;
    }
}
