# Solution

1. Build with Maven: `./mvnw clean package`
1. Build Docker image: `docker build -t service:1 .`
1. Run Docker image: `docker run -p 8080:8080 service:1`
1. Open http://localhost:8080/ in your browser
