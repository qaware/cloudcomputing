# Übung: Provisionierung mit DockerFile

## Vorbereitung
1. Prüfen sie, ob VirtualBox und Vagrant installiert sind. Installieren Sie beides für den Fall, dass sich auf ihrem Notebook die Software nicht befindet.
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github herunter (die ZIP-Datei). Entpacken sie die ZIP-Datei und starten sie die Konsole mit `console.bat`.
* Starten sie die Vagrant Box (`vagrant up`).
* Verbinden sie sich mit `vagrant ssh` per Kommandozeile in die Vagrant Box (Passwort: *tcuser*).

## Ziel
![Zielbild](ziel.png)

## Aufgaben

### Images erstellen
Zunächst erstellen wir Images für den Webserver (*NGINX*) und den Load-Balancer (*HAproxy*). Die jeweiligen Dockerfiles sind auf github im Lösungsordner der Übung verfügbar. Dem Kommando `docker build` kann direkt die github-URL auf die Datei mitgegeben werden (URL wird ermittelt, indem man zur jeweiligen Datei navigiert und bei der Datei-Anzeige den Button *Raw* drückt).
* Greifen sie zunächst per Browser auf die beiden Dockerfile zu und analysieren sie den Provisionierungsablauf und die hinterlegten Konfigurationsdateien.
* Erstellen sie ein Image mit dem Namen *cloudcomputing/nginx-node* aus dem NGNIX-Dockerfile.
* Erstellen sie ein Image mit dem Namen *cloudcomputing/haproxy-node* aus dem HAproxy-Dockerfile.
* Vergewissern sie sich über das entsprechende Docker Kommando, dass beide Images lokal vorliegen.

### NGINX-Cluster
Nun erstellen wir ein Cluster aus drei NGINX Webservern, die aus dem Host-System heraus unter den Ports 81, 82 und 83 erreichbar sind.
* Starten sie drei Container aus dem NGINX-Image. Der Container soll dabei im Hintergrund laufen (Parameter `-d`). Geben sie per `--name` Parameter den Containern die Namen nginx1, nginx2 und nginx3. Aufrufbeispiel:
`docker run -d -p 81:80 --name nginx1 cloudcomputing/nginx-node`
* Prüfen sie, ob die drei Container laufen.
* Prüfen sie, ob die drei in den Containern enthaltenen NGINX-Server eine http-Antwort geben.

### HAproxy
Nun starten sie einen HAProxy Container, der mit den drei NGINX Containern verbunden ist.
* Starten sie einen Container aus dem HAproxy Image und bilden sie dessen Port 80 auf den Host-Port 80 ab. Der Container soll dabei ebenfalls im Hintergrund laufen. Bauen sie dabei auch über entsprechende Kommandozeilen-Parameter eine Verbindung zwischen dem HAproxy Container und den NGINX Containern auf: `--link nginx1:nginx1`.
* Beantworten sie die folgende Frage: Wie bekommt der HAproxy mit, auf welchen IP-Adressen und unter welchen Ports er die NGINX-Server findet?

### Testlauf
* Überprüfen sie die HAproxy Statistiken aus einem Browser heraus: http://localhost:8080/haproxy?stats (Zugang mit *admin/admin*). Greifen sie dazu parallel mehrfach auf die Loadbalancer-URL zu: http://localhost:8080. Welche Informationen können sie der Statistik entnehmen?

## Optionale Zusatzaufgaben
* Nutzen sie den Docker-Provisionierer von Vagrant, um die Docker Images zu erstellen und die Container laufen zu lassen (Dokumentation: https://docs.vagrantup.com/v2/provisioning/docker.html).
* Automatisieren sie die Provisionierung anstelle von Dockerfiles mit Ansible und nutzen sie hierfür den Ansbile Provisioner von Vagrant (https://docs.vagrantup.com/v2/provisioning/ansible.html). Wie der Ansible-Provisoner von Vagrant von Windows aus genutzt werden kann ist hier beschrieben: https://gist.github.com/tknerr/291b765df23845e56a29. Nutzen sie hier die beschriebene Variante "Within the Target VM".
