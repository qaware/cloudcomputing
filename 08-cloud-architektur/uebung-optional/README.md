# Übung: Traefik und Consul
## Aufgabe 1: Erste Erfahrungen mit Traefik und Consul sammeln

Ziel dieser Übung ist es, erste praktische Erfahrungen mit Traefik und Consul zu machen. 
Die Übung basiert dabei auf Tutorials, die auf [Katacoda](https://www.katacoda.com/) verfügbar sind.

### Vorbereitung

1. Bitte richten sie einen [Katacoda](https://www.katacoda.com/) Account für sich ein

### Aufgaben
Arbeiten Sie die folgenden Tutorials auf Katacoda durch. 
Hinweis: Im Browser steht Ihnen dabei eine umfassende Umgebung zur Verfügung. Nutzen Sie dies auch, um mit Traefik und Consul
zu experimentieren.


1. [Traefic als Router/Edge Server](https://katacoda.com/boxboat/courses/k8s-adv/03-using-traefik-edge-router)

2. [Setup Consul Cluster](https://katacoda.com/courses/consul/launch-docker-cluster)

3. [Consul Konfiguration](https://katacoda.com/hashicorp/scenarios/consul-connect)

## Aufgabe 2

Ziel dieser Übung ist es, einen einfachen Spring Cloud REST Service zusammen mit Consul
für Service Discovery und Configuration und Traeffik als Edge Server aufzusetzen.

Orientieren Sie sich an diesem [Tutorial](https://m.mattmclaugh.com/traefik-and-consul-catalog-example-2c33fc1480c0).

## Vorbereitung

* Das Aufsetzen eines Spring Cloud Microservice ist in Übung 1 beschrieben. Die Lösung dieser
Übung dient als Startpunkt und wird in dieser Übung erweitert.

## Aufgaben

### Consul Cluster (Single Node) mit Docker Compose

Legen Sie ein Docker Compose File an.
Starten Sie darin ein Consul Cluster mit einem Node. Verwenden Sie das dafür aktuellste offizielle Docker Image von Hashicorp.

Stellen Sie sicher, dass die Consul UI gestartet wird, und alle benötigen Ports exponiert werden.

Orientieren Sie sich an folgendem Abschnitt, falls Sie nicht weiterkommen:
```
  consul:
    image: consul
    command: consul agent -server -dev -client=0.0.0.0 -ui -bootstrap -log-level warn
    ports:
      - "8400:8400"
      - "8500:8500"
      - "8600:53/udp"
```

### Traefik Edge Service mit Consul Backend

Betreiben Sie den Traefik Edge Service mittels Docker Compose und verwenden Sie
Consul als Discovery Backend für Traefik.


Legen Sie dazu ein Dockerfile und eine Traefik Konfiguration an.
Falls Sie nicht weiterkommen, verwenden Sie das Dockerfile und die Konfiguration unter /traefik.

Bauen Sie einen Container auf Basis des Dockerfiles und geben Sie diesem den Namen reverse-proxy:

```
docker build -t reverse-proxy .
```

Starten Sie den Container ebenfalls über Docker Compose und übergeben Sie beim Start die nötigen Konfigurationen zur
Interaktion mit Consul.

Falls Sie nicht weiterkommen, verwenden Sie folgenden Abschnitt:

```
    reverse-proxy:
      image: reverse-proxy
      command: traefik --consulcatalog.endpoint=consul:8500
      ports:
        - "8080:8080"
        - "8081:80"
      depends_on:
        - consul
      links:
        - consul
```

### Spring Cloud Microservice

Ziel dieser Aufgabe ist es, die Microservice aus Übung 1 so zu erweitern, dass sich dieser

* beim Start bei der Consul Service Discovery anmeldet,
* beim Start seine Konfigurationswerte bei Consul abholt,
* die Service-Schnittstellen (nicht die Admin Schnittstellen) über Traefik aufgerufen werden können

Die folgenden Dependencies müssen der `pom.xml` hinzugefügt werden:

```xml
<!-- required for Consol discovery and configuration -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

Danach muss unter `src/main/resources` die Datei `bootstrap.properties` angelegt werden:

```
spring.application.name=book-service

# specify Consul host and port
# we use the CONSUL_HOST and CONSUL_PORT env variables
# later set in docker compose as well as Kubernetes
spring.cloud.consul.host=${consul.host:consul}
spring.cloud.consul.port=${consul.port:8500}

spring.cloud.consul.config.enabled=true
spring.cloud.consul.config.prefix=configuration
spring.cloud.consul.config.default-context=application

# do not fail at startup if Consul is not there
spring.cloud.consul.config.fail-fast=false

# store properties as blob in property syntax
# e.g. configuration/book-service/data
spring.cloud.consul.config.format=properties
spring.cloud.consul.config.data-key=data
```

In der `application.properties` müssen zudem folgende Properties angelegt werden, um die Service-Registrierung
in Consul und die Tags für Traefik korrekt zu konfigurieren:

```
# assign a unique instance ID
spring.cloud.consul.discovery.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}

# required by Docker compose and Consul to run the health check
# register IP address and heartbeats
spring.cloud.consul.discovery.prefer-ip-address=true
spring.cloud.consul.discovery.heartbeat.enabled=true

spring.cloud.consul.discovery.tags=traefik.enable=true,traefik.frontend.rule=PathPrefixStrip:/book-service,traefik.tags=api,traefik.frontend.entrypoint=h
```

Starten Sie den Service ebenfalls über Docker Compose.

Falls Sie nicht weiterkommen, verwenden Sie folgenden Abschnitt:
```
 book-service:
    build: ./book-service
    image: book-service:1.1.0
    ports:
      - 18080:18080
    depends_on:
      - consul
    networks:
      - cloud-architecture
    environment:
      - SPRING_CLOUD_CONSUL_HOST=consul
```
### Testen

* Die Anwendung sollte nun unter `http://localhost:8081/book-service/api/books` erreichbar sein.
* Die Consul UI sollte unter `http://localhost:8500/ui` erreichbar sein.
* Die Traefik UI sollte unter `http://localhost:8080` erreichbar sein.

### Bonus: Orchestrierung mit Kubernetes

Bringen sie das Gespann aus Consul, Traefik und dem Microservice in Kubernetes zum Laufen.
