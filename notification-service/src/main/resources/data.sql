-- Insert sample notification data
INSERT INTO notifications (customer_id, type, channel, recipient, message, subject, status, created_at) VALUES
                                                                                                            ('CUST001', 'EMAIL', 'CARD_ISSUED', 'customer1@example.com', 'Your new card has been issued successfully.', 'Card Issued Successfully', 'SENT', NOW()),
                                                                                                            ('CUST002', 'EMAIL', 'CARD_ACTIVATED', 'customer2@example.com', 'Your card has been activated successfully.', 'Card Activated', 'SENT', NOW()),
                                                                                                            ('CUST003', 'EMAIL', 'TRANSACTION_ALERT', 'customer3@example.com', 'Transaction Alert: A transaction has been processed.', 'Transaction Alert', 'PENDING', NOW());
