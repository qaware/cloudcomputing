# Übung: IaaS mit der JClouds und der Amazon EC2

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter (über `git clone` oder Download des Repositories als ZIP).
* Öffnen sie das Verzeichnis dann als Projekt innerhalb ihrer Entwicklungsumgebung.
* Ergänzen sie die Zugriffsdaten auf die Amazon Cloud innerhalb der Klasse _Credentials_. Sie erhalten die Zugriffsdaten beim Übungsbetreuer.
* Führen Sie das *Maven Goal* `clean package` aus.

## Ziel
Wir wollen im Wesentlichen das Ziel der letzten Übung erreichen: Ein NGINX-Cluster mit einem vorgelagerten HAproxy Load Balancer als Konstellation an Docker-Containern laufen lassen. Im Rahmen dieser Übung lassen wir die Docker-Container jedoch nicht auf dem lokalen Rechner laufen sondern auf einem Rechner in einer IaaS Cloud (Amazon EC2). Wir provisionieren diesen Rechner nicht wie bisher rein über die Kommandozeile, sondern nutzen JClouds (https://jclouds.apache.org) dafür.

## Aufgaben

### Cluster per JClouds API aufbauen
* Öffnen sie die Klasse `CloudUtils` und erschließen sie sich die dort enthaltenen Methoden.
* Ergänzen sie in der Klasse `ListHardware` den Quellcode, um die Liste der verfügbaren Hardware-Profile ausgeben zu können. Lassen sie sich die Liste ausgeben.
* Erstellen sie eine weitere Klasse `ListRegions`, die alle verfügbaren Rechenzentren (Regionen) ausgibt.
* Starten sie eine EC2 Instanz über das Java Programm und analysieren sie die Stellen in der Konsolenausgabe, die die Rückgabe der Befehle enthalten: Wurden alle Befehle erfolgreich durchgeführt?
* Extrahieren sie aus der Konsolenausgabe die *IP* ihres erzeugten Knotens.
