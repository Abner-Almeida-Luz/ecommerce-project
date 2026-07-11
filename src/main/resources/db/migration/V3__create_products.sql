CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    stock INTEGER NOT NULL,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);