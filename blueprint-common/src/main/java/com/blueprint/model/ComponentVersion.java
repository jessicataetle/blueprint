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
@Table("component_versions")
public class ComponentVersion {
    @Id
    private UUID id;

    @Column("component_id")
    private UUID componentId;

    @Column("version_number")
    private Integer versionNumber;

    private String content;

    private String diff;

    @Column("created_at")
    private LocalDateTime createdAt;
}