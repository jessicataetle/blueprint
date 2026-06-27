-- Create component_versions table
CREATE TABLE component_versions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    component_id UUID NOT NULL REFERENCES components(id) ON DELETE CASCADE,
    version_number INT NOT NULL,
    content TEXT NOT NULL,
    diff TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(component_id, version_number)
);

CREATE INDEX idx_component_versions_component_id ON component_versions(component_id);