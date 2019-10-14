FROM openjdk:8u151-jre-slim

COPY target/book-service-1.0.1.jar /app/
RUN chmod +x /app/book-service-1.0.1.jar

ENTRYPOINT ["/app/book-service-1.0.1.jar"]
