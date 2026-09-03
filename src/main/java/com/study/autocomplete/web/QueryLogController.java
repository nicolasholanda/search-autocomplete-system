package com.study.autocomplete.web;

import com.study.autocomplete.ingestion.QueryLogProducer;
import com.study.autocomplete.web.dto.QueryLogRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/query-logs")
public class QueryLogController {

    private final QueryLogProducer producer;

    public QueryLogController(QueryLogProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<Void> log(@Valid @RequestBody QueryLogRequest request) {
        producer.publish(request.query());
        return ResponseEntity.accepted().build();
    }
}
