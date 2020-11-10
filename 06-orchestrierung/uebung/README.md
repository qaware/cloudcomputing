# Übung: Cluster-Orchestrierung mit Kubernetes

Ziel dieser Übung ist es, praktische Erfahrungen mit der Orchestrierung von Docker Containern zu machen. Als Cluster-Orchestrierer wird dabei Kubernetes verwendet.

## Vorbereitung

Installieren Sie [MiniKube](https://kubernetes.io/de/docs/setup/minikube/), [k9s](https://k9scli.io/topics/install/) und [kubectl](https://kubernetes.io/de/docs/tasks/tools/install-kubectl/) auf Ihrem Rechner.

Sie müssen k9s und kubectl nicht konfigurieren, diesen Schritt übernimmt Minikube für Sie.

## Aufgaben

### Aufgabe 1: Minikube starten

Erstellen Sie einen neuen Cluster mit `minikube start`. Sollte es zu Problemen mit der Virtualisierung kommen, versuchen Sie einen anderen [Driver](https://minikube.sigs.k8s.io/docs/drivers/). Um zu testen, ob ihr
Cluster läuft, können sie `kubectl --namespace kube-system get pods` verwenden.

### Aufgabe 2: Service schreiben

Erstellen Sie mit dem Web-Framework ihrer Wahl einen Hello-World-Service, der über HTTP erreichbar ist. Erstellen Sie für diesen Service nun ein Docker-Container.

### Aufgabe 3: Deployment erstellen

Bauen Sie den Docker-Container gegen Minikube (`minikube docker-env`) und schreiben Sie ein Kubernetes Deployment für diesen Service. Installieren Sie dieses Deployment in den Kubernetes-Cluster und prüfen Sie mittels
`k9s`, ob der Service korrekt startet.

### Aufgabe 4: Probes & Resource constraints

Bauen Sie in Ihr Deployment noch Liveness- und Readiness-Probes ein. Vergeben Sie außerdem Resource requests und limits.

### Aufgabe 5: Services & Ingresses

Legen Sie für Ihr Deployment einen Service und einen Ingress an. Prüfen Sie, ob Sie den Service von außerhalb des Clusters erreichen können (`minikube ip`).

### Aufgabe 6: Konfiguration

Überlegen Sie sich einen Anwendungsfall für Ihren Service, der Konfiguration nötig macht (z.B. Austauschen der Begrüßung). Legen Sie nun eine ConfigMap an, mit der Sie den Service konfigurieren können.
Ändern Sie nun Ihren Service so ab, das dieser die Einstellungen aus der ConfigMap berücksichtigt.

### Aufgabe 7: Persistent Volumes

Mounten Sie ein Persistent Volume in den Container Ihrer Anwendung. Testen Sie, ob die Dateien in diesem Volume einen Container-Neustart überleben.

