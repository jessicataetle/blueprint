package com.blueprint.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A component is a part of a system (e.g., schema, API, worker, cache).
 * One system contains many components. Each component evolves through versions.
 * currentVersion is denormalized for quick lookups (avoid JOIN to find latest).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("components")
public class Component {
    @Id
    private UUID id;

    private UUID systemId;  // Foreign key: many components per system

    private ComponentType type;  // SCHEMA | API | WORKER | CACHE

    private Integer currentVersion;  // Denormalized for performance (avoid SELECT MAX(version))

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}