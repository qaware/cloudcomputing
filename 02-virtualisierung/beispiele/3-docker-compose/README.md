# Übung: Virtualisierung

## Vorbereitung

Prüfen sie dass Docker und Docker Compose auf ihrem Rechner installiert sind.

### Aufgabe: Docker Compose

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

Docker
* https://docs.docker.com/compose/overview/
* https://hub.docker.com/
