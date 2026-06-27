-- Create ai_breakdowns table
CREATE TABLE ai_breakdowns (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    component_version_id UUID NOT NULL REFERENCES component_versions(id) ON DELETE CASCADE,
    prompt TEXT NOT NULL,
    response TEXT NOT NULL,
    model VARCHAR(50) NOT NULL,
    tokens_used INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ai_breakdowns_version_id ON ai_breakdowns(component_version_id);