CREATE TABLE orders(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    status TEXT NOT NULL CHECK ( status in ('pending','paid','shipping','delivered','cancelled') ),
    total DECIMAL(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);