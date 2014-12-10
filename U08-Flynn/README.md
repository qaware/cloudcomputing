# Vorbereitung
 * Verzeichnisse "controller" und "slave erstellen"
 * Boxes kopieren
 * vagrant box add flynn-base.box --name=flynn-base
 * vagrant box add centos.box --name=centos

# Images aufsetzen

## Slave Image aufsetzen
1. Verzeichnis "slave" im Übungsverzeichnis erstellen
2. In diesem Verzeichnis eine Datei "Vagrantfile" mit folgendem Inhalt erstellen:

	Vagrant.configure(2) do |config|
  		config.vm.box = "flynn-base"
  		config.vm.provision "shell", privileged: false, inline: <<SCRIPT
    		sudo start flynn-host
    		CLUSTER_DOMAIN=demo.localflynn.com \
    		flynn-host bootstrap /etc/flynn/bootstrap-manifest.json 2>&1
		SCRIPT
	end


 `git clone https://github.com/flynn/flynn`

 `cd flynn/demo`

 `vagrant up`

flynn cluster add -g demo.localflynn.com:2222 -p P9fDwHqhUC2yHjxVlAWh/eOVQaq5fuJkso1YM8uGPoY= default https://controller.demo.localflynn.com b43aadebb76730ef296d3d76567ad07



## CentOS Controller Image mit Vagrant aufsetzen
* CentOS COntroller Image mit Vagrant aufsetzen
* Login auf Root umbiegen. Vagrantfile:

  `config.ssh.username = 'root'`

  `config.ssh.password = 'vagrant'`

  `config.ssh.insert_key = 'true'`
* Image starten und per Konsole verbinden
* git installieren: 

  `yum install -y git`

* Flyn Kommandozeilenwerkzeuge installieren

  `L=/usr/local/bin/flynn && curl -sL -A "``uname -sp``" https://cli.flynn.io/flynn.gz | zcat >$L && chmod +x $L`

* SSH key erzeugen (bel. Passwort eingeben)

  `ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa`



- SSH key dem Flyyn Client bekannt machen (der in ~/.ssh/id_rsa per Default): https://flynn.io/docs/cli#key
flynn key add
- Beispielapplikation holen
git clone https://github.com/flynn/nodejs-flynn-example.git
cd nodejs-flynn-example
- git Remotes anzeigen lassen
git remote -vgit push 
- Flynn Applikation erstellen (trägt nur Flynn remotes ein): https://flynn.io/docs/cli#create
flynn create example
- git Remotes anzeigen lassen (prüfen ob Flynn remotes eingetragen)
git remote -v
- Flynn Routen anzeigen
flynn route
- Applikation pushen in Flynn PaaS
git push flynn master
- Analyse des Logs was alles passiert (insb. bzgl. Buildpacks)
> App detection
> Buildpack Ausführung
-  App im Browser aufrufen
http://example.demo.localflynn.com/
- Nano installieren (http://www.howtogeek.com/howto/42980/the-beginners-guide-to-nano-the-linux-command-line-text-editor/)
yum install nano
- Response-Nachricht editieren (Ctrl + X zum Beenden plus 2 x y)
nano web.js
- Änderung übertragen
git add web.js
git commit -m "Neue Antwort"
git push flynn master
-  App im Browser aufrufen
http://example.demo.localflynn.com/

# Infos und TODOs
* https://flynn.io/docs/using-flynn
* https://flynn.io/docs/cli
* Procfile ???
* Weitere Mittel in Using Flynn sichten (insb. Skalierung)
* Beispiel auf Java umstellen (REST App mit/ohne Dropwizard)
* Kür: Postgres als DB mit einführen (https://github.com/flynn/flynn)