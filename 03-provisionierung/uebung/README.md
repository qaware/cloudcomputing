# Übung: Provisionierung mit Docker Compose, Ansible und Terraform

Ziel dieser Übung ist es, praktische Erfahrungen mit den gängigen Technologien zur Provisionierung von Cloud Infrastrukturen nach dem Paradigma "Infrastructure as Code" zu sammeln. Die Übung basiert dabei auf Tutorials, die auf [Katacoda](https://www.katacoda.com/) verfügbar sind.

## Vorbereitung

1. Bitte richten sie einen [Katacoda](https://www.katacoda.com/)-Account für sich ein

## Aufgaben
Arbeiten Sie die folgenden Tutorials auf Katacoda durch. Sie vermitteln typische Arbeitsabläufe mit den Provisionierungs-Werkzeugen. Hinweis: Im Browser steht Ihnen dabei eine umfassende Umgebung zur Verfügung. Nutzen Sie dies auch, um mit den Funktionen der Werkzeuge zu experimentieren.

### Docker Compose
1. [Provisionierung multipler Container mit Docker Compose](https://www.katacoda.com/courses/docker/11)

### Ansible
1. [Mit Ansible warm werden](https://www.katacoda.com/jonatanblue/scenarios/1)
2. [Bootstrapping mit Ansible](https://www.katacoda.com/oliverveits/scenarios/ansible-bootstrap)

### Terraform
1. [Depolyment eines NGINX Containers mit Terraform](https://www.katacoda.com/courses/terraform/deploy-nginx)

# Bonus-Übung: Provisionierung mit Vagrant und Ansible

## Vorbereitung
* Prüfen sie, ob VirtualBox / Hyper-V und Vagrant installiert sind. Falls nicht: installieren.

## Aufgaben

### Hello Ansible (manuell)

In dieser Aufgabe wollen wir zunächst erste Gehversuche mit Vagrant und Ansible machen. 

1. Erzeugen sie sich hierfür mittels Vagrant eine neue VM:
```
vagrant init ubuntu/trusty64
vagrant up --provider=virtualbox
```
Mittels `vagrant ssh` kommen Sie auf die Shell der VM.

2. und installieren sie entweder manuell oder per Shell-Provisioning in der VM das Ansible Paket.

Legen sie anschließend ein neues Ansible Playbook YAML File an und führen sie es lokal aus. Erweitern sie das Hello World
Beispiel nach belieben, z.B. kopieren sie eine Datei vom Host-System in das `hello-world/` Verzeichnis mit dem `copy`
Command Modul.

```yaml
# To run this, name this file hello_world.yml and run the following in the same directory
# ansible-playbook hello_world.yml -i 'local,' --connection=local

- hosts:
  - local
  tasks:
  - name: Create a directory
    file: path=hello-world state=directory
```

### Hello Ansible Local Provisioner

In dieser Aufgabe soll nun der Ansible Local Provisioner (https://www.vagrantup.com/docs/provisioning/ansible_local.html)
verwendet werden. Ändern sie das Vagrantfile entsprechend, bzw. legen sie einfach ein neues an. Führen sie das Playbook aus
der ersten Aufgabe aus.

### Bonus-Aufgabe: Multi-Host Provisionierung

Führen sie eine komplexere Provisionierung von mehreren Hosts durch. Erzeugen sie hierfür mittels Vagrant einfach mehrfache
VMs. Die Provisionierung wird auf der `controller` VM ausgeführt. Installieren sie mittels Ansible, ähnlich zur Übung 03, einen NGINX, PHP und mySQL Stack auf den anderen beiden VMs.

```yaml
Vagrant.configure("2") do |config|

  config.vm.box = "ubuntu/trusty64"

  config.vm.define "webserver" do |machine|
    machine.vm.network "private_network", ip: "172.17.177.21"
  end

  config.vm.define "database" do |machine|
    machine.vm.network "private_network", ip: "172.17.177.22"
  end

  config.vm.define 'controller' do |machine|
    machine.vm.network "private_network", ip: "172.17.177.11"

    machine.vm.provision :ansible_local do |ansible|
      ansible.playbook       = "example.yml"
      ansible.verbose        = true
      ansible.install        = true
      ansible.limit          = "all" # or only "nodes" group, etc.
      ansible.inventory_path = "inventory"
    end
  end

end
```

## Referenzen

* https://www.ansible.com/quick-start-video
* https://www.vagrantup.com/docs/provisioning/ansible_intro.html
* https://www.vagrantup.com/docs/provisioning/ansible_local.html
* https://docs.ansible.com/ansible/latest/modules_by_category.html
* https://docs.ansible.com/ansible/latest/playbooks.html
