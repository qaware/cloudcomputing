#!/bin/bash
set -euxo pipefail

apt-get update
apt-get install -y busybox cowsay
source /etc/environment

echo "<pre>" >> index.html
/usr/games/cowsay -f dragon ${message} >> index.html
echo "</pre>" >> index.html

nohup busybox httpd -f index.html -p 8080 &
