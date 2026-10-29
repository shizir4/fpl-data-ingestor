package com.fpldata.ingestor.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StatusController {

    private final String service;
    private final String version;
    private final Environment environment;

    public StatusController(
            @Value("${spring.application.name}") String service,
            @Value("${app.version:dev}") String version,
            Environment environment) {
        this.service = service;
        this.version = version;
        this.environment = environment;
    }

    @GetMapping("/status")
    public StatusResponse status() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = activeProfiles.length == 0
                ? List.of("default")
                : Arrays.asList(activeProfiles);

        return new StatusResponse(service, "ok", version, profiles, Instant.now());
    }

    public record StatusResponse(
            String service,
            String status,
            String version,
            List<String> profiles,
            Instant timestamp) {
    }
}
