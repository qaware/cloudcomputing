# Übung: Cluster Scheduling mit DC/OS (Mesos und Marathon)

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter (über `git clone` oder Download des Repositories als ZIP).
* Öffnen sie das Verzeichnis dann als Projekt innerhalb von Netbeans.
* Führen Sie das *Maven Goal* `clean package` aus.
* Lassen sie sich vom Übungsbetreuer eine exklusive Port-Nummer im Bereich von 8081 bis 8090 zuteilen.

## Ziel
Wir wollen im Rahmen der Übung NGINX-Knoten auf einem Mesos + Marathon Cluster starten. 

![Zielbild](ziel.png)

Das Cluster läuft auf Basis der Amazon EC2 IaaS-Cloud und besteht aus 8 Rechnern - es ist bereits fertig eingerichtet. 

## Aufgaben
### Eine NGINX-Instanz starten
Ziel ist es, eine NGINX-Instanz in Marathon zu starten. Hierfür verwenden wir das Docker Image aus den vorangegangen Übungen. Es ist unter der ID `qaware/nginx` öffentlich in der öffentlichen Docker Registry, dem Docker Hub (https://hub.docker.com/u/qaware), verfügbar. Wir nutzen die REST-API von Marathon aus einem Java-Programm heraus, um NGINX zu starten. Gehen sie dabei wie folgt vor:
* Besorgen sie sich die Datei `dcos-jwt.txt` beim Übungsbetreuer und legen sie diese im Verzeichnis `src/main/resources` ab.
* Öffnen sie die Datei `nginx-cluster.json` in Netbeans. Diese repräsentiert den Request, den wir an Marathon senden werden um den NGINX-Container zu starten. Erschließen sie sich den Inhalt der Datei mit Hilfe der REST-Dokumentation von Marathon (https://mesosphere.github.io/marathon/docs/rest-api.html#post-v2-apps).
* Öffnen sie die Klasse `MarathonController` in Netbeans und programmieren sie dort die Methode `printRunningApps()` aus, die alle aktuell in Marathon laufenden Applikationen (Jobs) ausgibt. Nutzen sie für den entsprechenden REST-Aufruf die Bibliothek *Unirest* (http://unirest.io/java.html) und recherchieren sie den notwendigen REST-Aufruf in der Marathon Dokumentation (https://mesosphere.github.io/marathon/docs/rest-api.html).
* Lassen sie sich die aktuell laufenden Applikationen ausgeben.
* Ersetzen sie die Platzhalter `<MEINPORT>` in der Datei `nginx-cluster.json` durch den zugeteilten Port.
* Programmieren sie im Anschluss die Methode `submitApp()` aus, mit der ein neuer Job in Marathon eingestellt werden kann.
* Starten sie mit ihrem Programm den NGINX-Job in Marathon und überprüfen sie, ob die NGINX-Seite aus dem Browser heraus aufgerufen werden kann: http://http://54.93.52.133:[MEINPORT]. Die hier genutzte IP ist die die IP des öffentlichen DC/OS Slaves, auf dem wir die Container deployed haben und der über eine öffentliche IP-Adresse erreichbar ist.

### Das NGINX-Cluster hochskalieren
* Öffnen sie die Datei `nginx-cluster.json` und ersetzen sie die bestehende ID durch eine beliebige neue ID und setzen sie den `hostPort` auf 0. Das bedeutet, dass sich Mesos um die Vergabe des Host-Ports kümmert und einen Port-Konflikt vermeidet.
* Entfernen sie das Constraint, dass die Tasks nur auf dem öffentlich zugänglichen Slave-Knoten laufen können.
* Erhöhen sie die Anzahl der Instanzen.
* Starten sie mit ihrem Programm den NGINX-Job in Marathon.
