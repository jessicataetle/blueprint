# Blueprint

A backend service for studying system design by building it — incrementally, daily, and in code.

---

## Concept

Blueprint is a self-extending system design study platform. It contains a library of classic distributed systems (messaging, rate limiting, URL shortening, job queues, notification pipelines) and an AI-powered agent that extends them automatically on a daily schedule — adding schema migrations, API endpoints, tests, and design notes one commit at a time.

The project is the study material. Instead of reading about how a rate limiter works, you have a working rate limiter codebase that grew one meaningful commit per day. Instead of flashcards about database schema design, you have a versioned migration history you can read like a changelog.

---

## Goals

- Build a portfolio of real, working system implementations that grow over time
- Practice API layer and database design across multiple problem domains
- Generate daily commits that reflect genuine engineering work
- Prepare for AI engineering interviews by building the kinds of systems those interviews test

---

## Architecture

### API Layer

A REST API that exposes:

- **Systems** — CRUD for registered systems (messaging, rate limiter, etc.)
- **Components** — the building blocks of each system (schema, endpoints, workers, caches)
- **Snapshots** — point-in-time captures of a system's design state
- **Design decisions** — logged tradeoffs and notes attached to each component
- **AI breakdowns** — stored LLM-generated explanations of design choices, queryable by system and component

### Database

PostgreSQL. Core tables:

- `systems` — name, description, category, created_at
- `components` — belongs to a system; type (schema, api, worker, cache), current version
- `component_versions` — full content of a component at each version, with a diff from the previous
- `design_decisions` — free-text tradeoff notes attached to a component version
- `ai_breakdowns` — LLM-generated analysis stored per component version; prompt, response, model, timestamp
- `build_log` — record of every agent run: what it did, what it changed, whether it succeeded

### AI Agent

A scheduled job (daily) that:

1. Picks a system and component to extend based on a priority queue
2. Generates the next logical increment — a new migration, a new endpoint, a new test
3. Writes the code to disk and commits it
4. Calls the LLM to generate a design breakdown of what was just added
5. Stores the breakdown in the database via the API
6. Logs the run to `build_log`

The agent is intentionally incremental — each run does one small, meaningful thing rather than generating large amounts of code at once. This keeps commits realistic and the codebase readable.

---

## Systems Included (Initial Set)

| System | What It Covers |
|---|---|
| Messaging service | Users, channels, messages, threads, read receipts, fan-out |
| Rate limiter | Sliding window, token bucket, per-user and per-endpoint limits |
| URL shortener | Hashing, redirects, click analytics, expiration |
| Notification service | Delivery channels, user preferences, retry logic, deduplication |
| Job queue | Task scheduling, retries, dead letter queues, worker concurrency |

---

## Daily Commit Pattern

Each day the agent targets one component in one system and does one of:

- Adds or modifies a database migration
- Adds or extends an API endpoint
- Writes or expands a unit or integration test
- Adds a design decision note
- Generates and stores an AI breakdown of an existing component

Over time the commit history reads like a real engineering team incrementally building out a platform — because that is what it is.

---

## Interview Talking Points

- "I built a platform that incrementally implements classic system design problems as real working code."
- The database schema itself is an interview topic — versioned components, append-only design decision logs, time-series build logs.
- The AI agent covers LLM integration patterns, prompt management, structured output storage, and scheduled job design.
- Each sub-system (messaging, rate limiter, etc.) can be discussed independently as a system design answer with real code behind it.

---

## Tech Stack

- **Language:** Java (interview-aligned) or TypeScript
- **Database:** PostgreSQL
- **API:** REST (Spring Boot or Express)
- **Agent:** Cron job or scheduled GitHub Action
- **LLM:** Anthropic API (claude-sonnet-4-6)
- **Version control:** GitHub, one repo per system or monorepo with subdirectories
