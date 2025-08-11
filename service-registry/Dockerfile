FROM openjdk:17-jdk-slim

LABEL maintainer="cardmanagement.com"
LABEL description="Eureka Server for Card Management System"

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/service-registry-0.0.1-SNAPSHOT.jar service-registry.jar

# Expose port
EXPOSE 8761

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8761/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "service-registry.jar"]
