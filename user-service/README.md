# User Service

User management microservice for the Card Management System.

## Features

- CRUD operations for users
- User status management (ACTIVE, INACTIVE, SUSPENDED)
- Email and phone number validation
- Search functionality
- RESTful API endpoints
- MySQL database integration
- Eureka service discovery
- Spring Cloud Config integration

## API Endpoints

### User Management
- `POST /api/users` - Create a new user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `PATCH /api/users/{id}/status` - Update user status

### Search & Filter
- `GET /api/users/status/{status}` - Get users by status
- `GET /api/users/search?name={name}` - Search users by name

## Prerequisites

- Java 17
- Maven 3.6+
- MySQL 8.0+
- Eureka Server (running on port 8761)
- Config Server (running on port 8888)

## Running the Application

1. Start MySQL and create database `card_management_db`
2. Update database credentials in `application.properties`
3. Run the application:
