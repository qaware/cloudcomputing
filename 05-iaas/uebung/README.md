# Übung: IaaS mit der JClouds und der Amazon EC2

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter (über `git clone` oder Download des Repositories als ZIP).
* Öffnen sie das Verzeichnis dann als Projekt innerhalb von Netbeans.
* Führen Sie das *Maven Goal* `clean package` aus.

## Ziel
Wir wollen im Wesentlichen das Ziel der letzten Übung erreichen: Ein NGINX-Cluster mit einem vorgelagerten HAproxy Load Balancer als Konstellation an Docker-Containern laufen lassen. Im Rahmen dieser Übung lassen wir die Docker-Container jedoch nicht auf dem lokalen Rechner laufen sondern auf einem Rechner in einer IaaS Cloud (Amazon EC2). Wir provisionieren diesen Rechner nicht wie bisher rein über die Kommandozeile, sondern nutzen JClouds (https://jclouds.apache.org) dafür.

![Zielbild](ziel.png)

## Aufgaben

### Cluster per JClouds API aufbauen
* Öffnen sie die Klasse `CloudUtils` und erschließen sie sich die dort enthaltenen Methoden.
* Ergänzen sie in der Klasse `ListHardware` den Quellcode, um die Liste der verfügbaren Hardware-Profile ausgeben zu können. Lassen sie sich die Liste ausgeben.
* Erstellen sie eine weitere Klasse `ListRegions`, die alle verfügbaren Rechenzentren (Regionen) ausgibt.
* Ergänzen sie in der Klasse `LaunchNginxCluster` den Quellcode, um ein *NGINX* Cluster mit vorgelagertem *HAproxy* analog zur letzten Übung zu starten.
* Starten sie das NGINX Cluster über das Java Programm und analysieren sie die Stellen in der Konsolenausgabe, die die Rückgabe der Befehle enthalten: Wurden alle Befehle erfolgreich durchgeführt?
* Extrahieren sie aus der Konsolenausgabe die *IP* ihres erzeugten Knotens und teilen sie diese dem Übungsbetreuer mit.

### Testlauf
* Greifen sie auf die NGINX-Ausgabe zu: http://[IP]/
* Überprüfen sie die HAproxy Statistiken aus einem Browser heraus: http://[IP]/haproxy?stats. Greifen sie dazu parallel mehrfach auf die URL der NGINX-Webserver zu.
* Überprüfen sie die Liste der laufenden Docker Container: http://[IP]:2375/containers/json.

## Zusatzaufgabe
Schreiben sie ein Java-Programm, das es erlaubt neue HAproxy/NGINX-Cluster zu starten. Dabei kann die Anzahl der NGINX-Server flexibel angegeben werden. Nutzen sie hierfür die Docker-Java-Bibliothek (https://github.com/docker-java/docker-java). Verbinden sie sich mit dieser Bibliothek mit dem Docker Daemon auf dem bereits gestarteten IaaS-Knoten. Der Docker Daemon ist dort bereits auf dem Port 2375 von Außen erreichbar.
