# EazyBank — Spring Boot Microservices

Three independently deployable Spring Boot services — **Accounts**, **Loans** and **Cards** — that
together model a small retail-banking backend. Each service owns its own data, exposes a documented
REST API, and ships as its own container image.

Built in June–July 2025 while working through Spring Boot microservices patterns, using the
[EazyBytes](https://www.eazybytes.com) "EazyBank" course domain as the problem space.

| | |
|---|---|
| **Stack** | Java 21, Spring Boot 3.5.3, Spring Data JPA, Hibernate, Maven |
| **API docs** | springdoc-openapi 2.8.9 (Swagger UI) |
| **Persistence** | H2 in-memory, schema-per-service |
| **Ops** | Spring Boot Actuator, Docker, Docker Compose |
| **Services** | `accounts` (:8080) · `loans` (:8090) · `cards` (:9000) |

---

## Services

| Service | Port | Owns | Image |
|---|---|---|---|
| **accounts** | 8080 | `customer` + `accounts` — customer registration and their savings account | `kirlmen/accounts:s4` |
| **loans** | 8090 | `loans` — loan records, totals, amount paid, outstanding balance | `kirlmen/loans:s4` |
| **cards** | 9000 | `cards` — card records, credit limit, amount used, available balance | `kirlmen/cards:s4` |

Each service is a standalone Maven project with its own `pom.xml`, database schema and lifecycle.
There is no shared database and no shared parent POM — services are coupled only by the customer's
mobile number, which acts as the cross-service business key.

## API

All three services follow the same contract under `/api`, so the shape is predictable across the
estate:

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/create` | Create the record for a mobile number |
| `GET` | `/api/fetch?mobileNumber=` | Fetch the record for a mobile number |
| `PUT` | `/api/update` | Update the record (validated request body) |
| `DELETE` | `/api/delete?mobileNumber=` | Delete the record |

Interactive documentation is generated from the code and served per service:

- Swagger UI — `http://localhost:{port}/swagger-ui.html`
- OpenAPI spec — `http://localhost:{port}/v3/api-docs`
- Actuator health — `http://localhost:{port}/actuator/health`

Mobile numbers are validated with `@Pattern` at the boundary; `@Valid` request bodies are checked
before they ever reach a service method.

## Running it

### With Docker Compose (all three services)

```bash
docker compose -f accounts/docker-compose.yml up -d
```

Brings up all three services on a shared `eazybank` bridge network with a 700 MB memory limit each.

### Locally, one service at a time

```bash
cd accounts && ./mvnw spring-boot:run
```

Requires **JDK 21**. Each service starts with a fresh in-memory H2 database seeded from its own
`schema.sql`; the H2 console is enabled at `/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, user `sa`,
no password).

### Building images

The three services deliberately use three different containerization approaches:

| Service | Approach | Command |
|---|---|---|
| accounts | Hand-written `Dockerfile` | `docker build -t kirlmen/accounts:s4 .` |
| loans | Cloud Native Buildpacks | `./mvnw spring-boot:build-image` |
| cards | Google Jib (daemonless) | `./mvnw compile jib:dockerBuild` |

## Architecture

Every service uses the same layered structure, so moving between them costs nothing:

```
controller/   REST endpoints, request validation, OpenAPI annotations
service/      interface + impl — business rules, no persistence details
repository/   Spring Data JPA interfaces with derived queries
entity/       JPA entities extending a shared BaseEntity
dto/          request/response contracts, decoupled from entities
mapper/       explicit entity <-> DTO conversion
exception/    domain exceptions + @ControllerAdvice handler
audit/        AuditorAware implementation for JPA auditing
constants/    status codes and messages, no magic strings
```

**Details worth a look**

- **Entity/DTO separation** — entities never cross the HTTP boundary. Explicit mappers convert in
  both directions, so the database schema and the public API can evolve independently.
- **JPA auditing** — `BaseEntity` carries `createdAt` / `createdBy` / `updatedAt` / `updatedBy`,
  populated automatically via `@EnableJpaAuditing` and an `AuditorAware` implementation. The columns
  are marked `updatable = false` and `insertable = false` respectively, so create and update stamps
  cannot overwrite each other.
- **Consistent error contract** — a `@ControllerAdvice` per service returns a structured
  `ErrorResponseDto` (API path, status, message, timestamp) for domain exceptions, and overrides
  `handleMethodArgumentNotValid` to return a field-name → message map for validation failures rather
  than Spring's default payload.
- **Domain exceptions** — `ResourceNotFoundException`, `CustomerAlreadyExistException`,
  `LoanAlreadyExistsException` and `CardAlreadyExistsException` map to 404 / 400 rather than leaking
  500s.
- **Constants over magic strings** — each service has a final, non-instantiable constants class
  holding status codes and response messages.

## Status

This is a learning build, and a few things reflect that. Persistence is in-memory H2, so data does
not survive a restart. There is no service discovery, gateway, config server, or inter-service
communication yet — the services are independent by design at this stage. Test coverage is limited
to the generated context-load tests. The auditor is a hardcoded per-service string, pending a Spring
Security implementation.

---

**Volkan** · [github.com/Kirlmen](https://github.com/Kirlmen)
