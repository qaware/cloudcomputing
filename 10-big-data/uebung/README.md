# BigData - Übung

## Übung 1: MapReduce

Recherchieren Sie, wie MapReduce funktioniert, zum Beispiel indem Sie folgendes Video anschauen:

[MapReduce](https://www.youtube.com/watch?v=cvhKoniK5Uo).

Listen Sie die Vor- und Nachteile vom beschriebenen Vorgehen auf. Wann macht MapReduce Sinn, wann eher nicht?
Sammeln Sie dies auf einer Folie in Ihrer Gruppe.

Zählen Sie mittels MapReduce das Auftreten der einzelnen Wörter in der ersten Strophe von "O Tannenbaum"
(mit Stift und Papier, Sie müssen hier noch nichts implementieren):

```
O Tannenbaum, o Tannenbaum,
wie grün sind deine Blätter!
Du grünst nicht nur zur Sommerszeit,
nein, auch im Winter, wenn es schneit.
O Tannenbaum, o Tannenbaum,
wie grün sind deine Blätter!
```

## Übung 2: Verteilte Berechnung mit Apache Ignite

Hinweis: Diese Übung wurde mit IntelliJ erstellt und lässt sich am besten damit öffnen, funktioniert aber auch mit anderen Editoren. Benutzen sie JDK 11 zum ausführen der Übungen (dies ist notwendig, da der docker-container ebenfalls JDK 11 benutzt und Ignite bei abweichenden JDK Versionen Fehler produzieren kann). 
Öffnen Sie dazu am besten nur das Verzeichnis `uebung`. Wechseln sie auch auf Ihrer Shell in das Verzeichnis.

Für alle folgenden Übungen muss ein lokales Ignite-Cluster laufen. Starten Sie es mit `docker-compose build && docker-compose up`.

Wenn Änderungen am Code nicht wie erwartet ins Cluster deployed werden, kann es erforderlich sein, das Cluster neu zu starten.

### Testen ob Apache Ignite lokal funktioniert.

* Öffnen sie die Klasse `HelloWorld` und führen sie diese aus. Dies ist ein kleiner Test.
* Wenn der Test erfolgreich durchgelaufen ist (keine Fehler) haben sie bereits einen Task in Apache Ignite ausgeführt. 
* Versuchen sie, den Ignite-Cache auf dem Cluster auszugeben.

### Word Count mit Apache Ignite und MapReduce

* Öffnen sie im Editor die Klassen `WordCountMapReduce` und `WordCountSplitAdapter` und implementieren sie alle Code-Abschnitte, die mit einem `TODO`-Kommentar versehen sind.
* Führen Sie `WordCountMapReduce` aus. Untersuchen sie die Ausführungszeiten in der Ausgabe? Was sehen Sie? Woran liegt das?
* Was ändert sich, wenn Sie die Partitionsgröße mit der Konstante `PARTITION_SIZE` in der Klasse `WordCountSplitAdapter` verändern?

### Word Count mit Apache Ignite und Streaming

* Öffnen sie im Editor die Klassen `WordCountStreaming` und `WordStreamCounter` und implementieren sie alle Code-Abschnitte, die mit einem `TODO`-Kommentar versehen sind.
* Führen Sie `WordCountStreaming` aus. Was passiert jetzt?
* Führen Sie `WordStreamCounter` aus. Wie sind die erhaltenen Daten zu interpretieren?
* Was ändert sich, wenn Sie den Wert der Konstante `CACHE_SLIDING_WINDOW_SECONDS` in der Klasse `IgniteConfigurationProvider` erhöhen? 
