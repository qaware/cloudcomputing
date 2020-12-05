# Übung: Cloud Architektur
## Aufgabe 1: Eight Fallacies of Distributed Computing

Recherchieren Sie in Ihrer Gruppe die angegebenen Irrtümer / Trugschlüsse  der Verteilten Verarbeitung.
Sie können dafür diesen [Artikel](https://www.simpleorientedarchitecture.com/8-fallacies-of-distributed-systems/)
nutzen oder frei recherchieren. 

Bereiten Sie gemeinsam jeweils einen kurzen Foliensatz vor (1-3 Slides), in dem Sie
* das angesprochene Problem beschreiben
* mögliche Lösungen hierfür aufzeigen

Finden Sie einen Vertreter Ihrer Gruppe, der die Vorstellung übernimmt.

Bearbeiten Sie in den Gruppen die folgenden Punkte:

* Gruppe 1:
    * The network is reliable
    * Latency is zero
* Gruppe 2:
    * Bandwidth is infinite
    * The network is secure
* Gruppe 3:
    * Topology doesn't change
    * There is one administrator
* Gruppe 4:
    * Transport cost is zero
    * The network is homogeneous

## Aufgabe 2: Twelve Factor Apps

Die [Twelve Factor Apps](https://12factor.net/) beschreiben Methoden bzw. Empfehlungen zur Entwicklung von 
Cloud Anwendungen.

Recherchieren Sie in Ihrer Gruppe die angegebenen Faktoren. Sie können dafür diese 
[Slides](https://www.slideshare.net/Alicanakku1/12-factor-apps)
nutzen oder frei recherchieren.

Bereiten Sie gemeinsam jeweils einen kurzen Foliensatz vor (1 Slide je Factor), in dem Sie
* den Idee hinter dem jeweiligen Factor benennen
* die Empfehlung erläutern

Bearbeiten Sie in den Gruppen die folgenden Punkte:

* Gruppe 1:
    * Codebase
    * Dependencies
    * Configuration
* Gruppe 2:
    * Backing Services
    * Build, release, run
    * Processes
* Gruppe 3:
    * Port binding
    * Concurrency
    * Disposability
* Gruppe 4:
    * Dev/Prod Parity
    * Logs
    * Admin Processes

Finden Sie einen Vertreter Ihrer Gruppe, der die Vorstellung übernimmt.

### Aufgabe 3: Diagnosablity mit Prometheus
Ziel dieser Übung ist es, Prometheus als Tool zum Sammeln und Auswerten von Metriken kennen zu lernen.

Arbeiten Sie die folgenden Tutorials auf Katacoda durch. 
Hinweis: Im Browser steht Ihnen dabei eine umfassende Umgebung zur Verfügung. Nutzen Sie dies auch, um mit Prometheus
zu experimentieren.

1. [Getting started with Prometheus](https://www.katacoda.com/courses/prometheus/getting-started)
2. [Graphing Docker Metrics with Prometheus](https://www.katacoda.com/courses/prometheus/docker-metrics)

### Aufgabe 4: Raft Konsens Protokoll

Erarbeiten Sie die Funktionsweise vom Raft Protokoll mithilfe folgender
[Demo](http://thesecretlivesofdata.com/raft/).

