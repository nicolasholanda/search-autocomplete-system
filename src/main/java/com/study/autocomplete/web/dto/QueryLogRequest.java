package com.study.autocomplete.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QueryLogRequest(
        @NotBlank(message = "query must not be blank")
        @Size(max = 100, message = "query must not exceed 100 characters")
        String query) {
}
