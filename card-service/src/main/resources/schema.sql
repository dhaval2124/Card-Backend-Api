USE cardmanagementdb;

CREATE TABLE IF NOT EXISTS cards (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     card_number VARCHAR(16) NOT NULL UNIQUE,
    card_holder_name VARCHAR(100) NOT NULL,
    customer_id BIGINT NOT NULL,
    card_type VARCHAR(50) NOT NULL,
    expiry_date VARCHAR(5) NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    credit_limit DECIMAL(15,2),
    available_balance DECIMAL(15,2),
    status ENUM('ACTIVE', 'BLOCKED', 'EXPIRED', 'CANCELLED') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
    );
