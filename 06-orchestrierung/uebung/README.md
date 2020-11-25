# Übung: Orchestrierungsmuster - Separation of Concerns mit modularen Containern

Ziel dieser Übung ist es, Orchestrierungsmuster für Container kennenzulernen.

Es gibt etablierte Patterns, wie sich bei der Ausführung von Containern
 eine Separation of Concerns erreichen lässt, zum Beispiel lässt sich standardisierbare
 Basis-Funktionalität aus dem eigentlichen Anwendungscontainer auslagern.
 
## Schritt 1: 
Recherchieren Sie das für Ihre Gruppe angegebene Orchestrierungsmuster.
Sie können dabei den [Kubernetes Blog](https://kubernetes.io/blog/2015/06/the-distributed-system-toolkit-patterns/)
als Quelle nutzen, müssen das aber nicht.

Kubernetes-Konzepte und Begrifflichkeiten können Sie für diese Übung ignorieren.
Falls diese für das Verständnis unbedingt erforderlich sind,
 fragen Sie bitte beim Betreuer der Übung nach.

* Gruppe 1: Sidecar Containers
* Gruppe 2: Ambassador Containers
* Gruppe 3: Adapter Containers

## Schritt 2: 
Bereiten Sie eine kleine Präsentation vor (1-2 Folien), in der Sie
* das Orchestrierungsmuster vorstellen
* Beispiele benennen, wann dieses Orchestrierungsmuster Sinn macht

Bestimmen Sie einen Vertreter in Ihrer Gruppe, der das Ergebnis im Anschluss vorstellt.

# Übung: Cluster-Orchestrierung mit Kubernetes

Ziel dieser Übung ist es, praktische Erfahrungen mit der Orchestrierung von Docker Containern zu machen. Als Cluster-Orchestrierer wird dabei Kubernetes verwendet.

## Vorbereitung

Installieren Sie [MiniKube](https://kubernetes.io/de/docs/setup/minikube/), [k9s](https://k9scli.io/topics/install/) und [kubectl](https://kubernetes.io/de/docs/tasks/tools/install-kubectl/) auf Ihrem Rechner.

Sie müssen k9s und kubectl nicht konfigurieren, diesen Schritt übernimmt Minikube für Sie.

## Aufgaben

### Aufgabe 1: Minikube starten

Erstellen Sie einen neuen Cluster mit `minikube start`. Sollte es zu Problemen mit der Virtualisierung kommen, versuchen Sie einen anderen [Driver](https://minikube.sigs.k8s.io/docs/drivers/). Um zu testen, ob ihr
Cluster läuft, können sie `kubectl --namespace kube-system get pods` verwenden.

Falls Sie Windows verwenden und beim Starten von MiniKube stecken bleiben, [hilft vielleicht dieses Video](https://www.youtube.com/watch?v=u5Rx05r49tU).

### Aufgabe 2: Service schreiben

Erstellen Sie mit dem Web-Framework ihrer Wahl einen Hello-World-Service, der über HTTP erreichbar ist. Erstellen Sie für diesen Service nun ein Docker-Container.

### Aufgabe 3: Deployment erstellen

Bauen Sie den Docker-Container gegen Minikube (`minikube docker-env`) und schreiben Sie ein Kubernetes Deployment für diesen Service. Installieren Sie dieses Deployment in den Kubernetes-Cluster und prüfen Sie mittels
`k9s`, ob der Service korrekt startet.

Sollte es zu einem `ImagePullErr` kommen, obwohl Sie ihr Docker gegen Minikube konfiguriert haben, versuchen Sie mal ein `imagePullPolicy: Never` unter `spec.containers`.

Unter Windows gibt `minikube docker-env` einen Befehl in der letzten Zeile aus, der mit `REM` anfängt. Dieses `REM` ist nicht Teil des Befehls,
den Sie eingeben müssen! Geben Sie alles hinter `REM` in die Konsole ein.

### Aufgabe 4: Probes & Resource constraints

Bauen Sie in Ihr Deployment noch Liveness- und Readiness-Probes ein. Vergeben Sie außerdem Resource requests und limits.

### Aufgabe 5: Services & Ingresses

Legen Sie für Ihr Deployment einen Service und einen Ingress an. Prüfen Sie, ob Sie den Service von außerhalb des Clusters erreichen können (`minikube ip`).

### Aufgabe 6: Konfiguration

Überlegen Sie sich einen Anwendungsfall für Ihren Service, der Konfiguration nötig macht (z.B. Austauschen der Begrüßung). Legen Sie nun eine ConfigMap an, mit der Sie den Service konfigurieren können.
Ändern Sie nun Ihren Service so ab, das dieser die Einstellungen aus der ConfigMap berücksichtigt.

### Aufgabe 7: Persistent Volumes

Mounten Sie ein Persistent Volume in den Container Ihrer Anwendung. Testen Sie, ob die Dateien in diesem Volume einen Container-Neustart überleben.

### Aufgabe 8: Helm

Installieren Sie per Helm ein Redis in Ihr Cluster, das mit einem Master und 3 Slaves läuft. Wenn Sie das geschafft haben, starten Sie einen Redis-Client, verbinden Sie diesem zu Ihrem Master-Redis und legen Sie ein
Key-Value-Pair an. Danach können Sie das Redis wieder aus Ihrem Cluster deinstallieren.

