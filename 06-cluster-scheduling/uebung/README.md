# Übung: Cluster Scheduling mit Amazon EC2 Container Service (ECS)

## Vorbereitung

* Legen sie sich auf Docker Hub einen privaten Account an.
* Installieren sie die AWS CLI auf ihrem System. https://docs.aws.amazon.com/cli/latest/userguide/installing.html
* Sie erhalten die AWS Zugriffsdaten beim Übungsbetreuer.
* Konfigurieren sie die AWS CLI: `aws configure`

## Ziel

Wir wollen im Rahmen dieser Übung eine Beispiel Anwendung auf Amazon EC2 Container Service (ECS) bereitstellen.
Wir verwenden dafür die Anwendung aus der Vorlesung und Übung __"Kommunikation"__.


## Aufgaben

### Dockerfile erzeugen

Schreiben sie für den Microservice aus der Übung __"Kommunikation"__ ein `Dockerfile`. Verwenden sie als Basis-Image
ein aktuelles JDK8 oder JRE8. Achtung Größe! (siehe https://hub.docker.com/_/openjdk/)

### Docker Image lokal bauen

Bauen und testen sie das Image lokal. Verwenden sie hierfür die Kommandos aus der Übung __"Virtualisierung"__.

```bash
$ docker build -t book-service:1.0.1 .
$ docker run -it -p 8080:8080 book-service:1.0.1
```

### Getting Started with Amazon EC2 Container Service (ECS)

1. Melden sie sich mit den AWS Zugriffsdaten über den Browser an der AWS Web Console an.
2. Erstellen sie einen Amazon EC2 Container Service (ECS) und folgen den Anweisungen.

#### Step 1: Configure repository

Erstellen sie ein Repository für ihren Service. Am besten bauen sie den AWS Account-Namen ein, z.B: `cc2017/cc2017-00`.
* Repository Name: `cc2017/cc2017-00`
* Repository URI: 450802564356.dkr.ecr.eu-central-1.amazonaws.com/cc2017/cc2017-00

#### Build, tag, and push Docker image

```bash
aws ecr get-login --no-include-email --region eu-central-1

# kopieren sie den Output und führen diesen in der Shell aus

docker build -t cc2017/cc2017-00 .
docker tag cc2017/cc2017-00:latest 450802564356.dkr.ecr.eu-central-1.amazonaws.com/cc2017/cc2017-00:latest

# oder

docker tag book-service:1.0.1 450802564356.dkr.ecr.eu-central-1.amazonaws.com/cc2017/cc2017-00:latest

docker push 450802564356.dkr.ecr.eu-central-1.amazonaws.com/cc2017/cc2017-00:latest
```

#### Create a task definition

Nachdem das Image hochgeladen wurde, gehen sie nun zum nächsten Schritt und erzeugen die Task-Definition.
Vergeben sie sprechende Namen für `Task definition name` und `Container name`. Als `Memory Limits (MiB)*` setzen sie `500`.
Bei den Port `Port mappings` setzen sie den Container-Port `8080`.

#### Configure service

Konfigurieren sie einen Service für die Task Definition. Setzen sie einen `Service name` und starten sie `2` Tasks.
Konfigurieren sie ebenfalls einen Load-Balancer für den Service.

#### Configure cluster

Konfigurieren sie den EC2 Cluster. Vergeben sie einen eindeutigen `Cluster name*`. Verwenden sie `t2.micro` als `EC2 instance type*` und `2` `Number of instances*`.

#### Review

Prüfen sie alle Angaben und starten sie den Cluster.
