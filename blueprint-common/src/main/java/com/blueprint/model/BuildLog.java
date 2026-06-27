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
 * Record of each agent run (daily extension of systems).
 * agentRunId groups all actions from a single agent execution.
 * output stores error messages (failure) or generated code diff (success).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("build_log")
public class BuildLog {
    @Id
    private UUID id;

    @Column("agent_run_id")
    private UUID agentRunId;  // Groups all events from single agent run

    @Column("system_id")
    private UUID systemId;  // Which system was extended (nullable if query failed)

    @Column("component_id")
    private UUID componentId;  // Which component was extended (nullable if query failed)

    private String action;  // What was done: "migration", "endpoint", "test", "decision", "breakdown"

    private String status;  // Outcome: "success" or "failure"

    private String output;  // On failure: error message. On success: generated code diff

    @Column("created_at")
    private LocalDateTime createdAt;  // Timestamp of this log entry
}