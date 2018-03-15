# Übung: Cloud-native Anwendung mit Spring Cloud, Consul und Traefik

Ziel dieser Übung ist es einen einfachen Spring Cloud REST Service zusammen mit Consul
für Service Discovery und Configuration und Traeffik als Edge Server aufzusetzen.

## Vorbereitung

* Das Aufsetzen eines Spring Cloud Microservice ist in Übung 1 beschrieben. Die Lösung dieser
Übung dient als Startpunkt und wird in dieser Übung erweitert.

## Aufgaben

### Consul Cluster mit Docker Compose

Ziel dieser Aufgabe ist es, einen kleinen Consul Cluster aus 3 Knoten mittels Docker Compose
aufzusetzen. Siehe auch https://www.consul.io/intro/getting-started/install.html

### Traefik Edge Server mit Consul Backend

Ziel dieser Aufgabe ist es, den Traefik Edge Server mittels Docker Compose zu betreiben und den
Consul als Discovery Backend für Traefik zu verwenden.

```
# configure the entry points
defaultEntryPoints = ["http"]
[entryPoints]
  [entryPoints.http]
  address = ":80"

# Enable web configuration backend
[web]
address = ":8080"

# Enable Consul Catalog configuration backend
[consulCatalog]
endpoint = "consul:8500"
domain = "localhost"
exposedByDefault = true
prefix = "traefik"
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

spring.cloud.consul.discovery.tags=traefik.enable=true,traefik.frontend.rule=PathPrefixStrip:/book-service,traefik.tags=api,traefik.frontend.entrypoint=http
```

## Bonusaufgabe

### Orchestrierung mit Kubernetes

Bringen sie das Gespann aus Consul, Traefik und dem Microservice in Kubernetes zum Laufen.
