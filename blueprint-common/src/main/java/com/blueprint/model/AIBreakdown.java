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
 * LLM-generated explanation of a component's design.
 * Stores both prompt and response for traceability and reproducibility.
 * tokens_used tracks cost (important for quota management on free tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ai_breakdowns")
public class AIBreakdown {
    @Id
    private UUID id;

    @Column("component_version_id")
    private UUID componentVersionId;  // Foreign key: many breakdowns per version

    private String prompt;  // What we asked the LLM (for reproducibility)

    private String response;  // LLM's explanation of the design

    private String model;  // LLM model used (e.g., "gemini-1.5-pro", "claude-sonnet-4-6")

    @Column("tokens_used")
    private Integer tokensUsed;  // Track cost and quota usage

    @Column("created_at")
    private LocalDateTime createdAt;  // When this explanation was generated
}