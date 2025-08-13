-- Create notifications table
CREATE TABLE IF NOT EXISTS notifications (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             customer_id VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    channel VARCHAR(100) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    subject VARCHAR(500) NOT NULL,
    status ENUM('PENDING', 'SENT', 'FAILED', 'RETRY') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent_at TIMESTAMP NULL,
    error_message TEXT NULL,
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
    );
