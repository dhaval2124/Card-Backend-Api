#FROM openjdk:17-jdk-slim
#
## Install required packages
#RUN apt-get update && apt-get install -y \
#    curl \
#    netcat-openbsd \
#    wget \
#    && rm -rf /var/lib/apt/lists/*
#
## Add wait-for-it script for waiting on dependencies
#ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/local/bin/wait-for-it.sh
#RUN chmod +x /usr/local/bin/wait-for-it.sh
#
#WORKDIR /app
#
## Copy the JAR file
#COPY target/fraud-detection-service-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the application port
#EXPOSE 8083
#
## Add health check
#HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
#  CMD curl -f http://localhost:8083/api/fraud/health || exit 1
#
## Wait for dependencies and then start the application
#ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "mysql:3306", "--timeout=60", "--", \
#           "/usr/local/bin/wait-for-it.sh", "kafka:29092", "--timeout=60", "--", \
#           "java", "-jar", "app.jar"]


# Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

  # Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY target/fraud-detection-service-*.jar app.jar

EXPOSE 8021

ENTRYPOINT ["java", "-jar", "/app/app.jar"]


