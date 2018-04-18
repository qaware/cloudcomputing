# Übung: Cluster Scheduling mit Docher Swarm und Azure Container Instances

Ziel dieser Übung ist es, praktische Erfahrungen mit dem Scheduling von Docker Containers auf einem Cluster zu machen. Als Cluster Scheduler wird dabei Docker Swarm (Scheduling auf beliebigen Rechnern) und Azure Container Instances (Scheduling auf Knoten in der Microsoft Azure Cloud) verwendet. Die Übung basiert dabei auf Tutorials, die auf [Katacoda](https://www.katacoda.com/) verfügbar sind.

## Vorbereitung

1. Bitte richten sie einen [Katacoda](https://www.katacoda.com/)-Account für sich ein

## Aufgaben
Arbeiten Sie die folgenden Tutorials auf Katacoda durch. Sie vermitteln die Nutzersicht auf Cluster Scheduler. Sie werden dabei sehen, dass sie dabei keine Informationen benötigen, auf welchen konkreten Maschinen die Container ausgeführt werden. 
Hinweis: Im Browser steht Ihnen dabei eine umfassende Umgebung zur Verfügung. Nutzen Sie dies auch, um mit den Cluster Schedulern zu experimentieren.

### Docker Swarm
1. [Mit Docker Swarm warm werden](https://www.katacoda.com/courses/docker/getting-started-with-swarm-mode)

### Microsoft Azure Container Instances
1. [Container auf der Azure Cloud deployen](https://www.katacoda.com/courses/cloud/deploying-container-instances)

### Installation von Docker
Für die kommenden Übungen werden sie eine lokale Docker-Installation auf ihrem Rechner benötigen. Bitte installieren sie aus diesem Grund bereits in dieser Übung lokal Docker. Bitte beachten sie dabei, dass die Virtualisierungs-Extension in ihrem BIOS aktiviert ist. Installieren sie den Edge Channel von Docker CE. Folgend die Installationsbeschreibungen für die einzelnen Betriebssysteme:
 * [Windows](https://docs.docker.com/docker-for-windows/install)
 * [macOS](https://docs.docker.com/docker-for-mac/install)
 * [Linux Derivate](https://docs.docker.com/install/linux/docker-ce/ubuntu)
Aktivieren sie in den Einstellungen von Docker im Anschluss die [Unterstützung von Kubernetes](https://blog.docker.com/2018/01/docker-windows-desktop-now-kubernetes).