CREATE TABLE IF NOT EXISTS currency_exchange (
    id BIGINT PRIMARY KEY,
    currency_from VARCHAR(255),
    currency_to VARCHAR(255),
    conversion_multiple DECIMAL(15, 6),
    environment VARCHAR(255)
);
