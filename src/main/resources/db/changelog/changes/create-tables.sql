--liquibase formatted sql

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50) DEFAULT 'ROLE_USER'
);

CREATE TABLE url_mappings (
    id BIGSERIAL PRIMARY KEY,
    original_url TEXT,
    short_url VARCHAR(255),
    click_count INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT
);

CREATE TABLE click_events (
    id BIGSERIAL PRIMARY KEY,
    click_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    url_mapping_id BIGINT
);