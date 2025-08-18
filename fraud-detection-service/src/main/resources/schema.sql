CREATE DATABASE IF NOT EXISTS cardmanagementdb;

USE cardmanagementdb;

CREATE TABLE IF NOT EXISTS fraud_alerts (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            card_number VARCHAR(20) NOT NULL,
    transaction_amount DECIMAL(10,2) NOT NULL,
    merchant_name VARCHAR(255) NOT NULL,
    transaction_location VARCHAR(255) NOT NULL,
    fraud_type ENUM('SUSPICIOUS_AMOUNT', 'UNUSUAL_LOCATION', 'MULTIPLE_TRANSACTIONS', 'MERCHANT_BLACKLIST', 'VELOCITY_CHECK') NOT NULL,
    risk_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') NOT NULL,
    detected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'INVESTIGATED', 'RESOLVED', 'FALSE_POSITIVE') DEFAULT 'PENDING',
    description TEXT,
    INDEX idx_card_number (card_number),
    INDEX idx_detected_at (detected_at),
    INDEX idx_status (status),
    INDEX idx_risk_level (risk_level)
    );
