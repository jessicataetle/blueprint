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
@Table("design_decisions")
public class DesignDecision {
    @Id
    private UUID id;

    @Column("component_version_id")
    private UUID componentVersionId;

    private String content;

    @Column("created_at")
    private LocalDateTime createdAt;
}