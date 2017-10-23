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

Editieren sie das `Vagrantfile` und weisen sie der virtuellen Maschine anschließend 1024MB Hauptspeicher zu.
```ruby
config.vm.box_check_update = false
config.vm.provider "hyperv" do |vb|
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
erneut das `Vagrantfile`. Leiten sie außerdem den Port `80` des Gast-Betriebssystems als Port `18080` auf das
Hostbetriebssystem weiter.

```ruby
config.vm.network "forwarded_port", guest: 80, host: 18080, host_ip: "127.0.0.1"

config.vm.provision "shell", inline: <<-SHELL
  sudo apt-get update
  sudo apt-get -y install nginx
  sudo service nginx start
SHELL
```

Führen sie die Provisionierung mit Vagrant aus, prüfen sie die erfolgreiche Installation indem sie mit einem Browser
die Index-Seite auf `http://<IP>:80/` aufrufen.

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
```

Im nächsten Schritt kopieren sie die folgenden Dateien von Host in das Gast-Betriebssystems und starten Nginx neu.

```bash
sudo cp /vagrant/vagrant/default /etc/nginx/sites-available/default
sudo cp /vagrant/vagrant/info.php /usr/share/nginx/www/info.php
sudo cp /vagrant/vagrant/index.html /usr/share/nginx/www/index.html

sudo service nginx reload
```

Führen sie die Provisionierung durch prüfen sie dass die `info.php` erfolgreich aufgerufen werden kann.

```bash
$ vagrant provision
$ vagrant ssh
$ wget <IP>:80/info.php
```


### Aufgabe 2: Docker Compose

In dieser Aufgabe sollen sie den Technologie Stack aus NGINX, PHP und MySQL mittels Docker Compose hochfahren. Verwenden sie hierfür Images von Docker Hub sowie selbst erzeugte Images.

(1) Legen sie ein Docker Compose File an und definieren sie einen MySQL 5.5 Service sowie einen Service für einen Nginx+PHP7 Container.

(2) Legen sie für das Nginx+PHP7 Image ein Dockerfile an. Folgen sie im Wesentlichen den Installationsanweisungen auf https://www.digitalocean.com/community/tutorials/how-to-install-linux-nginx-mysql-php-lemp-stack-on-centos-7

Die wesentlichen Änderungen der Konfigurationsdateien sind:
```bash
sed -i -e "s/;\?cgi.fix_pathinfo\s*=\s*1/cgi.fix_pathinfo = 0/g" /etc/php.ini && \
sed -i -e "s/daemonize = no/daemonize = yes/g" /etc/php-fpm.conf && \

sed -i -e "s/;\?listen.owner\s*=\s*nobody/listen.owner = nobody/g" /etc/php-fpm.d/www.conf && \
sed -i -e "s/;\?listen.group\s*=\s*nobody/listen.group = nobody/g" /etc/php-fpm.d/www.conf && \

sed -i -e "s/user = apache/user = nginx/g" /etc/php-fpm.d/www.conf && \
sed -i -e "s/group = apache/group = nginx/g" /etc/php-fpm.d/www.conf
```

(3) Starten sie die Container mit Docker Compose und prüfen sie ob sowohl Nginx und PHP laufen.
Prüfen sie mittels Docker ob alles Prozesse innerhalb des Containers sauber laufen.

## Quellen
Diese Übung soll auch eine eigenständige Problemlösung auf Basis von Informationen aus dem Internet vermitteln.
ö
Vagrant
* https://www.vagrantup.com/docs/index.html
* https://www.vagrantup.com/docs/boxes.html
* https://askubuntu.com/questions/134666/what-is-the-easiest-way-to-enable-php-on-nginx
* https://unix.stackexchange.com/questions/147261/installing-mysql-server-in-vagrant-bootstrap-shell-script-how-to-skip-setup
* https://www.digitalocean.com/community/tutorials/how-to-install-linux-nginx-mysql-php-lemp-stack-on-centos-7

Docker
* https://docs.docker.com/compose/overview/
* https://hub.docker.com/
