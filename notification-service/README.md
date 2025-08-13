# Notification Service

Card Management System - Notification Microservice built with Spring Boot 3.5.4 and Java 17.

## Features

- **Kafka Integration**: Consumes events from other microservices
- **Email Notifications**: Sends automated emails using Spring Mail
- **Database Persistence**: Stores notification history in MySQL
- **Service Discovery**: Integrates with Eureka for service registration
- **RESTful APIs**: Provides endpoints for notification management

## Prerequisites

- Java 17+
- MySQL 8.0+
- Apache Kafka 2.8+
- Maven 3.6+

## Setup Instructions

### 1. Database Setup
- CREATE DATABASE cardmanagementdb;

### 2. Kafka Setup
Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

Start Kafka Server
bin/kafka-server-start.sh config/server.properties

Create Topics
bin/kafka-topics.sh --create --topic card-issued-topic --bootstrap-server localhost:9092 --partitions 3
bin/kafka-topics.sh --create --topic card-activated-topic --bootstrap-server localhost:9092 --partitions 3
bin/kafka-topics.sh --create --topic transaction-alert-topic --bootstrap-server localhost:9092 --partitions 3

### 3. Application Configuration
Update `application.properties` with your email credentials:

spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password


### 4. Build and Run

mvn clean install
mvn spring-boot:run


## API Endpoints

- `POST /api/notifications/send` - Send notification
- `GET /api/notifications/customer/{customerId}` - Get customer notifications
- `GET /api/notifications/pending` - Get pending notifications
- `POST /api/notifications/retry-failed` - Retry failed notifications
- `GET /api/notifications/health` - Health check

## Testing with Postman

Import the provided Postman collection for testing all endpoints.
