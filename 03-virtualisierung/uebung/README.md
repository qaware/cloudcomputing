# Übung: Virtualisierung

Ziel dieser Übung ist es sowohl mit Hyper-V und Vagrant als auch mit Docker einen einfachen Stack
aus NGINX, PHP und MySQL aufzusetzen.

## Vorbereitung

1. Prüfen sie dass Hyper-V und Vagrant installiert sind und funktionieren. Je nach Betriebssystem nutzen
sie anstatt Hyper-V entweder VirtualBox, kvm oder xhyve als Virtualisierungssoftware.

2. Prüfen sie dass Docker (for Windows oder for Mac) auf ihrem Rechner installiert sind.


## Aufgaben

### Aufgabe 1: Vagrant und (Hyper-V)

Bei dieser Aufgabe geht es darum den Technologie Stack aus NGINX, PHP und MySQL innerhalb einer virtuellen Maschine
mit Hilfe von Vagrant hochzufahren.

(1) In einem ersten Schritt legen sie zunächst mit Vagrant eine Ubuntu 16.04 Basis Box an.

```bash
$ vagrant init hashicorp/precise64
```

Editieren sie das `Vagrantfile` und weisen sie der virtuellen Maschine anschließend 1024MB Hauptspeicher
zu und aktivieren sie die GUI.

```ruby
config.vm.box_check_update = false
config.vm.provider "virtualbox" do |vb|
  # Display the VirtualBox GUI when booting the machine
  vb.gui = true

  # Customize the amount of memory on the VM:
  vb.memory = "1024"
end
```

Fahren sie anschließend die VM hoch (dauert etwas) und melden sie sich per SSH an.

```bash
$ vagrant up --provider hyperv
$ vagrant ssh
```

(2) Installieren sie nun als ersten den NGINX Server in der VM mittels Shell Provisioner. Editieren sie hierfür
erneut das `Vagrantfile`. Leiten sie außerdem den Port `80` des Gast-Betriebssystems als Port `8080` auf das
Hostbetriebssystem weiter.

```ruby
config.vm.network "forwarded_port", guest: 80, host: 8080, host_ip: "127.0.0.1"

config.vm.provision "shell", inline: <<-SHELL
  sudo apt-get update
  sudo apt-get -y install nginx
  sudo service nginx start
SHELL
```

Führen sie die Provisionierung mit Vagrant aus, prüfen sie die erfolgreiche Installation indem sie mit einem Browser
die Index-Seite auf `http://localhost:8080/` aufrufen.

```bash
$ vagrant provision
oder
$ vagrant reload --provision
```

(3) Installieren sie nun ähnlich zu Schritt (2) die mySQL Server und Client Pakete mittels Shell Provisioner in der VM.

```bash
debconf-set-selections <<< 'mysql-server mysql-server/root_password password secret'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password secret'
sudo apt-get -y install mysql-server mysql-client
sudo service mysql start
```

Melden sie sich nach der Installation per SSH an und prüfen sie ob mySQL läuft.
```bash
$ vagrant provision
$ vagrant ssh
$ mysql -u root
```

(4) In diesem Schritt installieren wir nun PHP5 und konfigurieren den NGINX Server entsprechend. Erweitern sie die
Provisionierung und installieren sie die Pakete `php5-fpm` und `php5-mysqlnd`.

```bash
sudo apt-get -y install php5-fpm php5-mysqlnd
sudo service php5-fpm start

sudo cp /vagrant/nginx/default /etc/nginx/sites-available/default
sudo cp /usr/nginx/info.php /usr/share/nginx/www/info.php

sudo service nginx reload
```

Führen sie die Provisionierung durch prüfen sie dass die `info.php` erfolgreich aufgerufen werden kann.

```bash
$ vagrant provision
$ vagrant ssh
$ wget localhost:8080/info.php
```


### Aufgabe 2: Docker Compose

In dieser Aufgabe sollen sie den Technologie Stack aus NGINX, PHP und MySQL mittels Docker Compose hochfahren.
Verwenden sie dafür Images von Docker Hub.



## Quellen
Diese Übung soll auch eine eigenständige Problemlösung auf Basis von Informationen aus dem Internet vermitteln.
Sie können dazu für die eingesetzten Technologien z.B. die folgenden Quellen nutzen:

Vagrant
* https://www.vagrantup.com/docs/index.html
* https://www.vagrantup.com/docs/boxes.html
* https://askubuntu.com/questions/134666/what-is-the-easiest-way-to-enable-php-on-nginx
* https://www.howtoforge.com/tutorial/installing-nginx-with-php7-fpm-and-mysql-on-ubuntu-16.04-lts-lemp/
https://unix.stackexchange.com/questions/147261/installing-mysql-server-in-vagrant-bootstrap-shell-script-how-to-skip-setup
* https://www.digitalocean.com/community/tutorials/how-to-install-linux-nginx-mysql-php-lemp-stack-in-ubuntu-16-04

Docker
* https://docs.docker.com/compose/overview/
* https://hub.docker.com/
