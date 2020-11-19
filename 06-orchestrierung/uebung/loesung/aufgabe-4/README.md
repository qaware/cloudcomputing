# Solution

I added the [Spring Boot actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
to the [pom.xml](pom.xml) which provides the liveness and the readiness endpoints in the application. 

1. Build with Maven: `./mvnw clean package`
1. Point Docker against minikube: `eval $(minikube -p minikube docker-env)`
1. Build image: `docker build -t service:2 .`
1. Apply [deployment](deployment.yaml): `kubectl apply -f deployment.yaml`
