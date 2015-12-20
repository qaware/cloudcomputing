# Übung: Cluster-Orchestrierung einer Spring Cloud Applikation mit Kubernetes

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner)
* Holen sie sich die Beispiel-Applikation Ping-Pong in dieses Verzeichnis: `git clone https://github.com/adersberger/spring-cloud-ping-pong-sample.git`. Falls sie git nicht auf dem Übungsrechner installiert haben, dann können sie sich den Code auch direkt als ZIP-Datei von der Website laden (Endung ".git" in diesem Fall bei der URL weglassen).
* Öffnen sie Ping-Pong als Projekt in Netbeans.

## Aufgaben
### 1) Lokal ausführen
Ziel dieser Aufgabe ist es, die Ping-Pong Applikation lokal auszuführen. Folgen sie hierzu den Anleitungen auf https://github.com/adersberger/spring-cloud-ping-pong-sample.

* Rufen sie im Anschluss die Web-UI der Anwendung auf sowie die Web-UI der Service Discovery. Vergewissern sie sich, dass alle Microservices bei der Service Discovery registriert sind.

* Untersuchen sie einen beliebigen der laufenden Microservices. Nutzen sie hierzu die Standard-Endpunkte, die automatisch von Spring Boot für jeden Microservices zur Verfügung gestellt werden (https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html). Lassen sie sich die verfügbaren Endpunkte, den Gesundheitszustand des Microservices sowie die aktuell gemessenen Metriken ausgeben.

### 2) Code-Schnitzeljagd
Beantworten sie die folgenden Fragen auf Basis des vorliegenden Quellcodes und der Dokumentation von Spring Boot und Spring Cloud:
* Wo werden die Ports eingestellt, auf denen die jeweiligen Microservices lauschen?
* Wo ist die `main()` Methode der jeweiligen Microservices?
* Wie wird die Service Discovery genutzt, um einen konkreten Endpunkt für einen Service aufzulösen?

### 3) Deployment im Kubernetes Cluster
* Erzeugen sie zu jedem Microservice einen Docker Container, wie in der Übung zur Provisionierung beschrieben.
* Erstellen sie für alle Microservices eine Kubernetes Pod- und Service-Definition im Verzeichnis `cluster/kubernetes`. Orientieren sie sich dabei an der Datei `cluster/docker/docker-compose.yml`, die eine vollständige Cluster-Beschreibung für den Cluster-Orchestrierer Docker Compose enthält.
* Erstellen sie in lokales Kubernetes-Cluster auf Basis von Vagrant, wie hier beschrieben: https://github.com/kubernetes/kubernetes/blob/release-1.1/docs/getting-started-guides/vagrant.md
* Deployen sie die Microservices in das lokale Kubernetes Cluster.
* Prüfen sie durch die Web-UI der Anwendung sowie die UI der Service Discovery, ob alle Microservices korrekt laufen.
