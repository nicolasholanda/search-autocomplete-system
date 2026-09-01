package com.study.autocomplete;

import com.study.autocomplete.config.AutocompleteProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AutocompleteProperties.class)
public class SearchAutocompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchAutocompleteApplication.class, args);
    }
}
