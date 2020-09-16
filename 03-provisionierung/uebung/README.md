# Übung: Provisionierung mit Ansible und Docker Compose

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
Diese sollen Ubuntu in Version 20.10 beinhalten und SSH Verbindungen von außen erlauben:
- Legen Sie dafür parallel zu dieser Readme eine Datei mit dem Namen "Dockerfile_Managed_Node" an.
- Übernehmen Sie die Inhalte des hier angegebenen Dockerfiles: 
https://docs.docker.com/engine/examples/running_ssh_service/
- Ersetzen Sie den Platzhalter 'THEPASSWORDYOUCREATED' durch ein geeignetes Passwort.
Wir wählen für die Übung das Passwort 'verysecretpassword'.

#### Bonus/Optional: 
Erstellen Sie alternativ selbst ein Docker Image, das SSH Verbindungen von außen erlaubt:
- Wählen Sie ein Ubuntu Basisimage.
- Sorgen Sie dafür, dass eine SSH-Verbindung zum Container aufgebaut werden kann (per SSH Deamon), 
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
     - das fertige Image "willhallonline/ansible:2.9-alpine" nutzt. In diesem ist Ansible in Version 2.9 mit
     Python3 verfügbar.
     - ein Memory Limit von 100 MB hat
     - erst gestartet wird, wenn die Managed Node läuft
     
     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>
     
     ```
     ansible-node:
         image: "willhallonline/ansible:2.9-alpine"
         networks:
           - cloudcomputing
         deploy:
           resources:
             limits:
               memory: 100m
         depends_on:
           - managed-node
    ```
    </details>
- Geben Sie im Docker Compose File ein Netzwerk vom Typ "bridge" an und sorgen Sie dafür, dass die Managed
Node und die Ansible Control Node im gleichen Netzwerk laufen. Denken Sie daran, das im Docker Compose File
für jeden der Services mittels "networks" anzugeben.

### Schritt 3: Strict Host Key Checking deaktivieren
Deaktivieren Sie Strict Host Key Checking beim ersten Verbindungsaufbau zwischen der Control Node und
der Managed Node, indem Sie 
- einen Ordner "ssh" anlegen
- darin eine Datei mit dem Namen "config" und folgende Inhalt ablegen:
  ```
   Host *
      StrictHostKeyChecking no
  ```
- diese Datei über Docker Compose für die Ansible Control Node unter /root/.ssh/config mounten.

     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>
     
  ```
     volumes:
     - "./ssh/config:/root/.ssh/config"
  ```   
  </details>

### Schritt 4: Setup testen
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

### Schritt 5: Erste Schritte mit Ansible

Konfigurieren Sie die Maschinen, die Sie mit Ansible provisionieren wollen, über die Hosts Datei.
Legen Sie hierzu einen Ordner "ansible" und darin die Datei "hosts" an.

Legen Sie die Group "server_hosts" an, tragen Sie darin die Managed Node ein, und konfigurieren Sie Ansible:
- Geben Sie python3 als Python Interpreter an
- Hinterlegen Sie Username und Passwort für die SSH Verbindung

     <details>
     <summary>Wenn Sie nicht weiterkommen, können Sie folgenden Codeblock verwenden:</summary>

```
   [server_hosts]
   uebung_managed-node_1

   [server_hosts:vars]
   ansible_python_interpreter=/usr/bin/python3
   ansible_ssh_user=root
   ansible_ssh_pass=verysecretpassword
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

### Schritt 7: Aufruf des gestarteten Webservers
Finden Sie über 

```
docker ps
```

heraus, unter welchem Port der gestartete Webserver auf Ihrem Host erreichbar ist. 
Welcher Port leitet Requests an den exponierten Port '80' des Containers weiter?

Rufen Sie in Ihrem Browser localhost:<port> für den spezifischen Port auf und verifizieren Sie, dass Ihre index.html
angezeigt wird.

### Schritt 8: Skalieren der Managed Nodes

Skalieren Sie die Managed Nodes auf 3. 
Nutzen Sie hierfür 'docker-compose scale'.

Ändern Sie die Datei 'hosts' so ab, dass alle 3 Managed Nodes provisioniert werden können und führen Sie die 
Provisionierung aus.

### Troubleshooting
Stellen Sie sicher, dass Sie über Docker den Zugriff auf die gemounteten Dateien erlauben.


