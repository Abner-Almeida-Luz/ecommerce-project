CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK ( role IN ('admin','user') ),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);