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

### Spring Cloud Microservice

Ziel dieser Aufgabe ist es, die Microservice aus Übung 1 so zu erweitern, dass sich dieser

* beim Start bei der Consul Service Discovery anmeldet,
* beim Start seine Konfigurationswerte bei Consul abholt,
* die Service-Schnittstellen (nicht die Admin Schnittstellen) über Traefik aufgerufen werden können

## Bonusaufgabe

### Orchestrierung mit Kubernetes

Bringen sie das Gespann aus Consul, Traefik und dem Microservice in Kubernetes zum Laufen.
