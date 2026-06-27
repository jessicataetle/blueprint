package com.blueprint.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable history of a component's evolution. Each version is append-only.
 * content: Full code/schema/migration at this version
 * diff: Unified diff from previous version (computed once, stored for history)
 * Unique constraint on (component_id, version_number) prevents duplicate versions
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("component_versions")
public class ComponentVersion {
    @Id
    private UUID id;

    @Column("component_id")
    private UUID componentId;  // Foreign key: many versions per component

    @Column("version_number")
    private Integer versionNumber;  // Immutable sequence (1, 2, 3, ...)

    private String content;  // Full code/SQL/config at this version

    private String diff;  // Unified diff from previous (computed once, immutable)

    @Column("created_at")
    private LocalDateTime createdAt;  // Only creation time (versions don't update)
}