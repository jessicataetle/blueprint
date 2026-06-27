package com.blueprint.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Free-text design tradeoff notes for a component version.
 * Attached to a specific version to show reasoning at that point in time.
 * Example: "Used sliding window over token bucket for simplicity at scale"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("design_decisions")
public class DesignDecision {
    @Id
    private UUID id;

    private UUID componentVersionId;  // Foreign key: many decisions per version

    private String content;  // Why this design was chosen (tradeoffs, constraints)

    private LocalDateTime createdAt;  // When this decision was documented
}