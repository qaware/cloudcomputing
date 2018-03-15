FROM openjdk:8u151-jre-slim

COPY target/book-service-1.1.0.jar /app/
RUN chmod +x /app/book-service-1.1.0.jar

ENTRYPOINT ["/app/book-service-1.1.0.jar"]
