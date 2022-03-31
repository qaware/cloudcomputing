# Übung: Virtualisierung

Ziel dieser Übung ist es mit Vagrant einen einfachen Stack aus NGINX, PHP und MySQL aufzusetzen.

## Vorbereitung

1. Prüfen Sie, dass Virtualbox und Vagrant installiert sind und funktionieren. Je nach Betriebssystem nutzen sie anstatt Virtualbox entweder Hyper-V, kvm, xhyve oder Parallels als Virtualisierungssoftware.

## Aufgaben

### Aufgabe 1: Vagrant und (Virtualbox)

Bei dieser Aufgabe geht es darum den Technologie-Stack aus NGINX, PHP und MySQL innerhalb einer virtuellen Maschine mit Hilfe von Vagrant hochzufahren.

(1) In einem ersten Schritt legen sie zunächst mit Vagrant eine Ubuntu 20.04 Basis Box an.

```bash
$ vagrant init ubuntu/focal64
```

Editieren sie das `Vagrantfile` und weisen sie der virtuellen Maschine anschließend 1024 MB Hauptspeicher zu.
```ruby
 config.vm.box_check_update = false
 config.vm.provider "virtualbox" do |vb|
     vb.memory = "1024"
 end
```

Fahren sie anschließend die VM hoch (dauert etwas) und melden sie sich per SSH an.

```bash
$ vagrant up --provider virtualbox
$ vagrant ssh
```

(2) Installieren sie nun als ersten den NGINX Server in der VM mittels Shell Provisioner. Editieren sie hierfür
erneut das `Vagrantfile`. Leiten sie außerdem den Port `80` des Gast-Betriebssystems als Port `18080` auf das
Hostbetriebssystem weiter.

```ruby
config.vm.network "forwarded_port", guest: 80, host: 8080, host_ip: "127.0.0.1"

config.vm.provision "shell", inline: <<-SHELL
  sudo apt-get update
  sudo apt-get install -y  nginx
  sudo systemctl start nginx
SHELL
```

Führen sie die Provisionierung mit Vagrant aus, prüfen sie die erfolgreiche Installation indem sie mit einem Browser auf dem Host
die Index-Seite auf `http://localhost:18080/` aufrufen.

```bash
$ vagrant provision
oder
$ vagrant reload --provision
```

(3) Installieren sie nun ähnlich zu Schritt (2) die mySQL Server und Client Pakete mittels Shell Provisioner in der VM.

```bash
# Mysql
debconf-set-selections <<< 'mysql-server mysql-server/root_password password secret'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password secret'
sudo apt-get install -y mysql-server mysql-client
sudo systemctl start mysql
```

Melden sie sich nach der Installation per SSH an und prüfen sie ob mySQL läuft.
```bash
$ vagrant provision
$ vagrant ssh
$ mysql -u root -p
```

(4) In diesem Schritt installieren wir nun PHP5 und konfigurieren den NGINX Server entsprechend. Erweitern sie die
Provisionierung und installieren sie die Pakete `php5-fpm` und `php5-mysqlnd`.

```bash
# PHP
sudo apt-get install -y php php-fpm php-mysql
sudo sed -i -e "s/;\?cgi.fix_pathinfo\s*=\s*1/cgi.fix_pathinfo = 0/g" /etc/php/7.4/fpm/php.ini
sudo systemctl start php7.4-fpm
```

Im nächsten Schritt kopieren sie die folgenden Dateien von Host in das Gast-Betriebssystems und starten Nginx neu.
Die Dateien müssen Sie evtl. vorher in das Verzeichnis kopieren, in dem das Vagrantfile liegt.

```bash
sudo mkdir -p /usr/share/nginx/www
sudo cp /vagrant/vagrant/default /etc/nginx/sites-available/default
sudo cp /vagrant/vagrant/info.php /usr/share/nginx/www/info.php
sudo cp /vagrant/vagrant/index.html /usr/share/nginx/www/index.html

sudo systemctl reload nginx
```

Führen sie die Provisionierung durch prüfen sie dass die `info.php` erfolgreich aufgerufen werden kann.

```bash
$ vagrant provision
$ vagrant ssh
$ wget localhost:80/info.php
```

## Quellen
Diese Übung soll auch eine eigenständige Problemlösung auf Basis von Informationen aus dem Internet vermitteln. Sie können dazu für die eingesetzten Technologien z.B. die folgenden Quellen nutzen:
* Die Dokumentation von Vagrant: https://docs.vagrantup.com
* Dokumentation des Vagrantfile Formats: https://www.vagrantup.com/docs/vagrantfile
* Dokumentation der Vagrant Kommandozeilenbefehle: https://www.vagrantup.com/docs/cli
