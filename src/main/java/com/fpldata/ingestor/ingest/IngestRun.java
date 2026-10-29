package com.fpldata.ingestor.ingest;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingest_runs")
public class IngestRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String endpoint;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(name = "content_hash", length = 64)
    private String contentHash;

    @Column(name = "row_counts", length = 1024)
    private String rowCounts;

    protected IngestRun() {
    }

    public IngestRun(String endpoint, Instant startedAt, String status) {
        this.endpoint = endpoint;
        this.startedAt = startedAt;
        this.status = status;
    }

    public void markFinished(String status, String contentHash, String rowCounts) {
        this.status = status;
        this.contentHash = contentHash;
        this.rowCounts = rowCounts;
        this.finishedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public String getStatus() {
        return status;
    }

    public String getContentHash() {
        return contentHash;
    }

    public String getRowCounts() {
        return rowCounts;
    }
}
