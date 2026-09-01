package com.study.autocomplete.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SuggestionRequest(
        @NotBlank(message = "prefix must not be blank")
        @Size(max = 50, message = "prefix must not exceed 50 characters")
        String prefix,

        @Min(value = 1, message = "limit must be at least 1")
        @Max(value = 10, message = "limit must not exceed 10")
        Integer limit) {

    public int limitOrDefault(int fallback) {
        return limit == null ? fallback : limit;
    }
}
