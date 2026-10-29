package com.fpldata.ingestor.ingest;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingest-runs")
public class IngestRunController {

    private final IngestRunRepository repository;

    public IngestRunController(IngestRunRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<IngestRunResponse> list() {
        return repository.findAllByOrderByStartedAtDesc().stream()
                .map(IngestRunResponse::from)
                .toList();
    }

    public record IngestRunResponse(
            Long id,
            String endpoint,
            Instant startedAt,
            Instant finishedAt,
            String status,
            String contentHash,
            String rowCounts) {

        static IngestRunResponse from(IngestRun run) {
            return new IngestRunResponse(
                    run.getId(),
                    run.getEndpoint(),
                    run.getStartedAt(),
                    run.getFinishedAt(),
                    run.getStatus(),
                    run.getContentHash(),
                    run.getRowCounts());
        }
    }
}
