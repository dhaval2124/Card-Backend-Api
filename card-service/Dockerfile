# Use OpenJDK 17 as base image
FROM openjdk:17-jre-slim

# Set maintainer information
LABEL maintainer="card-service-team"

# Create app directory
WORKDIR /app

# Copy the JAR file
COPY target/card-service-0.0.1-SNAPSHOT.jar card-service.jar

# Expose port 8080
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Set JVM options for container
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar card-service.jar"]
