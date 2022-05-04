# Übung 1: MapReduce
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


# Übung 2: Verteilte Berechnung mit Apache Spark

Apache Spark ist ein Framework, um Berechnungen in einem Cluster zu verteilen.
Damit erlaubt Apache Spark große Datenmengen zu verarbeiten, wozu ein einzelner Rechner üblicherweise nicht in der Lage ist.

## Vorbereitung
* Öffnen sie das Vorlagen-Verzeichnis der Übung als Projekt innerhalb der Entwicklungsumgebung.
* Führen Sie das *Maven Goal* `clean install` aus.
* Öffnen sie die Website von Apache Spark (http://spark.apache.org/) für Recherchezwecke.

## Ziel
Wir führen verschiedene Berechnungen und Analysen mit Apache Spark durch:

1. Word Count: Wir zählen die Auftreten einzelner Wörter.
2. Log-Analyse: Wir analysieren eine Logdatei, zählen die Queries und summieren die Laufzeiten.

Diese Berechnungen führen wir zunächst lokal und anschließend in einem Cluster aus.
Um Apache Spark lokal zu starten, muss der Spark Master auf *local* gesetzt werden.
```
  SparkConf conf = new SparkConf()
      .setAppName("Cloud Computing")
      .setMaster("local[4]"); //four threads
```

Um die Berechnung in einem Spark-Cluster auszuführen reicht es die Spark Konfiguration etwas anzupassen.
Hierzu muss jedoch ein Cluster verfügbar sein ;-)
```
  SparkConf conf = new SparkConf()
      .setAppName("Cloud Computing")
      .setMaster("spark://{IP}:7077");
```

![Zielbild](zielbild.png)

## Wichtige Adressen beim Arbeiten mit Apache Spark
### Cluster
* Spark-Master URL: spark://{IP}:7077
* Spark-Master UI: http://{IP}:8080
* Spark-Worker-1 UI: http://{IP}:8081
* Spark-Worker-2 UI: http://{IP}:8082

### Lokal
* Spark Job UI: http://{IP}:4040
* Spark lokal starten über, z.B.: local[4], wobei 4 die Anzahl der gestarteten Threads angibt.

## Aufgaben
1) Testen ob Apache Spark lokal funktioniert.

* Öffnen sie im Editor die Klasse *SparkInProcessTester.java* und führen sie diese aus. Dies ist ein kleiner Test.
* Wenn der Test erfolgreich durchgelaufen ist (keine Fehler) haben sie bereits Apache Spark ausgeführt. 
* Gegebenfalls müssen Sie folgende Umgebungsvariablen setzen:
   * SPARK_MASTER_IP=127.0.0.1 
   * SPARK_LOCAL_IP=127.0.0.1

2) Word Count mit Apache Spark und Java 8

* Öffnen sie im Editor die Klassen *SparkWordCount.java* und *JavaWordCount.java* und implementieren sie alle Code-Abschnitte, die mit einem `TODO` Kommentar versehen sind.
* Während der Entwicklung sollten sie die Anwendung lokal ausführen (Debug, usw. möglich).
* Führen *SparkWordCount.java* und anschließend *JavaWordCount.java* lokal aus. Untersuchen sie die Ausführungszeiten in der Ausgabe? Was sehen Sie? Woran liegt das?
* Schauen sie sich auch die Job-Informationen unter http://localhost:4040 an. Was sehen sie?

3) Log-Analyse mit Apache Spark

* Öffnen sie im Editor die Klassen *SparkAnalyzeLog.java* und implementieren sie alle Code-Abschnitte, die mit einem `TODO` Kommentar versehen sind.
* Während der Entwicklung sollten sie die Anwendung lokal ausführen (Debug, usw. möglich).
* Führen Sie die fertige Anwendung aus. Wie viele Requests sind im Log? Wie lange benötigen diese?
* Schauen sie sich auch die Job-Informationen unter http://localhost:4040 an. Was sehen sie?
