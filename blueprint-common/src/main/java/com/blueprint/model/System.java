package com.blueprint.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a distributed system (e.g., messaging, rate limiter, URL shortener).
 * Uses UUID for distributed identification (avoid centralized ID generation).
 * Tracks createdAt/updatedAt for audit trail and portfolio versioning.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("systems")
public class System {
    @Id
    private UUID id;  // Distributed ID, no sequence dependency
    private String name;
    private String description;
    private String category;  // Examples: "messaging", "rate_limiter", "url_shortener"
    private LocalDateTime createdAt;  // Immutable creation timestamp
    private LocalDateTime updatedAt;  // Mutable for tracking last modification
}