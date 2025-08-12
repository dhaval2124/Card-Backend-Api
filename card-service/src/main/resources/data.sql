USE cardmanagementdb;

INSERT INTO cards (card_number, card_holder_name, customer_id, card_type, expiry_date, cvv, credit_limit, available_balance, status)
VALUES
    ('1234567890123456', 'John Doe', 1001, 'CREDIT', '12/25', '123', 50000.00, 45000.00, 'ACTIVE'),
    ('9876543210987654', 'Jane Smith', 1002, 'DEBIT', '06/26', '456', 25000.00, 20000.00, 'ACTIVE'),
    ('5555666677778888', 'Bob Johnson', 1003, 'CREDIT', '03/27', '789', 75000.00, 70000.00, 'ACTIVE');
