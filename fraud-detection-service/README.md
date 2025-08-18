# Fraud Detection Service

A Spring Boot microservice for detecting and managing fraud alerts in card transactions.

## Features

- Real-time fraud detection
- Multiple fraud detection algorithms
- Kafka integration for transaction monitoring
- REST API for fraud alert management
- MySQL database integration
- Eureka service discovery
- Risk level assessment

## Technologies Used

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- MySQL 8.0
- Apache Kafka
- Netflix Eureka
- Spring Cloud Config

## API Endpoints

- POST /api/fraud/alerts - Create fraud alert
- GET /api/fraud/alerts - Get all fraud alerts
- GET /api/fraud/alerts/{id} - Get fraud alert by ID
- GET /api/fraud/alerts/card/{cardNumber} - Get alerts by card number
- PUT /api/fraud/alerts/{id} - Update fraud alert
- DELETE /api/fraud/alerts/{id} - Delete fraud alert
- POST /api/fraud/analyze - Analyze transaction for fraud

## Running the Service

1. Start MySQL server
2. Create database: cardmanagementdb
3. Start Kafka server
4. Run: mvn spring-boot:run
