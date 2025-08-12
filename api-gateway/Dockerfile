FROM openjdk:17-jdk-slim

LABEL maintainer="cardmanagement.com"
LABEL description="API Gateway for Card Management System"

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
