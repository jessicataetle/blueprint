package com.blueprint.model;

/**
 * Types of components in a distributed system.
 * SCHEMA: Database schema and migrations (DDL)
 * API: REST endpoints or async handlers
 * WORKER: Background job processors
 * CACHE: Caching layer implementations
 */
public enum ComponentType {
    SCHEMA,  // Database schema definitions
    API,     // REST endpoints, request handlers
    WORKER,  // Background workers, job processors
    CACHE    // Cache implementations (Redis, in-memory, etc.)
}