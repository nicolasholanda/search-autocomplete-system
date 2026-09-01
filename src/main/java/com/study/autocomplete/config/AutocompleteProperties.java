package com.study.autocomplete.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "autocomplete")
public record AutocompleteProperties(
        int maxPrefixLength,
        int suggestionsPerPrefix,
        LocalCache localCache,
        Rebuild rebuild) {

    public record LocalCache(long maximumSize, Duration expireAfterWrite) {
    }

    public record Rebuild(String cron) {
    }
}
