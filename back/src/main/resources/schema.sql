CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(255),
    category VARCHAR(255),
    price DECIMAL(10, 2),
    quantity INT,
    internal_reference VARCHAR(255),
    shell_id INT,
    inventory_status VARCHAR(50),
    rating INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

ALTER TABLE product ADD CONSTRAINT unique_code UNIQUE (code);
