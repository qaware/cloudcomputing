# Übung: IaaS mit der Google Compute Engine

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter. **TODO (git Befehl)**
* Öffnen sie das Verzeichnis dann als Projekt innerhalb von Netbeans.
* Führen Sie das *Maven Goal* `clean package` aus.
* Laden sie die Datei *gce-cert.json* von der URL herunter, die vor der Übung bekannt gegeben wurde, und legen sie diese im Verzeichnis ab, in dem sich auch die *pom.xml* befindet (Wurzelverzeichnis des Projekts).

## Ziel
Wir wollen im Wesentlichen das selbe Ziel wie in der letzten Übung erreichen: Ein NGINX-Cluster mit einem vorgelagerten HAproxy Load Balancer als Konstellation an Docker-Containern laufen lassen. Im Rahmen dieser Übung lassen wir die Docker-Container jedoch nicht auf dem lokalen Rechner laufen sondern auf einem Rechner in der Google Cloud. Wir provisionieren diesen Rechner nicht wie bisher rein über die Kommandozeile, sondern nutzen JClouds (https://jclouds.apache.org) dafür.

![Zielbild](ziel.png)

## Aufgaben

### Cluster per JClouds API aufbauen
* Öffnen sie die Klasse `CloudUtils` und erschließen sie sich die dort enthaltenen Methoden.
* Ergänzen sie in der Klasse `ListImages` den Quellcode, um die Liste der verfügbaren Images der *Google Compute Engine* ausgeben zu können.
* Ergänzen sie in der Klasse `ListHardware` den Quellcode, um die Liste der verfügbaren Hardware-Profile der Google Compute Engine ausgeben zu können. Lassen sie sich die Liste ausgeben.
* Ergänzen sie in der Klasse `LaunchNginxCluster` den Quellcode, um ein *NGINX* Cluster mit vorgelagertem *HAproxy* analog zur letzten Übung zu starten. Lassen sie sich die Liste ausgeben.
* Starten sie das NGINX Cluster über das Java Programm und analysieren sie die Stellen in der Konsolenausgabe, die die Rückgabe der Befehle enthalten: Wurden alle Befehle erfolgreich durchgeführt?
* Extrahieren sie aus der Konsolenausgabe die *Node-Id* und *IP* ihres erzeugten Knotens mit dem NGINX Cluster und teilen sie diese dem Übungsbetreuer mit.

### Testlauf
Überprüfen sie die HAproxy Statistiken aus einem Browser heraus: http://<ip>/haproxy?stats. Greifen sie dazu parallel mehrfach auf die URL der NGINX-Webserver zu: http://<ip>.

## Optionale Zusatzaufgabe
Legen sie einen Account in der Amazon Cloud an (http://aws.amazon.com/de, Achtung: Hierfür benötigen sie eine Kreditkarte, die aber bei Nutzung kleiner Instanzen nicht belastet wird). Portieren sie das Java-Programm zur Provisionierung des NGINX Clusters auf die Amazon Cloud. Nutzen sie dabei die entsprechende Dokumentation von Amazon EC2 (http://aws.amazon.com/de/documentation/ec2) und der JClouds API (https://jclouds.apache.org/start/compute).

# Quellen
* Nutzung von JClouds mit der Google Compute Engine: https://jclouds.apache.org/guides/google
