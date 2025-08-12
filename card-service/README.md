# Card Management Service

A comprehensive Spring Boot microservice for managing credit and debit cards in a banking system, built with modern Java technologies and microservices architecture.

## 🚀 Features

- **CRUD Operations**: Create, read, update, and delete cards
- **Card Management**: Block/activate cards, update card details
- **Customer Integration**: Query cards by customer ID
- **Validation**: Comprehensive input validation with proper error handling
- **Microservices Ready**: Eureka client integration for service discovery
- **Database Integration**: MySQL with JPA/Hibernate
- **RESTful APIs**: Well-structured REST endpoints with proper HTTP status codes

## 🛠 Technology Stack

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Cloud (Eureka Client)**
- **MySQL 8.0**
- **Maven**
- **Hibernate**

## 📋 Prerequisites

Before running this application, ensure you have:

- Java 17 or higher installed
- Maven 3.6+ installed
- MySQL 8.0+ running
- Postman (for API testing)

## 🔧 Setup Instructions

### 1. Database Setup

-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE IF NOT EXISTS cardmanagementdb;
USE cardmanagementdb;

-- The application will auto-create tables on first run
-- Or run the schema.sql script for manual setup

### 2. Application Configuration

Update `src/main/resources/application.properties`:

Update with your MySQL credentials
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD


### 3. Build and Run

Clone the repository
git clone <your-repo-url>
cd card-service

Build the application
mvn clean install

Run the application
mvn spring-boot:run

Alternative: Run as JAR
java -jar target/card-service-0.0.1-SNAPSHOT.jar


The application will start on `http://localhost:8080`

## 📚 API Documentation

### Base URL

http://localhost:8080/api/cards



### Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/cards` | Get all cards | None |
| GET | `/api/cards/{id}` | Get card by ID | None |
| GET | `/api/cards/number/{cardNumber}` | Get card by number | None |
| GET | `/api/cards/customer/{customerId}` | Get cards by customer ID | None |
| POST | `/api/cards` | Create new card | CardRequest JSON |
| PUT | `/api/cards/{id}` | Update card | CardRequest JSON |
| PUT | `/api/cards/{id}/block` | Block card | None |
| PUT | `/api/cards/{id}/activate` | Activate card | None |
| DELETE | `/api/cards/{id}` | Delete card | None |

### Request/Response Examples

#### Create Card Request


{
"cardNumber": "1111222233334444",
"cardHolderName": "John Doe",
"customerId": 1001,
"cardType": "CREDIT",
"expiryDate": "12/26",
"cvv": "123",
"creditLimit": 50000.00,
"availableBalance": 50000.00
}


#### Successful Response

{
"id": 1,
"cardNumber": "1111222233334444",
"cardHolderName": "John Doe",
"customerId": 1001,
"cardType": "CREDIT",
"expiryDate": "12/26",
"creditLimit": 50000.00,
"availableBalance": 50000.00,
"status": "ACTIVE",
"createdDate": "2025-08-12T16:08:42",
"updatedDate": null
}


## 🧪 Testing with Postman

1. **Import Collection**: Import the provided Postman collection JSON
2. **Set Environment**: Ensure `baseUrl` is set to `http://localhost:8080`
3. **Test Sequence**:
    - Start with "Get All Cards" to verify service is running
    - Create new cards using "Create Card"
    - Test all CRUD operations
    - Test card status management (block/activate)

## 🗂 Project Structure


## 🔍 Validation Rules

- **Card Number**: Must be exactly 16 digits
- **Card Holder Name**: 2-100 characters
- **Expiry Date**: MM/YY format
- **CVV**: 3-4 digits
- **Customer ID**: Required, must be numeric

## ⚙ Configuration Options

### Environment Variables
- `MYSQL_HOST`: MySQL host (default: localhost)
- `MYSQL_PORT`: MySQL port (default: 3306)
- `MYSQL_DB`: Database name (default: cardmanagementdb)
- `MYSQL_USER`: MySQL username
- `MYSQL_PASSWORD`: MySQL password

### Application Properties
- `server.port`: Application port (default: 8080)
- `spring.jpa.hibernate.ddl-auto`: Database schema handling (default: update)
- `eureka.client.register-with-eureka`: Eureka registration (default: false)

## 🐳 Docker Support

Build and run with Docker:

Build Docker image
docker build -t card-service .

Run container
docker run -p 8080:8080
-e MYSQL_HOST=host.docker.internal
-e MYSQL_PASSWORD=yourpassword
card-service


## 🔧 Troubleshooting

### Common Issues

1. **Database Connection Failed**
    - Verify MySQL is running
    - Check credentials in application.properties
    - Ensure database exists

2. **Port Already in Use**
    - Change server.port in application.properties
    - Kill process using port 8080

3. **Config Server Errors**
    - Set `spring.cloud.config.enabled=false` if not using config server

### Logs and Debugging

Enable debug logging:

logging.level.com.cardmanagement=DEBUG
logging.level.org.springframework.web=DEBUG



## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact: dhavalp3679@gmail.com

---

# Test if service is running postmen 
curl http://localhost:8080/api/cards

# Create a new card
curl -X POST http://localhost:8080/api/cards \
-H "Content-Type: application/json" \
-d '{
"cardNumber": "1111222233334444",
"cardHolderName": "Test User",
"customerId": 1001,
"cardType": "CREDIT",
"expiryDate": "12/26",
"cvv": "123",
"creditLimit": 50000.00,
"availableBalance": 50000.00
}'






**Happy Coding!** 🚀
