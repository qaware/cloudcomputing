# Die Übung
Ziel dieser Übung ist es, auf dem lokalen Rechner eine Flynn PaaS Cloud zu erstellen oder dort eine Node.JS Anwendung zu deployen. Node.JS ist ein Anwendungsserver für JavaScript.

Vorbedingungen zur Übung sind: 
* Eine aktuelle Vagrant- und VirtualBox-Version ist installiert.
* Die beiden Vagrant Boxen _flynn-base.box_ und _centos.box_ sind ins Übungsverzeichnis kopiert.

# Schritt 1: Images aufsetzen

## Schritt 1.1: Slave Image aufsetzen
1. Die Vagrant die Box _flynn-base.box_ bekannt machen:
   `vagrant box add flynn-base.box --name=flynn-base`
2. Verzeichnis _slave_ im Übungsverzeichnis erstellen.
3. In diesem Verzeichnis eine Datei _Vagrantfile_ mit folgendem Inhalt erstellen:
```ruby
	Vagrant.configure(2) do |config|
	  config.vm.box = "flynn-base"
	  config.vm.network "private_network", ip: "192.0.2.200"
	  config.vm.provision "shell", privileged: false, inline: <<SCRIPT
	    sudo start flynn-host
	    CLUSTER_DOMAIN=demo.localflynn.com \
	    flynn-host bootstrap /etc/flynn/bootstrap-manifest.json 2>&1
	SCRIPT
	end
```

4. Die Vagrant Box starten. Die letzte Log-Meldung in der Konsolenausgabe dabei in eine Text-Datei kopieren. Hier eine beispielhafte entsprechende Log-Meldung:

`flynn cluster add -g demo.localflynn.com:2222 -p P9fDwHaDeFgeyHjxVlAWh/eOVQaq5fuJkso1YM8uGPoY= default https://controller.demo.localflynn.com b43aadebdf4730ef296d3d76567ad07`

## Schritt 1.2: Controller Image aufsetzen
1. Die Vagrant die Box _centos.box_ bekannt machen:
   `vagrant box add centos.box --name=centos`
2. En Verzeichnis _controller_ im Übungsverzeichnis erstellen und dort ein Vagrant Image auf Basis der _centos_ Box initialisieren. Dabei dann die folgenden Zeilen an der entsprechenden Stelle im Vagrantfile ergänzen (wir werden Root-Rechte in der Box benötigen):
```ruby
  config.ssh.username = 'root'
  config.ssh.password = 'vagrant'
  config.ssh.insert_key = 'true'
```
3. Das Vagrant Image starten und per SSH-Konsole verbinden. Falls auf dem Rechner kein SSH Client installiert ist, so können sie unter Windows den folgenden, bereits aus vorhergehenden Übungen bekannten, SSH Client verwenden: https://dl.dropboxusercontent.com/u/3318749/ssh.zip.
4. _git_ (VCS) und _nano_ (Editor) installieren über den _yum_ Paketmanager (vergleichbar mit dem bisher bekannten _apt-get_). Eine Anleitung zur Verwendung von _nano_ ist hier zu finden: http://www.howtogeek.com/howto/42980/the-beginners-guide-to-nano-the-linux-command-line-text-editor.
  * `yum install -y git`
  * `yum install nano` 
 
5. Das Flynn Client Kommandozeilenwerkzeug erstellen über den folgenden Befehl:

  `L=/usr/local/bin/flynn && curl -sL -A "``uname -sp``" https://cli.flynn.io/flynn.gz | zcat >$L && chmod +x $L`
6. Einen SSH key erzeugen (beliebiges Passwort wählen) und dem Flynn CLient bekannt machen:
  * `ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa`
  * `flynn key add`

## Schritt 2: Beispielapplikation holen und deployen 
1. Die Node.JS Beispielapplikation holen:
`git clone https://github.com/flynn/nodejs-flynn-example.git`.
Wechseln sie nun in das Verzeichnis der Beispielapplikation uns lassen sie sich die Liste der Dateien dort ausgeben. Die Dateien _package.json_ und _web.js_ sind Node.JS-spezifisch. Die Datei _Procfile_ ist Flynn-spezifisch. Was ist im _Procfile_ beschrieben? Lassen sie sich den Inhalt der Datei anzeigen und recherchieren sie das Konzept des Procfiles in Flynn im Internet.
2. Das lokale git-Repository mit der Beispielapplikation an Flynn binden mit dem Anwendungsnamen _example_:
`flynn create example`. Anmerkungen: Dabei wird nur ein Verweis auf ein Remote-Repository gesetzt, das dem Git Receiver der Flynn PaaS Cloud entspricht. In dieses Remote Repository kann der Code der Beispielapplikation jederzeit per `git push flynn master` übertragen werden. Sie können sich die verfügbaren Remote Repositories für ein lokales git Repository über den Befehl `git remote -v` anzeigen lassen.
3. Übertragen sie die Beispielapplikation und analysieren sie die Log-Ausgabe, was dabei in der PaaS Cloud passiert. Rufen sie die Anwendung direkt in ihrem Browser auf. Sie ist über dei URL http://example.demo.localflynn.com erreichbar.
4. Inspizieren sie den aktuellen Zustand der PaaS Cloud. Dabei helfen ihnen die folgenden Kommandos. Die Dokumentation der Kommandos finden sie hier: https://flynn.io/docs/cli.
  * `flynn ps`: Zeigt die laufenden Applikationen (Container / Jobs) an.
  * `flynn route`: Zeigt die aktiven Routen an.
  * `flynn log`: Zeigt die Log-Ausgabe einer Applikation an.


## Schritt 3: Applikation verändern und neuen Stand deployen
1. Ändern sie die Response-Nachricht in der Beispielapplikation innerhalb der Datei _web.js_.
2. Übertragen sie die Änderungen in das lokale git Repository: `git commit -am "Neue Antwort"`.
3. Übertragen sie die Anwendung in die PaaS Cloud.
4. Testen sie im Browser, ob die Änderungen sichtbar sind.

## Schritt 4: Applikation skalieren
1. Skalieren sie die Applikation auf 3 Instanzen mit dem Befehl `flynn scale`.
2. Überprüfen sie per `flynn ps`, ob alle Instanzen laufen.
3. Überprüfen sie über mehrfache Aufrufe im Browser, dass das automatische Load Balancing funktioniert.

## Kür: Applikation in anderer Sprache deployen
Erstellen sie ein lokales git Repository und hinterlegen sie dort eine kleine selbstgeschriebene Beispielapplikation in einer anderen von Flynn unterstützten Sprache (z.B. Java, PHP oder Ruby). Eine Anleitung dazu finden sie in den Flynn Language Guides unter https://flynn.io/docs. Deployen sie die Beispielapplikation in der Flynn Cloud.
