package com.study.autocomplete.query;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class QueryNormalizer {

    private static final Pattern DIACRITICS = Pattern.compile("\\p{M}+");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern UNSUPPORTED = Pattern.compile("[^a-z0-9 ]");

    public String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        String decomposed = Normalizer.normalize(raw, Normalizer.Form.NFD);
        String stripped = DIACRITICS.matcher(decomposed).replaceAll("");
        String lowered = stripped.toLowerCase(Locale.ROOT);
        String cleaned = UNSUPPORTED.matcher(lowered).replaceAll(" ");
        return WHITESPACE.matcher(cleaned).replaceAll(" ").strip();
    }
}
