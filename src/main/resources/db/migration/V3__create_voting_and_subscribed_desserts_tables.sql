CREATE TABLE voting (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    number_of_participants INTEGER DEFAULT 0,
    is_open_to_vote BOOLEAN DEFAULT FALSE,
    closing_date TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_voting_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE subscribed_desserts (
    dessert_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number_of_votes INTEGER DEFAULT 0,
    vote_position INTEGER DEFAULT 0,
    voting_id UUID NOT NULL,
    CONSTRAINT fk_subscribed_desserts_voting FOREIGN KEY (voting_id) REFERENCES voting(id)
);

CREATE INDEX idx_subscribed_desserts_voting_id ON subscribed_desserts(voting_id);
