-- Sample payment data
INSERT INTO payments (card_id, user_id, amount, transaction_type, merchant_name, description, status, transaction_id) VALUES
                                                                                                                          (1, 1, 100.50, 'DEBIT', 'Amazon', 'Purchase of electronics', 'SUCCESS', 'TXN_AMZ001'),
                                                                                                                          (1, 1, 50.75, 'DEBIT', 'Starbucks', 'Coffee purchase', 'SUCCESS', 'TXN_SBX001'),
                                                                                                                          (2, 2, 200.00, 'DEBIT', 'Walmart', 'Grocery shopping', 'SUCCESS', 'TXN_WMT001'),
                                                                                                                          (2, 2, 25.00, 'CREDIT', 'Refund Store', 'Product return refund', 'SUCCESS', 'TXN_REF001'),
                                                                                                                          (3, 3, 300.25, 'DEBIT', 'Best Buy', 'Electronics purchase', 'PENDING', 'TXN_BBY001'),
                                                                                                                          (3, 3, 75.50, 'DEBIT', 'Gas Station', 'Fuel purchase', 'FAILED', 'TXN_GAS001'),
                                                                                                                          (1, 1, 15.99, 'DEBIT', 'Netflix', 'Monthly subscription', 'SUCCESS', 'TXN_NFX001'),
                                                                                                                          (2, 2, 500.00, 'CREDIT', 'Salary Deposit', 'Monthly salary credit', 'SUCCESS', 'TXN_SAL001');
