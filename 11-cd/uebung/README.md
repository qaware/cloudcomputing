# Übung CI / CD

## Übung 1: Jenkins und Sonarqube

Machen Sie sich mit den Tools _Jenkins_ und _Sonarqube_ vertraut.

Jenkins ist ein Build Server, mit dem Sie Ihre Pipelines konfigurieren und ausführem können. 

Sonarqube ist ein Tool, um statische Code-Analysen durchzuführen, das Sie innerhalb Ihrer Pipelines
verwenden können.

### Variante 1: Lokal mit Docker Compose

#### Sonarqube
Folgen Sie der Anleitung unter https://docs.sonarqube.org/latest/setup/get-started-2-minutes/
um lokal Sonarqube als Docker Container auszuführen.

Im Wesentlichen müssen Sie folgendes Command ausführen:
```
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Rufen Sie dann wie unter https://docs.sonarqube.org/latest/setup/get-started-2-minutes/ beschrieben localhost:9000 auf
und loggen Sie sich ein.

Legen Sie dann über die UI ein neues Projekt an und führen Sie eine Codeanalyse durch
(wie im Abschnitt _Analyzing a Project_ beschrieben).
Sie können hierzu eines Ihrer privaten Projekte nutzen, oder z.B. Ihre Lösung zur Übungsaufgabe zum Kapitel _01-kommunikation_.

Schauen Sie sich das Ergebnis der Analyse über die Sonarqube UI an.

#### Jenkins
Folgen Sie den Anleitungen auf https://www.jenkins.io/solutions/docker/, um Jenkins lokal als Docker Container auszuführen
und dort Build Pipelines einzurichten.

Interessant sind vor allem die Schritte
* [Jenkins+Docker Tutorial](https://medium.com/@gustavo.guss/quick-tutorial-of-jenkins-b99d5f5889f2) um Jenkins lokal zu starten
* im Anschluss [Build Docker Images with Jenkins](https://medium.com/@karthi.net/docker-tutorial-build-docker-images-using-jenkins-d2880e65b74)


### Variante 2: Katacoda Online Tutorials

Lernen Sie Jenkins und Sonarqube kennen, indem Sie die folgenden Katacoda Online Tutorials durcharbeiten:

* Building Docker Images Using Jenkins:
https://www.katacoda.com/courses/cicd/build-docker-images-using-jenkins
* Sonarqube on Kubernetes: 
https://www.katacoda.com/courses/cicd/sonarqube

## Übung 2: DevOps Topologies

Unter https://web.devopstopologies.com/ finden Sie einige Patterns und Anti-Patterns, wie DevOps umgesetzt werden kann.

Erarbeiten Sie in Ihrer Gruppe die genannten Patterns und Anti-Patterns.

* Gruppe 1: Anti-Type A: Dev and Ops Silos, Anti-Type B: DevOps Team Silo, Type 1: Dev and Ops Collaboration, 
Type 2: Fully Shared Ops Responsibilities, Type 8: Container-Driven Collaboration
* Gruppe 2: Anti-Type C: Dev Don't Need Ops, Anti-Type D: DevOps as Tools Team, 
Type 3: Ops as Infrastructure-as-a-Service (Platform), Type 4: DevOps as an External Service
* Gruppe 3: Anti-Type E: Rebranded SysAdmin, Anti-Type F: Ops Embedded in Dev Team, 
Type 5: DevOps Team with an Expiry Date, Type 6: DevOps Advocacy Team, Type 7: SRE Team (Google Model), 

Bereiten Sie eine kleine Präsentation vor (~1 Folie je Pattern bzw. Anti-Pattern), die das jeweilige Pattern erklärt.
Finden Sie in Ihrer Gruppe einen Vertreter, der die Patterns und Anti-Patterns im Anschluss vorstellt.