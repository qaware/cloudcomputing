# Übung: Verteile Berechnung mit Apache Spark
Apache Spark ist ein Framework, um Berechnungen in einem Cluster zu verteilen.
Damit Somit erlaubt Apache Spark große Datenmengen zu verarbeiten, wozu ein einzelner Rechner üblicherweise nicht in der Lage ist.

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) 
und laden sie die Vorlage zur Übung von github in das Verzeichnis herunter 
(über `git clone` oder Download des Repositories als ZIP).
* Öffnen sie das Verzeichnis dann als Projekt innerhalb von Netbeans.
* Führen Sie das *Maven Goal* `clean install` aus.
* Öffnen sie die Website von Apache Spark (http://spark.apache.org/) für Recherchezwecke.
* Laden sie sich das vorkonfigurierte Apache Spark herunter 
(https://www.dropbox.com/s/0063mzb2jg3pfdh/spark-1.6.1-bin-hadoop2.6.zip?dl=0) 
und entpacken sie es in dem Verzeichnis, in dem auch das *vorlage* Verzeichnis zu finden ist.
* **Nur Windows:** Laden sie noch zusätzlich Hadoop herunter (http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.6.2/hadoop-2.6.2.tar.gz). Setzen sie anschließend die Umgebungsvariable HADOOP_HOME in ihrer Entwicklungsumgebung (z.B.: Eclipse Run -> Run Configurations .. -> Environment).

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

4) Word Count und Log-Analyse im Spark Cluster ausführen (**NUR LINUX!**)

* Stellen sie sicher, dass sie die vorkonfigurierte Variante von Spark heruntergeladen haben und das Verzeichnis stimmt.
* In der Vorlage finden sie zwei Skripte *startSparkStandalone.sh* und *stopAllsparkProcesses.sh*. Mit diesen können sie das Cluster starten und wieder beenden.
* Im Skript *startSparkStandalone.sh* muss die Spark-Master URL beim Starten der Spark-Worker (Slaves) angepasst werden.
* Anschließend müssen sie noch das *SPARK_WORKER_DIR* in der Datei *spark-1.6.1-bin-hadoop2.6/conf/spark-env.sh* anpassen, indem sie ein beliebiges existierendes Verzeichnis angeben.
* Starten sie anschließend das Cluster. Der Master ist unter der URL http://localhost:8080/ erreichbar.
* Prüfen sie in der UI ob alle Worker (2 Stück) ebenfalls gestartet sind.
* Modifizieren sie die Spark-Master URLs in den Anwendungen, indem sie die Master-URL auf spark://{IP}:7077 setzen.
* Führen sie das Maven Goal `install` aus. Dadurch wird eine Jar-Datei erstellt, die beim Start der Anwendung auf die Worker verteilt wird.
* Starten sie die Anwendungen. Untersuchen sie die Abarbeitung mittels der Webseiten.
* Schauen sie sich auch die Job-Informationen unter http://localhost:4040 an. Was sehen sie?
