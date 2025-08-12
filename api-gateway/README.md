# API Gateway - Card Management System

Spring Cloud Gateway application serving as the entry point for all microservices in the Card Management System.

## Features

- **Spring Cloud Gateway** for intelligent routing and load balancing
- **Eureka Service Discovery** integration for dynamic service resolution
- **Config Server** integration for externalized configuration management
- **MySQL** integration for logging and monitoring
- **JWT Authentication** with custom security filters
- **Request/Response Logging** for audit and debugging
- **CORS Support** for cross-origin requests
- **Circuit Breaker** pattern for resilience
- **Health Checks** and monitoring endpoints

## Architecture

The API Gateway acts as a single entry point that routes requests to appropriate microservices:


## Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Eureka Server (running on port 8761)
- Config Server (running on port 8888)

## Quick Start

### 1. Database Setup



### 2. Build Application



### 3. Run Application



## Configuration

### Eureka Client Setup
The gateway automatically registers with Eureka server and discovers other services:


### MySQL Setup
Database connection for logging and monitoring:



## API Routes

| Path | Target Service | Port | Authentication |
|------|---------------|------|----------------|
| `/api/users/**` | user-service | 8081 | Required* |
| `/api/cards/**` | card-service | 8082 | Required |
| `/api/transactions/**` | transaction-service | 8083 | Required |
| `/api/payments/**` | payment-service | 8084 | Required |
| `/api/notifications/**` | notification-service | 8085 | Optional |
| `/api/fraud/**` | fraud-detection-service | 8086 | Required |
| `/api/auth/**` | user-service | 8081 | Not Required |

*Except for login/register endpoints

## Authentication

Include JWT token in the Authorization header:



Public endpoints (no authentication required):
- `/api/auth/login`
- `/api/auth/register`
- `/api/users/signup`
- `/actuator/**`

## Monitoring

### Health Check

### Gateway Routes

### Service Registry

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Gateway port | 8080 |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://localhost:8761/eureka/ |
| `SPRING_DATASOURCE_URL` | Database URL | jdbc:mysql://localhost:3306/cardmanagementdb |
| `JWT_SECRET` | JWT signing secret | cardManagementSecretKeyForJWTTokenGenerationAndValidation |

## Development

### Running with Docker

### Testing

### Logs
The gateway provides detailed request/response logging:

## Troubleshooting

### Common Issues

1. **Service Discovery Issues**
    - Ensure Eureka server is running
    - Check service registration in Eureka dashboard

2. **Authentication Failures**
    - Verify JWT token is valid and not expired
    - Check JWT secret configuration

3. **Database Connection Issues**
    - Verify MySQL is running and accessible
    - Check database credentials and permissions

### Support

For issues and questions, please check the logs and verify all dependencies are running correctly.
