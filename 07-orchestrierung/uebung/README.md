# Übung: Cluster-Orchestrierung mit Kubernetes

Ziel dieser Übung ist es, den Microservice aus Übung 1 mit Kubernetes zu betrieben.

## Vorbereitung
* Eine lokale Minikube (https://kubernetes.io/docs/getting-started-guides/minikube/) Installation.
* Lokale Installation von `kubectl` (https://kubernetes.io/docs/tasks/tools/install-kubectl/)
* Den lauffähigen Microservice aus [Übung 1](../../01-kommunikation/uebung/)

## Aufgaben

### 1) Lokale Lauffähigkeit

Starten sie Minikube lokal und prüfen sie anschließend die lokale Lauffähigkeit. Starten sie
das Kubernetes Dashboard und verschaffen sie sich einen Überblick. **Achtung:** je nach Betriebssystem
und Virtualisierungssoftware sehen die Start-Parameter leicht anders aus, speziell für Hyper-V.

```bash
$ minikube start --help
$ minikube start --memory 2048 --vm-driver [virtualbox, hyperv] --hyperv-virtual-switch External-LAN
$ minikube dashboard
```

Machen sie sich mit der Kubernetes CLI `kubectl` vertraut, dies ist hier Hauptwerkzeug um
mit dem Kubernetes Cluster zu interagieren. Erforschen sie den Cluster und

```bash
$ kubectl cluster-info

$ kubectl get nodes
$ kubectl describe node minikube

$ kubectl get pods --namespace kube-system
$ kubectl describe pod kubernetes-dashboard-pkvsc --namespace kube-system
```

Starten sie nun mit `kubectl` einen NGINX Server mit 3 Replicas und überwachen sie
den Start der Pods.

```bash
$ kubectl run nginx --image=nginx:1.7.9 --replicas=3
$ kubectl get pods -w
```

Exponieren sie den NGINX Server mittels `kubectl expose` auf Port 80 und mit Type `NodePort`
und rufen sie den Service mit `minikube service` auf.

```bash
$ kubectl expose deployment nginx --name=nginx --port=80 --protocol=TCP --target-port=80 --type=NodePort
$ minikube service nginx
```

### 2) Containerization

In diesem Schritt müssen wir den Microservice nun Containerisieren und anschließend in eine Docker
Registry laden. Falls noch nicht erledigt:

* legen sie sich auf https://hub.docker.com einen Account an
* schreiben sie für den Microservice aus Übung 1 ein `Dockerfile`
* bauen sie das Docker Image und prüfen sie die lokale Lauffähigkeit
* taggen und pushen sie das funktionsfähige Image nach Docker Hub

**Alternative:** Im Fall einer schlechten Netzverbindung kann auch mit einer lokalen Registry
gearbeitet werden und das Image direkt in der Minikube Docker Registry gebaut werden.

```bash
$ minikube docker-env

$ # type echoed command here
$ # build Docker image
```

### 3) Deployment in Kubernetes

In diesem Schritt werden wir den Microservice aus Übung 1 nun in Kubernetes deployen.

#### 3a) Deployment Descriptor

In diesem Schritt erstellen wir ein Deployment Descriptor für den Microservice. Schreiben
sie eine Datei im YAML Format, referenzieren sie hierbei das vorher erstellte Docker Image
in der Pod Template Definition.

```yaml
apiVersion: apps/v1beta2 # for versions before 1.8.0 use apps/v1beta1
kind: Deployment
metadata:
  name: book-application
spec:
  selector:
    matchLabels:
      app: BookServiceApplication
  replicas: 1 # tells deployment to run 1 pods matching the template
  template: # create pods using pod definition in this template
    metadata:
      labels:
        app: BookServiceApplication
    spec:
      containers:
      - name: book-service
        image: book-service:1.0.1
        ports:
        - containerPort: 8080
```

Starten sie das Deployment mittels `kubectl`. Prüfen sie den Status des Deployments und
der Pods. Sobald alles Ready ist, skalieren sie das Deployment auf 3 Replicas.

```bash
$ kubectl apply -f book-application.yaml
$ kubectl get pods,deployments
$ kubectl get pods -w
$ kubectl scale deployment book-application --replicas=3
$ kubectl get pods -w
```

Erweitern und Aktualisieren sie nun das Deployment. Definieren sie Liveness und Readiness
Probes sowie die Anforderungen und Limits an CPU und Memory Ressourcen. Als Endpoints
für die Probes können sie die Actuator Endpoints verwenden.

Weitere Infos:
* https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/
* https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-probes/#define-a-liveness-http-request
* https://kubernetes.io/docs/tasks/configure-pod-container/assign-memory-resource/
* https://kubernetes.io/docs/tasks/configure-pod-container/assign-cpu-resource/

#### 3b) Service Descriptor

Erstellen sie nun einen Service Descriptor für unseren Microservice. Schreiben sie
eine Datei im YAML Format, referenzieren sie die im Deployment verwendeten Labels.
Verwenden sie den Wert `NodePort` als Typ. Deployen sie den Service mittels `kubectl`.

```yaml
kind: Service
apiVersion: v1
metadata:
  name: book-service
spec:
  selector:
    app: BookServiceApplication
  ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
  type: NodePort
```

Weitere Infos:
* https://kubernetes.io/docs/concepts/services-networking/service/
* https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/
* https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/

#### 3c) ConfigMap Descriptor

In diesem Schritt erstellen wir einen ConfigMap Descriptor für die Konfigurations-Werte
unseres Microservice. Die Konfigurations-Werte werden dem Service dann über Umgebungs-Variablen
zu Verfügung gestellt.

Schreiben sie einen ConfigMap Descriptor im YAML Format mit den folgenden Werten:
```
info.app.name=Book Service
info.course.name=Cloud Computing
info.course.location=FH Rosenheim
```

Legen sie die ConfigMap mit der Kubernetes CLI an, alternativ über das Dashboard. Verändern sie
nun den Deployment Descriptor des Microservice und referenzieren sie die ConfigMap Einträge
als Umgebungsvariablen. Aktualisieren sie das Deployment und prüfen sie den `/admin/info` Endpoint.

Weitere Infos:
* https://kubernetes.io/docs/tasks/configure-pod-container/configmap/
* https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/

### Bonus Aufgaben

#### Rolling Upgrades

Erstellen sie ein weiteres Tag für ihr Docker Image und pushen sie dieses in die Registry.
Führen sie dann ein Rolling-Update für ihr Deployment auf die neue Version aus indem sie
die Image Versions des Containers im Pod neu setzen.

#### Ingress Descriptor

Für den externen Zugriff auf den Service und das Deployment erstellen sie einen Ingress Controller
der auf den Service zeigt. Siehe https://kubernetes.io/docs/concepts/services-networking/ingress/
