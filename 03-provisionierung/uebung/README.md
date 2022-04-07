# Übung: Provisionierung mit Ansible und Docker Compose

## Übung 1: Provisionierung mit Docker und Docker Compose
Ziel dieser Übung ist, praktische Erfahrungen mit Docker und Docker Compose zu sammeln.

Wir wollen in dieser Übung:
- ein fertiges Docker Image nutzen
- ein Docker Image bauen
- ein Image über Docker Compose starten

### Schritt 1: Docker Image nutzen und Container starten
Nginx ist ein Open Source Reverse Proxy. 
Unter https://hub.docker.com/_/nginx ist dokumentiert, wie das offizielle Docker Image
genutzt werden kann.

Schauen Sie sich die Doku an und starten Sie über die Console einen Nginx Container.

Sorgen Sie dafür, dass der Port '80' auch auf Ihrem Host exponiert wird.

<details>
<summary>Hinweis, falls Sie nicht weiterkommen:</summary>

```
docker run -d -p 80:80 nginx
```
</details>

Rufen Sie dann testweise localhost:80 in Ihrem Browser auf.

### Schritt 2: Dockerfile schreiben und eigene index.html anlegen
In diesem Schritt wollen wir nicht mehr das fertige Image nutzen, sondern ein eigenes
angepasstes Image bauen.

Legen Sie hierfür zunächst eine index.html an. Diese Seite soll unser Webserver ausliefern, wenn er aufgerufen wird.
Nutzen Sie hierfür ein einfaches HTML, dass eine "Hello World" Begrüßung ausgibt.

Legen Sie ein Dockerfile an.
Führen Sie folgende Schritte aus:
- Nutzen Sie nginx:1.19-alpine als Basisimage.
- Kopieren Sie ihre 'index.html' nach '/usr/share/nginx/html/', 
indem Sie das Docker Command 'COPY' nutzen.
- Exponieren Sie den Port 80.
- Starten Sie nginx über das Command 'nginx'. Achten Sie darauf, -g 'daemon off;'
beim Start mit anzugeben, damit Nginx nicht direkt nach dem Containerstart beendet wird,
sondern im Vordergrund läuft.

<details>
<summary>Hinweis, falls Sie nicht weiterkommen:</summary>

```
FROM nginx:1.19-alpine
COPY index.html /usr/share/nginx/html
EXPOSE 80
CMD nginx -g 'daemon off;'
```
</details>

### Schritt 3: Docker Image bauen
Bauen Sie das Docker Image mit dem Namen cc-nginx und dem Tag v1.

<details>
<summary>Hinweis, falls Sie nicht weiterkommen:</summary>

```docker build . -t cc-nginx:v1```
</details>

Prüfen Sie danach über ```docker images```, dass das neue Docker Image in Ihrer
lokalen Registry liegt.

### Schritt 4: Container starten
Starten Sie den Container.
Mappen Sie dabei den Port 8080 auf Ihrem Host auf den Container Port 80.

<details>
<summary>Hinweis, falls Sie nicht weiterkommen:</summary>

```docker run -it -p 8080:80 cc-nginx:v1```
</details>

Rufen Sie den gestarteten nginx Container unter localhost:8080 auf.

### Schritt 5: Container über Docker Compose starten
Wir nutzen jetzt unser Docker Image und starten dieses über Docker Compose.

Legen Sie hierfür  Datei docker-compose.yml an, das einen nginx Instanz startet und dabei:

- den Service Namen "cc-nginx" hat
- das Image auf Basis des in Schritt 1 erstellen Dockerfiles baut
- den Port 80 exportiert und unter 8080 auf Ihrem Host erreichbar macht
     
<details>
<summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>

```
version: '3.8'
services:
  cc-nginx:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:80"
```
</details>

### Bonus/Optional:
Probieren Sie weitere Docker und Docker Compose Commands aus.
- Skalieren Sie den nginx auf 3 Instanzen
- Öffnen Sie eine Shell im laufenden Container
- Editieren sie die index.html im Container
- Schauen Sie in die nginx Logs
- ...


## Übung 2: Provisionierung mit Ansible
Ziel dieser Übung ist, praktische Erfahrungen mit Ansible zu sammeln und Docker und Docker Compose
noch besser kennenzulernen.

Um das lokale Setup möglichst einfach zu halten, werden wir sowohl die Ansible Control Node als auch 
die Maschinen, die wir provisionieren wollen, per Docker Compose lokal hochfahren.

Wir wollen:
- Die Ansible Control Node per Docker Compose starten
- 3 Managed Nodes per Docker Compose starten
- ein Playbook erstellen, dass auf allen 3 Managed Nodes einen Apache Http Server installiert, 
konfiguriert und startet
- das Playbook ausführen und die gestarteten Apache Http Server testweise aufrufen

### Hinweis:
Falls Sie nicht weiterkommen, können Sie sich an der Musterlösung orientieren. 

Nutzen Sie auch die folgenden Referenzen:
- Docker Compose Syntax: https://docs.docker.com/compose/compose-file/
- Ansible Documentation: https://docs.ansible.com/ansible/latest/index.html 

### Schritt 1: Image für Managed Nodes bauen
Erstellen Sie ein Dockerfile für die zu provisionierenden Maschinen / Managed Nodes. 
Diese sollen Ubuntu in Version 21.10 beinhalten und SSH Verbindungen von außen erlauben:
- Legen Sie dafür parallel zu dieser Readme eine Datei mit dem Namen "Dockerfile_Managed_Node" an.
- Schreiben sie ein Dockerfile, dass die folgenden Shell-Befehle ausführt um den SSH server zu installieren:
     ```
  apt-get update && apt-get install -y openssh-server
  mkdir /var/run/sshd
  echo 'root:verysecretpassword' | chpasswd
  sed -i 's/#*PermitRootLogin prohibit-password/PermitRootLogin yes/g' /etc/ssh/sshd_config
  sed -i 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' /etc/pam.d/sshd
  echo "export VISIBLE=now" >> /etc/prof
     ```
- Mit dem folgenden Befehl wir der ssh Server dann gestartet:
     ```
  /usr/sbin/sshd -D
     ```

#### Bonus/Optional: 
Erstellen Sie alternativ selbst ein Docker Image, das SSH Verbindungen von außen erlaubt:
- Wählen Sie ein Ubuntu Basisimage.
- Sorgen Sie dafür, dass eine SSH-Verbindung zum Container aufgebaut werden kann (per SSH Daemon), 
und hinterlegen Sie hierfür die Credentials Username="root" und Passwort="verysecretpassword".

### Schritt 2: Managed Node und Ansible Control Node über Docker Compose gemeinsam starten
Erstellen Sie ein Docker Compose File (Datei mit dem Namen "docker-compose.yml" parallel zu dieser Readme), das
- Eine Managed Node startet und dabei:
     - den Service Namen "managed-node" hat
     - das Image auf Basis des in Schritt 1 erstellen Dockerfiles baut
     - den Port 80 exportiert
     
     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>
     
     ```
  managed-node:
    build:
      context: .
      dockerfile: Dockerfile_Managed_Node
    ports:
      - "80"
     ```
     </details>
- Eine Ansible Control Node startet und dabei:
     - den Service Namen "ansible-node" hat
     - das fertige Image "willhallonline/ansible:2.9-alpine-3.13" nutzt. In diesem ist Ansible in Version 2.9 mit
     Python3 verfügbar. (siehe https://hub.docker.com/r/willhallonline/ansible)
     - ein Memory Limit von 100 MB hat
     - erst gestartet wird, wenn die Managed Node läuft
     
     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>
     
     ```
  ansible-node:
    image: "willhallonline/ansible:2.9-alpine-3.13"
    networks:
      - cloudcomputing
    depends_on:
      - managed-node
    ```
    </details>
- Geben Sie im Docker Compose File ein Netzwerk vom Typ "bridge" an und sorgen Sie dafür, dass die Managed
Node und die Ansible Control Node im gleichen Netzwerk laufen. Denken Sie daran, das im Docker Compose File
für jeden der Services mittels "networks" anzugeben.

### Schritt 3: Setup testen
Starten Sie die Managed Node über
```
docker-compose up --build -d
```
Starten Sie eine Shell auf der Ansible Control Node über
```
docker-compose run ansible-node /bin/sh
``` 

Stellen Sie eine SSH Verbindung zur Managed Node über folgendes Kommando her:
```
ssh managed-node
```
Geben Sie das Passwort 'verysecretpassword' ein, wenn Sie danach gefragt werden.
Sie können die SSH-Verbindung mit ```exit``` wieder beenden.

Sie können sowohl über den Service Namen 'managed-node' als auch mit vorangestelltem Präfix 'uebung' (der Name
des Ordners) und Suffix '1' eine SSH Verbindung herstellen. Verifizieren Sie das über 
```
ssh uebung_managed-node_1
```

### Schritt 4: Erste Schritte mit Ansible

Konfigurieren Sie die Maschinen, die Sie mit Ansible provisionieren wollen, über die Hosts Datei.
Legen Sie hierzu einen Ordner "ansible" und darin die Datei "hosts" an.

Legen Sie die Group "server_hosts" an, tragen Sie darin die Managed Node ein, und konfigurieren Sie Ansible:
- Geben Sie python3 als Python Interpreter an
- Hinterlegen Sie für die SSH-Verbindung Username und Passwort und fügen sie das ssh-Argument '-o StrictHostKeyChecking=no' hinzu

     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>

      ```
      [server_hosts]
      uebung_managed-node_1

      [server_hosts:vars]
      ansible_python_interpreter=/usr/bin/python3
      ansible_ssh_user=root
      ansible_ssh_pass=verysecretpassword
      ansible_ssh_common_args='-o StrictHostKeyChecking=no'
      ```

    </details>

Mounten Sie diese Datei über Docker Compose für den Service ansible-node unter /etc/ansible/hosts:

```
    volumes:
    [...]
    - "./ansible/hosts:/etc/ansible/hosts"
```

Starten Sie die Ansible Node neu über
```
docker-compose run ansible-node /bin/sh
``` 

Führen Sie darin folgende Kommandos aus
- Version ausgeben:  ```ansible --version```
- Alle Managed Nodes pingen: ```ansible all -m ping```
- Remote Command auf allen Managed Nodes ausführen: ```ansible all -m command -a "echo hello"```

### Schritt 6: Apache Http Server per Ansible Playbook installieren und konfigurieren

Legen Sie den Ordner 'playbooks' an und darin eine Datei mit dem Namen 'install-apache2.yml'.
Machen Sie sich über https://docs.ansible.com/ansible/latest/index.html mit der Ansible Syntax vertraut.

Tragen Sie die Hosts 'server_hosts' und den Remote User 'root' ein.

Mounten Sie das Playbook über Docker Compose in die Ansible Control Node unter '/root/playbooks'.

     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>

      ```
          volumes:
          [...]]
          - "./playbooks:/root/playbooks"
      ```

     </details>

Führen Sie im Playbook die folgenden Tasks aus:

- Testen Sie, ob Sie zur Managed Node eine Verbindung aufbauen können. Verwenden Sie dafür das Ansible Module 'ping'
 (https://docs.ansible.com/ansible/latest/modules/ping_module.html#stq=copy%20module&stp=1).
- Installieren Sie über das Ansible Module 'apt' apache2 in der Version 'latest' (https://docs.ansible.com/ansible/latest/modules/apt_module.html).
- Legen Sie eine eigene index.html an, die der Apache ausliefern soll, und kopieren Sie diese auf die Managed Node unter '/var/www/html/index.html'.
Verwenden Sie hierzu das Ansible Module 'copy' (https://docs.ansible.com/ansible/latest/modules/copy_module.html).
- Stellen Sie über das Ansible Module 'service' sicher, dass der Apache Http Server gestartet wurde (https://docs.ansible.com/ansible/latest/modules/service_module.html).

Führen Sie das Playbook auf der Ansible Control Node aus:

```
ansible-playbook /root/playbooks/install-apache.yml
```

### Schritt 6: Aufruf des gestarteten Webservers
Finden Sie über 

```
docker ps
```

heraus, unter welchem Port der gestartete Webserver auf Ihrem Host erreichbar ist. 
Welcher Port leitet Requests an den exponierten Port '80' des Containers weiter?

Rufen Sie in Ihrem Browser localhost:<port> für den spezifischen Port auf und verifizieren Sie, dass Ihre index.html
angezeigt wird.

### Schritt 7: Skalieren der Managed Nodes

Skalieren Sie die Managed Nodes auf 3. 
Nutzen Sie hierfür das docker-compose Kommando 'up' mit der Option '--scale' (siehe https://docs.docker.com/compose/reference/up/).

Ändern Sie die Datei 'hosts' so ab, dass alle 3 Managed Nodes provisioniert werden können und führen Sie die 
Provisionierung aus.

### Troubleshooting
Stellen Sie sicher, dass Sie über Docker den Zugriff auf die gemounteten Dateien erlauben.


## Übung 3: Packer (Optional)

### Schritt 1: Packer installieren
Installieren Sie Packer, indem Sie die Anleitung auf https://learn.hashicorp.com/tutorials/packer/getting-started-install
befolgen.

### Schritt 2: Packer kennenlernen
Machen Sie sich mit Packer vertraut, indem Sie sich die Beispiele auf https://learn.hashicorp.com/tutorials/packer/getting-started-build-image?in=packer/getting-started
anschauen.

Optional: bauen Sie eins der Beispiele lokal mit Packer.

### Schritt 3: Docker Image mit Packer bauen
Lesen Sie die Doku zum Bauen von Docker Images mit Packer: https://www.packer.io/docs/builders/docker

Legen Sie ein Packer Template an.
Verwenden Sie den Docker Builder von Packer, um ein Nginx Image mit.
einer eigenen Welcome Seite zu bauen.

Verwenden Sie als Basisimage "nginx:1.19-alpine".

Exponieren Sie Port 80 im Image und führen Sie das CMD "nginx -g daemon off;"
zum Start von Nginx aus.

Da wir ein Alpine Image verwenden, in dem per Default ```Bash```
nicht installiert ist, nutzen Sie das folgende ```run_command```
von Packer, damit später beim Container-Start ```/bin/sh``` anstelle von
```/bin/bash``` verwendet wird:
...
```"run_command": [ "-d", "-t", "-i", "{{.Image}}", "/bin/sh" ] ```

Nutzen Sie den File Provisioner von Packer, um eine lokal angelegte index.html
nach /usr/share/nginx/html/ im Image zu kopieren.

Nutzen Sie den Post Processor "docker-tag" von Packer, um dem Image den.
Namen "packer-nginx" und den Tag "1.0" zu geben.

Führen Sie
```packer build <Ihr Template>``` aus.

Prüfen Sie über ```docker images```, ob das Docker Image in Ihrer lokalen.
Registry verfügbar ist.

Starten Sie dann den Container mit ```docker```.
Mappen Sie dabei den Containerport 80 auf den Host Port 8080.

<details>
<summary>Hinweis, falls Sie nicht weiterkommen:</summary>

```
docker run -d -p 8080:80 packer-nginx:1.0
```
</details>

Rufen Sie im Browser ```localhost:8080``` auf und verifizieren Sie,
dass Ihre Welcome Seite angezeigt wird.

### Bonus/Optional 1:
Verwenden Sie anstelle des Nginx Images ein Alpine oder Centos Image.
Installieren Sie Nginx per shell Provisioner.

### Bonus/Optional 2: Ansible Provisionierung mit Packer ausführen

Nutzen Sie Ansible zur Provisionierung mit Packer..
Verwenden Sie dafür das Playbook aus Übung 2 und führen Sie dieses mit dem
Ansible Provisioner von Packer aus..

Nutzen Sie hierzu die Packer Doku und recherchieren Sie Beispiele.
