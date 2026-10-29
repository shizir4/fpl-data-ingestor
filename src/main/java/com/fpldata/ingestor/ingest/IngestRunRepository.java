package com.fpldata.ingestor.ingest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngestRunRepository extends JpaRepository<IngestRun, Long> {

    List<IngestRun> findAllByOrderByStartedAtDesc();
}
