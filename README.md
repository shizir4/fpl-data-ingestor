# fpl-data-ingestor

Catalog service for Fantasy Premier League data. This is the first service in the
War Room system: it will ingest FPL data, store a canonical copy, and expose it
over a read-only REST API.

This repository is currently a **walking skeleton**: Spring Boot web layer, JPA,
Flyway, H2 for local development, Postgres for production, health checks, and a
first `ingest_runs` table. The FPL client and bootstrap ingest come next.

## Stack

- Java 17
- Spring Boot 4.1
- Maven
- Spring Data JPA + Flyway
- H2 (local default) / PostgreSQL (`prod` profile)
- Actuator

## Run locally

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080` with an in-memory H2 database.

```bash
curl http://localhost:8080/api/v1/status
curl http://localhost:8080/api/v1/ingest-runs
curl http://localhost:8080/actuator/health
```

H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:fpl`,
user `sa`, empty password).

## Tests

```bash
./mvnw test
```

## Run with Postgres (Docker)

```bash
docker compose up --build
```

This starts Postgres and the app with the `prod` profile. Flyway applies the
migrations on startup.

## Deploy

The repository includes:

- `Dockerfile` — multi-stage build
- `compose.yaml` — local Postgres + app
- `render.yaml` — Render blueprint (Docker, free plan)

To deploy on Render: connect the GitHub repository, create a Blueprint from
`render.yaml`, and set any environment variables you need. Without a database
attached it runs with the local H2 profile, which is fine for a demo but not for
persistent data.

For a real deployment, set:

```text
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:postgresql://<host>:5432/<database>
DATABASE_USERNAME=<user>
DATABASE_PASSWORD=<password>
```

## Roadmap

1. FPL API client with recorded fixtures and typed responses
2. `bootstrap-static` ingest: teams, gameweeks, positions, players
3. `fixtures` ingest
4. Read API: `/v1/bootstrap`, `/v1/players`, `/v1/fixtures`, `/v1/teams`
5. Scheduled ingest + idempotency tests
6. League and picks service

## Disclaimer

Not affiliated with or endorsed by the Premier League or Fantasy Premier League.
