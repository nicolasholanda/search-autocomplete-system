package com.study.autocomplete.trie;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode implements Serializable {

    private final Map<Character, TrieNode> children = new HashMap<>();
    private boolean terminal;
    private BigDecimal score = BigDecimal.ZERO;
    private List<Suggestion> topK = List.of();

    public TrieNode childOf(char character) {
        return children.get(character);
    }

    public TrieNode childOrCreate(char character) {
        return children.computeIfAbsent(character, key -> new TrieNode());
    }

    public Map<Character, TrieNode> children() {
        return children;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void markTerminal() {
        this.terminal = true;
    }

    public BigDecimal score() {
        return score;
    }

    public void addScore(BigDecimal amount) {
        this.score = this.score.add(amount);
    }

    public List<Suggestion> topK() {
        return topK;
    }

    public void topK(List<Suggestion> suggestions) {
        this.topK = List.copyOf(suggestions);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
