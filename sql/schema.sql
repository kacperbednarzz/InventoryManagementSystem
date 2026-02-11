CREATE DATABASE warehouse_db;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INTEGER DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO products (name, quantity, price) VALUES
('Laptop', 10, 3500.00),
('Myszka', 50, 120.50);