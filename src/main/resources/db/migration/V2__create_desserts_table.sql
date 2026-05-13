CREATE TABLE desserts (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_subscribed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_desserts_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE INDEX idx_desserts_owner_id ON desserts(owner_id);
