# Übung: PaaS mit OpenShift und PCF

Ziel dieser Übung ist es erste praktische Erfahrungen im Umgang mit PaaS Platformen
wie OpenShift oder Pivotal Cloud Foundry zu sammeln.

## Vorbereitung

* Eine lokale Minishift Installation. Siehe [minishift.adoc](minishift.adoc) und [https://github.com/minishift/minishift](https://github.com/minishift/minishift)

## Aufgaben

### Spring Boot Development mit OpenShift (Beginner)

In dieser Übung nutzen wir das OpenShift [Interactive Learning Portal](https://learn.openshift.com/middleware/). Folgen sie den Anweisungen für das Tutorial **[Spring Boot development](https://learn.openshift.com/middleware/rhoar-getting-started-spring/)**.

## WildFly Swarm Development mit OpenShift (Advanced)

In dieser Übung nutzen wir das OpenShift [Interactive Learning Portal](https://learn.openshift.com/middleware/). Folgen sie den Anweisungen für das Tutorial **[WildFly Swarm development](https://learn.openshift.com/middleware/rhoar-getting-started-wfswarm/)**.

## Lokale Entwicklung mit Minishift

Führen sie zunächst den [Minishift Quickstart](https://docs.openshift.org/latest/minishift/getting-started/quickstart.html) durch und studieren sie den Quellcode der Beispiel Anwendung.

Anschließend bringen sie unsere Beispiel-Anwendung aus [Übung 1](../../01-kommunikation/uebung/) bzw. [Übung 7](../../07-orchestrierung/uebung/)  in Minishift zum laufen. Schreiben sie hierfür die nötigen Deployment Deskriptoren bzw. generieren sie diese aus einer `docker-compose.yml` mit dem Tool **kompose**.

## Bonusaufgaben

### Hands-On Pivotal CloudFoundry und PCF Dev

In dieser Bonus Aufgabe können sie sich mit Pivotal CloudFoundry vertraut machen. Führen sie hierzu die Schritte des [PCF Tutorials](
https://pivotal.io/platform/pcf-tutorials/getting-started-with-pivotal-cloud-foundry-dev/introduction) unter https://pivotal.io/platform/pcf-tutorials/getting-started-with-pivotal-cloud-foundry-dev/introduction aus und studieren sie den Source Code der Beispiel-Anwendung.
