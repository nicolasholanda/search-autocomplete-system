package com.study.autocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SearchAutocompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchAutocompleteApplication.class, args);
    }
}
