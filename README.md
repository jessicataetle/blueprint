# Blueprint

A self-extending system design study platform. Build a portfolio of real, working system implementations that grow incrementally, daily, and automatically.

## Overview

Blueprint is a Java Spring WebFlux REST API service that maintains a library of classic distributed systems (messaging, rate limiting, URL shortening, job queues, notification pipelines) and automatically extends them on a daily schedule via an AI agent.

Instead of reading about system design, you have:
- **Working code** for each system (not just documentation)
- **Versioned schema** showing how it evolved
- **Design decision logs** explaining tradeoffs
- **AI-generated breakdowns** of design choices
- **Daily commits** showing realistic engineering work

**Perfect for:** System design interview prep, portfolio building, learning distributed systems through code.

---

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose
- Google Gemini API key (free)

### 1. Get Gemini API Key (2 minutes)

1. Visit **https://ai.google.dev/**
2. Click **"Get API Key"** button
3. Click **"Create API Key"** (it's free)
4. Copy your key
5. Set the environment variable:
   ```bash
   export GOOGLE_GEMINI_API_KEY=your_key_here
   ```

### 2. Start PostgreSQL

```bash
cd /Users/jtaetle/Documents/GitHub/blueprint
docker-compose up -d
```

Verify connection:
```bash
psql -h localhost -U blueprint_user -d blueprint -c "SELECT 1"
```

Password: `changeme`

### 3. Build & Run API

```bash
# Compile all modules
mvn clean compile

# Run the API server (port 8080)
mvn -pl blueprint-api spring-boot:run
```

The API will start at `http://localhost:8080`

Database migrations run automatically on startup.

### 4. Run the Agent (Optional)

In a separate terminal:
```bash
mvn -pl blueprint-agent spring-boot:run
```

The agent runs on port 8081 and executes the daily schedule at 2:00 AM UTC.

---

## Architecture

### Project Structure

```
blueprint/
├── blueprint-common/        # Shared domain models
│   └── src/main/java/com/blueprint/model/
│       ├── System.java              # System metadata
│       ├── Component.java           # Components (schema, api, worker, cache)
│       ├── ComponentVersion.java    # Versioned component content
│       ├── DesignDecision.java      # Design tradeoff notes
│       ├── AIBreakdown.java         # LLM-generated explanations
│       └── BuildLog.java            # Agent run history
│
├── blueprint-db/            # Database & migrations
│   └── src/main/resources/db/migration/
│       ├── V1__create_systems_table.sql
│       ├── V2__create_components_table.sql
│       ├── V3__create_component_versions_table.sql
│       ├── V4__create_design_decisions_table.sql
│       ├── V5__create_ai_breakdowns_table.sql
│       └── V6__create_build_log_table.sql
│
├── blueprint-api/           # REST API service
│   ├── controller/          # HTTP endpoints
│   ├── service/             # Business logic
│   ├── repository/          # R2DBC repositories
│   └── dto/                 # Request/response models
│
├── blueprint-agent/         # Daily scheduled agent
│   ├── scheduler/           # @Scheduled trigger
│   ├── orchestrator/        # Workflow coordination
│   ├── strategy/            # Code generation strategies
│   └── service/             # Git, Gemini integration
│
├── docker-compose.yml       # PostgreSQL setup
├── pom.xml                  # Parent POM (Maven)
├── IMPLEMENTATION_PLAN.md   # Detailed architecture decisions
├── PHASE_1_COMPLETE.md      # Phase 1 status
└── README.md                # This file
```

### Technology Stack

| Layer | Technology | Why |
|-------|-----------|-----|
| **Language** | Java 21 | Latest LTS, virtual threads, interview-relevant |
| **Framework** | Spring WebFlux | Reactive, non-blocking, modern |
| **Database** | PostgreSQL 15 | JSON support, versioning, mature |
| **DB Access** | Spring Data R2DBC | Reactive, non-blocking queries |
| **Migrations** | Flyway Community | SQL-based, free, simple |
| **Build** | Maven | Dependency management, multi-module |
| **AI** | Google Gemini API | Free tier (60 req/min), high quality |
| **Development** | Docker Compose | Reproducible local environment |

### Cost

| Component | Cost |
|-----------|------|
| Java, Spring, PostgreSQL, Maven, Docker | $0 |
| Google Gemini API (free tier) | $0 |
| **Total** | **$0/month** |

---

## REST API Endpoints

### Systems
```
POST   /systems                    Create system
GET    /systems                    List all systems
GET    /systems?category=X         Filter by category
GET    /systems/{id}               Get system details
PUT    /systems/{id}               Update system
DELETE /systems/{id}               Delete system
```

### Components
```
POST   /systems/{id}/components    Create component in system
GET    /systems/{id}/components    List system's components
PUT    /components/{id}            Update component
DELETE /components/{id}            Delete component
```

### Versions
```
POST   /components/{id}/versions           Create new version
GET    /components/{id}/versions           List all versions
GET    /components/{id}/versions/{vnum}    Get specific version
```

### Design Decisions
```
POST   /components/{id}/decisions  Add design decision
GET    /components/{id}/decisions  List decisions
```

### AI Breakdowns
```
POST   /components/{id}/breakdowns Add AI breakdown
GET    /components/{id}/breakdowns List breakdowns
```

### Build Logs
```
GET    /build-logs                 Query agent runs
GET    /build-logs/{id}            Get specific run
```

---

## How It Works

### Daily Agent Workflow

Every day at 2:00 AM UTC, the agent:

1. **Selects a system/component** (round-robin by age)
2. **Generates code** (migration, endpoint, or test stub with TODOs)
3. **Commits locally** to `/tmp/blueprint-systems`
4. **Calls Gemini API** to explain the design
5. **Stores breakdown** in database via REST API
6. **Logs the run** to build_log table

**Result:** A daily commit showing realistic, incremental system design work.

### Example Flow

```
Day 1:
  Agent selects: "Messaging System" → "API Component"
  Generates: MessagingController.java (stub with TODOs)
  Commit: "agent: messaging api endpoint"
  AI Breakdown: "This endpoint handles message creation..."

Day 2:
  Agent selects: "Rate Limiter" → "Schema Component"
  Generates: V001__add_rate_limits_table.sql
  Commit: "agent: rate_limiter schema migration"
  AI Breakdown: "This migration adds sliding window tracking..."
```

---

## Configuration

### Environment Variables

```bash
# Database
DB_PASSWORD=changeme

# Gemini API
GOOGLE_GEMINI_API_KEY=your_key_here

# Agent (optional)
AGENT_GIT_REPO_PATH=/tmp/blueprint-systems
```

### application.properties

**API** (`blueprint-api/src/main/resources/application.properties`):
```properties
server.port=8080
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/blueprint
spring.r2dbc.username=blueprint_user
spring.r2dbc.password=changeme
google.gemini.api-key=${GOOGLE_GEMINI_API_KEY}
google.gemini.model=gemini-1.5-pro
```

**Agent** (`blueprint-agent/src/main/resources/application.properties`):
```properties
server.port=8081
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/blueprint
agent.git-repo-path=/tmp/blueprint-systems
agent.blueprint-api-url=http://localhost:8080
```

---

## Development

### Build All Modules
```bash
mvn clean compile
```

### Build + Run Tests
```bash
mvn clean test
```

### Run Individual Service
```bash
# API server
mvn -pl blueprint-api spring-boot:run

# Agent
mvn -pl blueprint-agent spring-boot:run
```

### View Database
```bash
# Connect to PostgreSQL
psql -h localhost -U blueprint_user -d blueprint

# Query systems
SELECT * FROM systems;

# Query component versions
SELECT cv.version_number, cv.content FROM component_versions cv;

# Query AI breakdowns
SELECT model, tokens_used FROM ai_breakdowns;

# Query agent runs
SELECT status, COUNT(*) FROM build_log GROUP BY status;
```

### Clean Up
```bash
# Stop database
docker-compose down

# Remove all local data
docker-compose down -v

# Clean build artifacts
mvn clean
```

---

## Implementation Roadmap

| Phase | Weeks | Status | What's Built |
|-------|-------|--------|-------------|
| 1. Foundation | 1 | ✓ DONE | Maven structure, schemas, models |
| 2. REST API | 2-3 | → NEXT | All CRUD endpoints, services, controllers |
| 3. Agent + Gemini | 4 | Pending | Scheduler, code generation, AI integration |
| 4. Testing | 5 | Pending | Unit + integration tests |
| 5. Deployment | 6 | Pending | Docker, health checks, documentation |

**Current Status:** Phase 1 (Foundation) complete. Ready to build Phase 2 REST API.

---

## Next Steps

1. ✓ Set Gemini API key: `export GOOGLE_GEMINI_API_KEY=...`
2. ✓ Start PostgreSQL: `docker-compose up -d`
3. **→ Build REST API** (Phase 2): All CRUD endpoints for systems, components, versions
4. Build Agent (Phase 3): Daily scheduler + Gemini integration
5. Add Tests (Phase 4): Unit + integration tests
6. Deploy (Phase 5): Docker, production configuration

---

## Database Schema

### systems
```sql
id: UUID (PK)
name: VARCHAR (unique)
description: TEXT
category: VARCHAR (messaging, rate_limiter, url_shortener, etc.)
created_at: TIMESTAMP
updated_at: TIMESTAMP
```

### components
```sql
id: UUID (PK)
system_id: UUID (FK → systems)
type: VARCHAR (SCHEMA, API, WORKER, CACHE)
current_version: INT
created_at: TIMESTAMP
updated_at: TIMESTAMP
```

### component_versions
```sql
id: UUID (PK)
component_id: UUID (FK → components)
version_number: INT
content: TEXT (full code/schema)
diff: TEXT (unified diff from previous)
created_at: TIMESTAMP
UNIQUE(component_id, version_number)
```

### design_decisions
```sql
id: UUID (PK)
component_version_id: UUID (FK → component_versions)
content: TEXT (design tradeoff notes)
created_at: TIMESTAMP
```

### ai_breakdowns
```sql
id: UUID (PK)
component_version_id: UUID (FK → component_versions)
prompt: TEXT (what we asked Gemini)
response: TEXT (what Gemini said)
model: VARCHAR (gemini-1.5-pro)
tokens_used: INT
created_at: TIMESTAMP
```

### build_log
```sql
id: UUID (PK)
agent_run_id: UUID (identifies single run)
system_id: UUID (FK → systems)
component_id: UUID (FK → components)
action: VARCHAR (migration, endpoint, test, decision, breakdown)
status: VARCHAR (success, failure)
output: TEXT (error message or generated code diff)
created_at: TIMESTAMP
```

---

## Troubleshooting

### PostgreSQL Connection Error
```
Error: Connection refused at localhost:5432
```
**Solution:** Start Docker: `docker-compose up -d`

### Gemini API Key Error
```
Error: GOOGLE_GEMINI_API_KEY not set
```
**Solution:** Set the environment variable:
```bash
export GOOGLE_GEMINI_API_KEY=your_key_here
```

### Build Failure: "Cannot find symbol"
```
[ERROR] cannot find symbol: class Table
```
**Solution:** Run `mvn clean` then rebuild: `mvn clean compile`

### Flyway Migration Fails
```
Error: Relation "systems" does not exist
```
**Solution:** Ensure PostgreSQL is running and migrations ran:
```bash
mvn -pl blueprint-api spring-boot:run
# Check logs for "Flyway: Successfully validated"
```

---

## Contributing

This is a personal portfolio project. To extend:

1. Add new systems in Phase 2 (new system CRUD endpoints)
2. Improve code generation in Phase 3 (generate more realistic stubs)
3. Add more tests in Phase 4
4. Deploy to cloud in Phase 5

---

## License

Educational use. MIT license.

---

**Status:** Phase 1 (Foundation) complete. Build is ✓ successful.

**Next:** Phase 2 (REST API endpoints) — All CRUD operations for systems, components, and versions.
