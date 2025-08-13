FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENV JAVA_OPTS="-Xms512m -Xmx1024m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
