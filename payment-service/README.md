# Payment Service

Payment service for the Card Management System microservices architecture.

## Features

- Process payments (DEBIT, CREDIT, REFUND)
- Track payment status and history
- Integration with User Service via Feign Client
- Service discovery with Eureka
- Centralized configuration
- MySQL database integration

## Endpoints

### Payment Operations
- `POST /api/payments` - Process a new payment
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/transaction/{transactionId}` - Get payment by transaction ID
- `PUT /api/payments/{id}/status` - Update payment status
- `DELETE /api/payments/{id}` - Delete payment

### Query Operations
- `GET /api/payments` - Get all payments
- `GET /api/payments/card/{cardId}` - Get payments by card ID
- `GET /api/payments/user/{userId}` - Get payments by user ID
- `GET /api/payments/card/{cardId}/user/{userId}` - Get payments by card and user
- `GET /api/payments/status/{status}` - Get payments by status
- `GET /api/payments/type/{transactionType}` - Get payments by transaction type
- `GET /api/payments/merchant/{merchantName}` - Get payments by merchant
- `GET /api/payments/card/{cardId}/daterange` - Get payments by date range
- `GET /api/payments/user/{userId}/daterange` - Get user payments by date range

## Running the Application

1. Ensure MySQL is running with the database `cardmanagementdb`
2. Run Eureka Server (port 8761)
3. Run Config Server (port 8888) - optional
4. Run the application: `mvn spring-boot:run`

The service will start on port 8083.

## Sample Postman Requests

### Process Payment
