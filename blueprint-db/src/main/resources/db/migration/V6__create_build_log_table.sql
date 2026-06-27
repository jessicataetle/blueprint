-- Create build_log table
CREATE TABLE build_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agent_run_id UUID NOT NULL,
    system_id UUID REFERENCES systems(id) ON DELETE SET NULL,
    component_id UUID REFERENCES components(id) ON DELETE SET NULL,
    action VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    output TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_build_log_agent_run ON build_log(agent_run_id);
CREATE INDEX idx_build_log_system_id ON build_log(system_id);
CREATE INDEX idx_build_log_created_at ON build_log(created_at);