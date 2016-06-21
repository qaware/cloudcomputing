# Übung: Cloud-native Anwendung mit Dropwizard, Consul und Fabio

In einer vorhergehenden Übung wurde bereits der Microservice _Zwitscher_ auf Basis von Dropwizard
(http://www.dropwizard.io) umgesetzt. Nun soll dieser Microservice um die notwendige Cloud-Infrastruktur
ergänzt werden. Dies ist Consul (https://www.consul.io) für Configuration & Coordination sowie Service Discovery
und Fabio (https://github.com/eBay/fabio) als Edge Server.

## Ziel
Ziel ist es den Zwitscher Microservice in die Consul- und Fabio-Infrastruktur zu integrieren.

## Vorbereitung
 * Laden sie die Vorlage zur Übung von github herunter. Machen sie sich mit dem Code wieder vertraut und starten sie den Service aus der Entwicklungsumgebung heraus, rufen sie die Endpunkte im Browser auf und stoppen sie den Service wieder. Zwitscher läuft nun nicht auf einem fix vorgegebenen Port sondern sucht sich selbst einen freien Port. Wie wurde dies erreicht?
 * Laden sie Consul herunter (https://www.consul.io/downloads.html) und entpacken die ZIP-Datei im Wurzelverzeichnis der Übung.
 * Laden sie das letzte stabile Release von Fabio herunter (Linux und Mac OS X Version: https://github.com/eBay/fabio/releases - Datei im Anschluss in `fabio` umbenennen; Windows-Version 64 Bit: https://github.com/adersberger/cloudcomputing/tree/master/08-cloud-architektur/uebung/uebung-2/bin/win64). Legen sie die Fabio executable ebenfalls im Wurzelverzeichnis deer Übung ab. Bei Linux und Mac OS X muss die Datei eventuell noch als ausführbar markiert werden: `chmod +x fabio`.
 * Legen sie im Wurzelverzeichnis der Übung eine Konfigurationsdatei für Fabio mit dem Dateinamen `fabio.properties` an und befüllen sie diese mit dem Default-Inhalt
 (https://raw.githubusercontent.com/eBay/fabio/master/fabio.properties).

## Aufgaben

### Consul starten (Configuration & Coordination)
 1. Consul starten: `consul agent -dev -ui -bind=127.0.0.1`. Consul kann später per `Ctrl + C` gestoppt werden.
 2. Die Ausgabe von Consul analysieren. Hier kann man sehen, wie das Raft-Protokoll den Leader wählt und anschließend den Zustand synchronisiert.
 3. Die Consul Web-UI aufrufen: http://localhost:8500/ui. Consul muss hier selbst als Service erfolgreich registriert sein.
 4. Über die REST API von Consul prüfen, ob der Service "consul" läuft: http://localhost:8500/v1/catalog/services.

### Den Microservice bei Consul registrieren (Service Discovery)
 1. Den Zwischer Service so modifizieren, dass der Service an Consul registriert wird (siehe entsprechenden TODO-Kommentar).
 Hierfür wird die Orbiz Java-API für Consul genutzt (https://github.com/OrbitzWorldwide/consul-client). Beantworten sie dabei auch die Fragen, die im Quellcode gestellt werden.
 2. In Consul prüfen, ob der Zwitscher-Service registriert ist. Per UI (http://localhost:8500/ui) oder per REST API (http://localhost:8500/v1/catalog/service/zwitscher).
 3. Den Zwitscher-Service direkt aufrufen: http://localhost:2890/messages.
 4. In Consul den Health-Status des Zwitscher-Service prüfen. Sollte grün sein.
 5. 3 weitere Service-Instanzen starten und in Consul nachvollziehen, dass sie dort registriert und gesund sind.

### Fabio konfigurieren und starten (Edge Server)
 1. In der Konfigurationsdatei die Proxy-Adresse und die lokale IP auf `localhost` bzw. `127.0.0.1` setzen
 sowie die LB-Strategie auf Round Robin umsetzen.
 2. Fabio per `fabio -cfg fabio.properties` starten. Fabio setzt dabei einen lokal laufenden Consul-Agent auf dem Port 8500 voraus.
 3. In Consul prüfen, ob fabio als Service registriert und gesund ist.
 4. Auf die Fabio Web-UI zugreifen: http://localhost:9998 und die dort definierten Routen analysieren.
 Was bedeutet "Weight" bei einer Route?
 5. Den Zwitscher Service über fabio aufrufen: http://localhost:9999/messages. In den Logs der Service-Instanzen
   die Verteilung der Requests nachvollziehen. Fabio auf random-Verteilung umstellen und die Request-Verteilung dann
   nachvollziehen.

### Bonusaufgaben
  1. Auf die Consul-Anbindung von Dropwizard umstellen: https://github.com/smoketurner/dropwizard-consul.
  2. Zwitscher per DNS suchen: `dig @127.0.0.1 -p 8600 zwitscher.service.consul SRV`.
    In Windows muss hierzu das Werkzeug Bind installiert werden (http://www.bind9.net/download).

Bei Interesse finden sie auch eine etwas umfangreichere Version von Zwitscher auf github (https://github.com/qaware/cloud-native-zwitscher),
die auf Spring Cloud (Microservice Container, Integrationen in Infrastruktur, Configuration & Coordination)
und dem Netflix OSS Stack (Edge Server, Service Connector, Monitoring Service) basiert. Diese läuft sowohl mit Docker Compose, Kubernetes als auch Marathon als Cluster Orchestrierer.
