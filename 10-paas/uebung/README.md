# Übung: PaaS mit flynn

## Vorbereitung
* Erstellen sie ein Verzeichnis für die Übung (auf ihrem Home-Laufwerk oder lokal auf dem Rechner) und laden sie die Vorlage zur Übung von github in dieses Verzeichnis herunter (über `git clone` oder Download des Repositories als ZIP).
* Stellen sie sicher, dass eine aktuelle Vagrant- und VirtualBox-Version installiert ist. Stellen sie ebenso sicher, dass git installiert ist (https://git-scm.com).

## Ziel
Ziel dieser Übung ist es, auf dem lokalen Rechner eine _flynn_ PaaS Cloud (https://flynn.io) zu erstellen oder dort eine _Node.js_ Anwendung zu deployen. Node.js ist ein Anwendungsserver für JavaScript.

# Aufgaben

## Schritt 1: flynn Kommandozeilenwerkzeug installieren
* Installieren sie das flynn Kommandozeilenwerkzeug auf ihrem Rechner wie hier beschrieben: https://flynn.io/docs/cli.

## Schritt 2: flynn Cluster per Vagrant starten
* Wechseln sie in das Verzeichnis `uebung/vorlage` und starten sie die darin definierte Vagrant Box.
* Analysieren sie die Ausgabe der Provisionierung und führen sie den dort enthaltenen Befehl aus, um ein flynn Cluster zu erzeugen: `flynn cluster add ...`
* Öffnen sie im Browser die flynn Web-UI unter der folgenden URL: http://dashboard.demo.localflynn.com und folgenden sie den Anweisungen. Verwenden sie zum Login den Token, der bei der Provisionierung der Vagrant Box ausgegeben wurde. Alternativ erhalten sie den Token auch über den Befehl: `flynn -a dashboard env get LOGIN_TOKEN`

## Schritt 3: Beispielapplikation deployen

1. Die Node.js Beispielapplikation holen:
`git clone https://github.com/flynn/nodejs-flynn-example.git`.
Wechseln sie nun in das Verzeichnis der Beispielapplikation und lassen sie sich die Liste der Dateien dort ausgeben. Die Dateien _package.json_ und _web.js_ sind Node.js-spezifisch. Die Datei _Procfile_ ist Flynn-spezifisch und wurde als Konzept vom PaaS-Vorreiter Heroku übernommen. Was ist im _Procfile_ beschrieben? Lassen sie sich den Inhalt der Datei anzeigen und lesen sie sich in das Konzept hinter Procfiles ein (siehe unten aufgeführte Quelle).

2. Die Beispielapplikation als flynn Anwendung mit dem Namen _example_ erzeugen: `flynn create example`.
 * Hierbei wird ein Verweis auf ein Remote-Repository gesetzt, das dem git Receiver der flynn PaaS entspricht. In dieses Remote Repository kann der Code der Beispielapplikation jederzeit per `git push flynn master` übertragen werden. Lassen sie sich die verfügbaren Remote Repositories über den Befehl `git remote -v` anzeigen.
 * Ferner wird innerhalb der flynn PaaS eine Route auf die Applikation erzeugen. Lassen sie sich die Route per `flynn route` anzeigen.

3. Übertragen sie die Beispielapplikation und analysieren sie die Log-Ausgabe, was dabei innerhalb der PaaS passiert. Wechseln sie in die Web-UI von flynn und lassen sie sich die Applikationsseite anzeigen. Rufen sie die Anwendung aus der Web-UI heraus auf.

4. Inspizieren sie den aktuellen Zustand der PaaS Cloud. Dabei helfen ihnen die folgenden Kommandos. Die Dokumentation der Kommandos finden sie hier: https://flynn.io/docs/cli.
  * `flynn ps`: Zeigt die laufenden Applikationen (Container / Jobs) an.
  * `flynn log`: Zeigt die Log-Ausgabe einer Applikation an.


## Schritt 4: Applikation verändern und neuen Stand deployen
1. Ändern sie die Response-Nachricht in der Beispielapplikation innerhalb der Datei _web.js_.
2. Übertragen sie die Änderungen in das lokale git Repository: `git commit -am "Neue Antwort"`.
3. Übertragen sie die Anwendung in die PaaS Cloud.
4. Testen sie im Browser, ob die Änderungen sichtbar sind.

## Schritt 5: Applikation skalieren
1. Skalieren sie die Applikation auf 3 Instanzen mit dem Befehl `flynn scale`. Die Dokumentation zu diesem Befehl erhalten sie per  `flynn help scale`.
2. Überprüfen sie per `flynn ps`, ob alle Instanzen laufen.
3. Überprüfen sie über mehrfache Aufrufe im Browser, dass das automatische Load Balancing funktioniert.

## Kür: Applikation in anderer Sprache deployen
Erstellen sie ein lokales git Repository und hinterlegen sie dort eine kleine selbstgeschriebene Beispielapplikation in einer anderen von Flynn unterstützten Sprache (z.B. Java, PHP oder Ruby). Eine Anleitung dazu finden sie in den Flynn Language Guides unter https://flynn.io/docs. Deployen sie die Beispielapplikation in der Flynn Cloud.

## Quellen
 * flynn Dokumentation: https://flynn.io/docs
 * Procfile Dokumentation: https://devcenter.heroku.com/articles/procfile
 * git push Dokumentation: https://git-scm.com/docs/git-push
