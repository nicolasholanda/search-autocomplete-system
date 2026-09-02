package com.study.autocomplete.web;

import com.study.autocomplete.config.AutocompleteProperties;
import com.study.autocomplete.service.SuggestionService;
import com.study.autocomplete.trie.Suggestion;
import com.study.autocomplete.web.dto.SuggestionRequest;
import com.study.autocomplete.web.dto.SuggestionResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final int defaultLimit;

    public SuggestionController(SuggestionService suggestionService, AutocompleteProperties properties) {
        this.suggestionService = suggestionService;
        this.defaultLimit = properties.suggestionsPerPrefix();
    }

    @GetMapping
    public ResponseEntity<SuggestionResponse> suggest(@Valid @ModelAttribute SuggestionRequest request) {
        List<Suggestion> suggestions =
                suggestionService.suggest(request.prefix(), request.limitOrDefault(defaultLimit));
        return ResponseEntity.ok(SuggestionResponse.of(request.prefix(), suggestions));
    }
}
