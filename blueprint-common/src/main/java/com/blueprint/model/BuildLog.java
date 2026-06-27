package com.blueprint.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("build_log")
public class BuildLog {
    @Id
    private UUID id;

    @Column("agent_run_id")
    private UUID agentRunId;

    @Column("system_id")
    private UUID systemId;

    @Column("component_id")
    private UUID componentId;

    private String action;

    private String status;

    private String output;

    @Column("created_at")
    private LocalDateTime createdAt;
}