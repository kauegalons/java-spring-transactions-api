# Transactions Statistics API

REST API that receives financial transactions and returns real-time statistics for the last 60 seconds, built as a solution to **Itaú Unibanco's backend technical challenge**.

All data is stored **in memory** — no database or cache systems are used, as required by the challenge.

> **Note:** endpoint paths, JSON fields and domain names are in Portuguese (e.g. `/transacao`, `valor`, `dataHora`) to strictly follow the challenge specification. Documentation is in English.

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Lombok
- Maven
- JUnit 5

## Architecture

The project follows a **layered architecture**, keeping responsibilities clearly separated:

```
controller  → HTTP layer: receives requests and returns responses
service     → business rules and validations
repository  → thread-safe in-memory storage
dto         → request/response contracts
model       → domain entities
exception   → centralized error handling
```

## API Endpoints

### `POST /transacao` — Create a transaction

```json
{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

Validation rules:
- `valor` and `dataHora` are required
- `valor` must be greater than or equal to `0`
- `dataHora` must not be in the future

| Status | Description |
|--------|-------------|
| `201 Created` | Transaction accepted and stored |
| `422 Unprocessable Entity` | Transaction failed one or more validation rules |
| `400 Bad Request` | Malformed or unreadable request |

### `DELETE /transacao` — Delete all transactions

| Status | Description |
|--------|-------------|
| `200 OK` | All transactions deleted |

### `GET /estatistica` — Get statistics

Returns statistics for transactions that occurred in the **last 60 seconds**:

```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

If there are no transactions in that time window, all values are returned as `0`.

| Status | Description |
|--------|-------------|
| `200 OK` | Statistics successfully calculated |

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+ (or use the included Maven Wrapper)

### Running the application

```bash
git clone https://github.com/kauegalons/java-spring-transactions-api.git
cd java-spring-transactions-api
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Running the tests

```bash
./mvnw test
```

## Roadmap

- [x] `POST /transacao` endpoint with validation rules
- [x] Thread-safe in-memory storage (`ConcurrentLinkedQueue`)
- [x] `DELETE /transacao` endpoint
- [x] `GET /estatistica` endpoint (last 60 seconds, `BigDecimal` precision)
- [ ] Unit and integration tests (including error scenarios)
- [x] Centralized error handling (`400 Bad Request` without body for malformed JSON)
- [ ] Configurable statistics time window
- [ ] Logging
- [ ] Health check (Spring Boot Actuator)
- [ ] API documentation (Swagger / OpenAPI)
- [ ] Docker support

## Author

**Kauê Galon Silva**

- GitHub: [@kauegalons](https://github.com/kauegalons)
