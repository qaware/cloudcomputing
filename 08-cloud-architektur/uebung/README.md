# Übung: Softwarearchitektur für die Cloud mit Hazelcast
Hazelcast ist eine Bibliothek, die Datenstrukturen so umsetzt, dass sie im Cluster
genutzt werden können. Dies bedeutet, dass alle Instanzen in einem Cluster die selben Daten sehen und Änderungen an den Daten cluster-weit synchrnisiert werden. Hazelcast ist somit ein Mechanismus für cluster-weiten Zustand.  

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter (über `git clone` oder Download des Repositories als ZIP).
* Öffnen sie das Verzeichnis dann als Projekt innerhalb von Netbeans.
* Führen Sie das *Maven Goal* `clean package` aus.
* Öffnen sie die Website von Hazelcast (http://hazelcast.org) für Recherchezwecke.

## Ziel
Wir entwickeln eine Chat-Anwendung, bei der ein Text in der Kommandozeile eingegeben werden kann und sich dann automatisch im Cluster aller Chat-Instanzen synchronisiert.

## Aufgaben
1. Wählen sie aus der Hazelcast-Dokumentation eine passende Datenstruktur aus (http://docs.hazelcast.org/docs/3.5/manual/html-single/index.html#distributed-data-structures). Wie Datenstrukturen zu verwenden sind, entnimmt man am besten dem Beispiel direkt von der Hazelcast Homepage (http://hazelcast.org).
*	Öffnen sie im Editor die Klasse Chatter und ergänzen sie die Code-Abschnitte, die mit einem `TODO` Kommentar versehen sind.
*	Starten sie eine Instanz der Chatter Klasse und beobachten sie die Log-Ausgaben. Was passiert bei der Initialisierung von Hazelcast?
*	Starten sie eine zweite Instanz der Klasse und beobachten sie nun ebenfalls die Log-Ausgaben – auch die Ausgabe der ersten Instanz, die gestartet wurde.
*	Geben sie in der Konsole beider Instanzen jeweils eine Nachricht ein und beobachten sie die resultierenden Ausgaben auf der Konsole. Hierbei kann es nun passieren, dass sie auch die Nachrichten anderer Übungs-Teilnehmer sehen. Wie können sie dies verhindern?
*	Starten sie eine dritte Instanz und beobachten sie die Ausgaben auf der Konsole. Was fällt hierbei auf?
* Terminieren sie eine der drei Instanzen und geben sie in einer verbleibenden Instanz eine Nachricht ein. Funktioniert das Hazelcast-Cluster weiterhin?
