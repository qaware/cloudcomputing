# Übung: Cluster Scheduling

## Übung 1: Binpack und Spread

### Schritt 1:
Recherchieren Sie je einen Approximationsalgorithmus für das Bin Packing Problem.
* Gruppe 1: First Fit Decreasing
* Gruppe 2: Best Fit
* Gruppe 3: Worst Fit
* Gruppe 4: Next Fit

Bereiten Sie eine kleine Präsentation vor (~1 Folie), die den Algorithmus erklärt.
Finden Sie in Ihrer Gruppe einen Vertreter, der den Algorithmus im Anschluss vorstellt.

### Schritt 2:
Berechnen Sie mit dem Algorithmus, den Ihre Gruppe erarbeitet hat, das Scheduling
 für folgende Ressourcen und Jobs:
- Nehmen Sie an, dass alle Ressourcen die Größe 20 haben.
- Verteilen Sie Jobs der Größen 11,2,15,5,6,17,7 auf die Ressourcen.
- Wieviele Ressourcen-Einheiten benötigen Sie?
- Wie verteilen sich die Jobs auf die Ressourcen?

Bereiten Sie auch hierzu eine Folie vor, mit der Sie das Ergebnis vorstellen können.

### Schritt 3:
Recherchieren Sie, wie Round Robin Scheduling funktioniert.
Wann macht ein Round Robin Verfahren Ihrer Meinung nach Sinn?

## Übung 2: Dominant Resource Fairness
Nehmen Sie an, dass Ihnen insgesamt 84 CPUs und 12000 GB Memory zur Verfügung stehen.

Sie haben 2 Applikationen, für die sie mit DRF die Anzahl der Container bestimmen.

* Applikation A benötigt je Container 4 CPUs und 120 GB Memory
* Applikation B benötig je Container 1 CPU und 240 GB Memory

Wieviele Container von Applikation A und B können Sie jeweils starten, wenn Sie Ihr 
Scheduling mit DRF durchführen?

## Übung 3: Scheduling mit Nomad 

### Schritt 1: Cluster Scheduling mit Nomad - Theorie
Lesen Sie sich kurz in das Cluster Scheduling mit Nomad ein.
Nutzen Sie dafür die Nomad Dokumentation unter 

* https://www.nomadproject.io/intro
* https://www.nomadproject.io/docs/internals/scheduling

### Schritt 2: Katacoda Account einrichten

Bitte richten sie einen [Katacoda](https://www.katacoda.com/) Account für sich ein.

### Schritt 3: Katacoda Tutorial für Nomad - Praktische Erfahrungen sammeln

Arbeiten Sie das folgende Tutorial auf Katacoda durch. 
Dies vermittelt die Nutzersicht auf das Cluster Scheduling mit Nomad. 
Sie werden dabei sehen, dass sie dabei keine Informationen benötigen, 
auf welchen konkreten Maschinen die Container 
ausgeführt werden. 

Hinweis: Im Browser steht Ihnen dabei eine umfassende Umgebung zur Verfügung. 
Nutzen Sie dies auch, um mit Nomad zu experimentieren.

https://www.katacoda.com/hashicorp/scenarios/nomad-introduction