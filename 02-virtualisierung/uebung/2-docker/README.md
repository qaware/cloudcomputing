# Übung: Virtualisierung mit Docker

## Vorbereitung

Dieses Tutorial benötigt eine Docker Installation auf ihrem lokalen System. 

Sie können sich aber auch eine VM mittels Vagrant dazu einrichten.

```bash
vagrant init generic/alpine39
vagrant up
vagrant status
vagrant ssh
```
In der VM kann dann docker installiert werden.

```bash
sudo apk update
sudo apk add docker
sudo addgroup username docker
sudo rc-update add docker boot
sudo service docker start
exit
```
Danach wieder mit `vagrant ssh` einloggen. 

*Hinweis:* Sollte ihre lokale Docker Installation nicht funktionieren, dann können Sie die folgende Übung ab Schritt 3 auch hier durchführen: https://www.katacoda.com/courses/docker/playground

## Aufgaben Teil 1
1. Starten sie einen Docker Container auf Basis des *alpine* Images und lassen sie darin eine interaktive Konsole als Entrypoint-Prozess laufen. Auf Docker Hub https://hub.docker.com finden sie das benötigte Basis Image.
2. Geben sie 'whoami' ein um herauszufinden, als welcher User sie nun eingeloggt sind. root ist dabei der Nutzer mit den höchsten Privilegien. Sie sind aber nur root innerhalb dieses Namespace.
1. Initialisieren sie die Paketmanager *apk* mit `apk update`
1. Installieren sie den NGINX Webserver mit `apk add nginx` und analog das curl Paket.
1. Starten sie den NGINX Webserver mit dem Befehl `nginx`. Hinweis: Sollte dabei der Fehler _open() "/run/nginx/nginx.pid" failed_ auftreten, dann bitte das entsprechende Verzeichnis erstellen mit `mkdir /run/nginx`. 
1. Lassen sie sich mit dem `curl` Befehl die Website auf der Kommandozeile ausgeben, die der NGINX Webserver auf *localhost* Port 80 zur Verfügung stellt. Überprüfen sie, ob eine Begrüßungs-Website von NGINX ausgegeben wird.
1. Prüfen sie, ob sie im Host System ebenfalls auf die Seite zugreifen können. Das sollte nicht der Fall sein.
1. Steigen sie aus der Bash im Docker Container per `exit` Befehl aus. Wo befinden sie sich nun?
1. Lassen sie sich alle von Docker verwalteten Container anzeigen mit `docker ps –a` und ermitteln sie die Container-Id des gerade erzeugten Containers. Warum sehen sie den Container nicht mit `docker ps`?
1. Erstellen sie ein Image aus dem erzeugten Container mit dem Befehl `docker commit <CONTAINER ID> cloudcomputing/nginx`
1. Lassen sie sich mit `docker images` alle von Docker verwalteten Images anzeigen und prüfen sie, ob das soeben erzeugte Image mit dabei ist.
1. Starten sie den neu erzeugten NGINX Container im Daemon-Modus (docker Container startet im Hintergrund) und leiten sie dabei den Gast-Port 80 auf den Host-Port 80 weiter. Der Entrypoint-Prozess ist dabei NGINX. NGINX muss dabei aber im Vordergrund, also im Nicht-Daemon-Modus laufen, da ansonsten der Container sofort beendet würde: `nginx -g "daemon off;"`
1. Lassen sie sich alle laufenden Docker Container per `docker ps` anzeigen. 
1. Lassen sie sich per `curl` die Ausgaben auf *localhost* unter den Ports 80 ausgeben. Sie können nun auch auf die NGINX-Seiten direkt aus einem Browser des Haupt-Betriebssystems ihres Rechners zugreifen. Über welche URL ist dies möglich?
1. Inspizieren sie die Docker Container mit `docker inspect` und lassen sie sich die Systemlogs in den Containern per `docker logs` ausgeben.

## Aufgaben Teil 2

In den ersten Schritten haben wir das Image manuell erstellt und mussten außerdem für nginx Startparameter über die Kommandozeile angeben. 

1. Nutzen Sie explizit als Basisimage alpine:3.11. Hier wird automatisch die letzte Bugfixversion mit der Hauptversion 3.11 runterladen. Zum Beispiel 3.11.6.
1. Schreiben Sie ein sog. *Dockerfile* und bauen sie damit automatisch das cloudcomputing/nginx Image.
1. Verwenden sie dazu die Kommandos "RUN, EXPOSE, ENTRYPOINT oder CMD"
1. Bauen sie das Docker image mit `docker build -t cloudcomputing/nginx .` 
1. Starten sie das docker image mit `docker run -d -p 80:80 cloudcomputing/nginx`.
1. Prüfen sie korrekte Funktion mit curl.

## Aufräumen

Docker kann viel Platz auf ihrer Festplatte verbrauchen. Mit folgenden Befehlen räumen sie auf

1. Listen sie alle Container, ob laufend oder gestoppt `docker ps -a`
1. Stoppen sie alle noch laufenden Container  `docker stop $(docker ps -aq)`
1. Entfernen sie alle Container `docker rm $(docker ps -aq)`
1. Löschen sie alle Images `docker rm docker rmi $(docker images -q)`

Für ein schnelles aufräumen aller ungenutzer Ressourcen kann auch `docker system prune` verwendet werden.

## Fundierter Einstieg in Docker

Für die Vorlesung wird empfohlen einen tieferen Einstieg in Docker zu machen, als dies im Rahmen der Übung möglich ist. Nutzen sie hierfür den dreien Docker-Kurs auf Katacoda: https://katacoda.com/courses/docker. Arbeiten Sie die folgenden Szenarien im Sinne einer Hausaufgabe durch:
 * Launching Containers
 * Deploy Static HTML Websites as Container
 * Building Container Images
 * Dockerizing Node.js
 * Optimise Builds With Docker OnBuild
 * Ignoring Files During Build
 * Create Data Containers
 * Creating Networls Between Containers Using Links
 * Creating Networks Between Containers Using Networks
 * Persisting Data Using Volumes
 * Manage Container Log Files
 * Ensuring Container Updatime With Restart Policies
 * Adding Docker Metadata & Labels

## Quellen
Diese Übung soll auch eine eigenständige Problemlösung auf Basis von Informationen aus dem Internet vermitteln. Sie können dazu für die eingesetzten Technologien z.B. die folgenden Quellen nutzen:
* Die Dokumentation von Docker: https://docs.docker.com
* Ein interaktives Tutorial zu Docker: https://www.docker.com/tryit
* Eine Übersicht der wichtigsten Docker Befehle: https://github.com/wsargent/docker-cheat-sheet
* Die Referenz zur Docker CLI und zum Dockerfile: https://docs.docker.com/reference/
