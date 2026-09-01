package com.study.autocomplete.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.study.autocomplete.trie.Suggestion;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String, List<Suggestion>> suggestionCache(AutocompleteProperties properties) {
        AutocompleteProperties.LocalCache localCache = properties.localCache();
        return Caffeine.newBuilder()
                .maximumSize(localCache.maximumSize())
                .expireAfterWrite(localCache.expireAfterWrite())
                .recordStats()
                .build();
    }
}
