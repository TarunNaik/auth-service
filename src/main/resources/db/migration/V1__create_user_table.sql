-- V1__create_user_table.sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL
);

-- Optional: add index for faster lookups by email
CREATE INDEX idx_users_email ON users(email);

