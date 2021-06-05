# Übung: Cloud-native Anwendung mit Dropwizard, Consul und Fabio

In einer vorhergehenden Übung wurde bereits der Microservice _Zwitscher_ auf Basis von Dropwizard
(http://www.dropwizard.io) umgesetzt. Nun soll dieser Microservice um die notwendige Cloud-Infrastruktur
ergänzt werden. Dies ist Consul (https://www.consul.io) für Configuration & Coordination sowie Service Discovery
und Fabio (https://github.com/eBay/fabio) als Edge Server.

## Ziel
Ziel ist es den Zwitscher Microservice in die Consul- und Fabio-Infrastruktur zu integrieren und dann auf einem Kubernetes Cluster zum Laufen zu bringen.

## Aufgabenblock 1: Microservice Stack lokal ausführen

### Vorbereitung
 * Laden sie die Vorlage zur Übung von github herunter. Machen sie sich mit dem Code wieder vertraut und starten sie den Service aus der Entwicklungsumgebung heraus, rufen sie die Endpunkte im Browser auf und stoppen sie den Service wieder. Zwitscher läuft nun nicht auf einem fix vorgegebenen Port sondern sucht sich selbst einen freien Port. Wie wurde dies erreicht?
 * Laden sie Consul herunter (https://www.consul.io/downloads.html) und entpacken die ZIP-Datei im Wurzelverzeichnis der Übung.
 * Laden sie das letzte stabile Release von Fabio herunter (Linux und Mac OS X Version: https://github.com/eBay/fabio/releases - Datei im Anschluss in `fabio` umbenennen. Legen sie die Fabio executable ebenfalls im Wurzelverzeichnis deer Übung ab. Bei Linux und Mac OS X muss die Datei eventuell noch als ausführbar markiert werden: `chmod +x fabio`.
 * Legen sie im Wurzelverzeichnis der Übung eine Konfigurationsdatei für Fabio mit dem Dateinamen `fabio.properties` an und befüllen sie diese mit dem Default-Inhalt
 (https://raw.githubusercontent.com/eBay/fabio/master/fabio.properties).

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

## Aufgabenblock 2: Microservice Stack in auf ein Kubernetes Cluster deployen

### Vorbereitung
 1. Laden sie minikube herunter (https://github.com/kubernetes/minikube/releases) und legen sie die Datei im Wurzelverzeichnis der Übung ab. Über minikube kann ein lokales Kubernetes Cluster erzeugt und verwaltet werden. Nennen sie die heruntergeladene Datei in _minikube_ / _minikube.exe_ um.
 * Laden sie kubectl herunter und legen sie die Datei im Wurzelverzeichnis ab. Mit kubectl kann ein Kubernetes Cluster gesteuert werden. Die Dokumentation zu kubectl finden sie hier: http://kubernetes.io/docs/user-guide/kubectl-overview. Die ausführbare kubectl Datei ist unter den folgenden URLs zugreifbar:
   * Win64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/windows/amd64/kubectl.exe
   * Win32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/windows/386/kubectl.exe
   * macOS64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/darwin/amd64/kubectl
   * macOS32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/darwin/386/kubectl
   * Linux64: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/linux/amd64/kubectl
   * Linux32: https://storage.googleapis.com/kubernetes-release/release/v1.4.7/bin/linux/386/kubectl
 * Starten sie ein lokales Kubernetes Cluster, lassen sie sich die dabei den Status und die verfügbaren Knoten ausgeben und öffnen sie das Kubernetes Web-Dashboard:
   * `minikube start`
   * `minikube status`
   * `kubectl get nodes`
   * `minikube dashboard`
 * Installieren sie das Docker Kommandozeilen-Werkzeug. Laden sie dieses entsprechend herunter (https://docs.docker.com/engine/installation/binaries) und legen sie die ausführbare _docker_ Datei ins Wurzelverzeichnis.
 * Führen sie die Kommandos aus, die der Befehl `minikube docker-env` ausgibt. Dies verbindet den Docker Client mit dem Docker Daemon innerhalb von minikube.
 * Prüfen sie mit `docker ps`, ob die Verbindung zum Docker Daemon klappt. Hier sollten ein paar Docker Container aufgelistet werden, die innerhalb von minikube laufen.

### Consul deployen
 1. Öffnen sie die Service- und RC-Deskriptoren für Consul und analysieren sie diese
 * Deployen sie zunächst den den Service und dann den RC und prüfen sie im Anschluss im Dashboard, ob beide erfolgreich laufen
   * `kubectl create -f ./src/infrastructure/k8s/consul-svc.yaml`
   * `kubectl create -f ./src/infrastructure/k8s/consul-rc.yaml`
 * Greifen sie auf die Consul Web-UI zu. Dazu müssen sie zunächst per `minikube ip` die IP des Kubernetes Cluster ermitteln. Der Zugriff auf die Consul Web-UI erfolgt dann über die URL: http://MINIKUBE-IP:30850/ui
 
### Microservice mit Consul verbinden (Bonusaufgabe)
 1. Modifizieren sie den Microservice so, dass er sich mit dem Consul Service innerhalb von Kubernetes verbindet. Wie ein Service-Endpunkt innerhalb von Kubernetes ermittelt werden kann ist hier beschrieben: http://kubernetes.io/docs/user-guide/services.
 * Sie müssen dabei auch die IP des Microservice Pods ermitteln. Welche Möglichkeiten es hierfür gibt, sind z.B. hier beschrieben:
    * http://stackoverflow.com/questions/30746888/how-to-know-a-pods-own-ip-address-from-a-container-in-the-pod
    * http://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
 * Erstellen sie ein Dockerfile für den Microservice und bauen sie mit dem Docker Kommandozeilenwerkzeug ein Image 
 * Erstellen sie einen Kubernetes Service- und RC-Deskriptor für den Microservice und deployen sie beides in den Kubernetes Cluster
 * Prüfen sie im Anschluss per Dashboard und Consul UI, ob der Microservice läuft und bei Consul registriert ist
 
### Fabio vor Consul schalten (Bonusaufgabe)
  1. Erstellen sie einen Service- und RC-Deskriptor für fabio. 
    * Ein Docker Image für fabio ist hier zu finden: https://hub.docker.com/r/magiconair/fabio. 
    * Wie die Verbindung zwischen fabio und Consul per Kommandozeilen-Parameter aufgebaut werden kann ist hier zu finden: https://github.com/eBay/fabio/wiki/Configuration
  * Deployen sie den fabio Service und RC und prüfen sie, ob sie die fabio UI und den fabio Endpunkt erreichen können.

Bei Interesse finden sie auch eine etwas umfangreichere Version von Zwitscher auf github (https://github.com/qaware/cloud-native-zwitscher),
die auf Spring Cloud (Microservice Container, Integrationen in Infrastruktur, Configuration & Coordination)
und dem Netflix OSS Stack (Edge Server, Service Connector, Monitoring Service) basiert. Diese läuft sowohl mit Docker Compose, Kubernetes als auch Marathon als Cluster Orchestrierer.
