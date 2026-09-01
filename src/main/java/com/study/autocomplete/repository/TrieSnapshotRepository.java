package com.study.autocomplete.repository;

import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrieSnapshotRepository {

    private static final String CURRENT_KEY = "autocomplete:trie:current";
    private static final String VERSION_KEY_PREFIX = "autocomplete:trie:version:";

    private final RedisTemplate<String, TrieSnapshot> redisTemplate;

    public TrieSnapshotRepository(RedisTemplate<String, TrieSnapshot> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(TrieSnapshot snapshot) {
        redisTemplate.opsForValue().set(VERSION_KEY_PREFIX + snapshot.version(), snapshot);
        redisTemplate.opsForValue().set(CURRENT_KEY, snapshot);
    }

    public Optional<TrieSnapshot> findCurrent() {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CURRENT_KEY));
    }

    public Optional<TrieSnapshot> findByVersion(String version) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(VERSION_KEY_PREFIX + version));
    }

    public void deleteVersion(String version) {
        redisTemplate.delete(VERSION_KEY_PREFIX + version);
    }
}
