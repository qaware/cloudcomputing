# Übung: Serverless Computing mit FaaS

In dieser Übung wollen wir verschiedene FaaS Frameworks kennenlernen.

## Kubeless

Diese Übung beschäftigt sich mit Kubeless (https://kubeless.io), einem Kubernetes
Native Serverless Framework.

1. Für die ersten Gehversuche, verwenden sie das Katacoda Tutorial von Kubeless:
https://www.katacoda.com/kubeless/

2. Installieren sie nun Kubeless lokal, entweder per Minikube oder mittels
Kubernetes von Docker. Folgen sie dabei der offiziellen Anleitung: https://kubeless.io/docs/quick-start/

3. Schreiben und deployen sie nun eine einfache Funktion in einer Sprache ihrer Wahl.
Für eine Beschreibung der verschiedenen Runtimes nutzen sie folgende Informationen:
https://github.com/kubeless/kubeless/blob/master/docs/runtimes.md

## OpenFaaS

Diese Übung beschäftigt sich mit OpenFaaS ( https://www.openfaas.com/ ), einer Serverlessplattform 
für Kubernetes oder OpenShift mit Fokus auf Einfachheit.

1. Installieren sie zunächst auf Ihrem Kubernetes OpenFaaS. Folgen sie hierzu den Anweisungen der
Dokumentation zum Deployment auf Kubernetes: https://docs.openfaas.com/deployment/kubernetes/
Sie können mit dem Abschnitt "Install the faas-cli" beginnen.

2. Folgen sie anschließend den Anweisungen der im "Lab 3" des OpenFaaS-Workshops:
https://github.com/openfaas/workshop/blob/master/lab3.md
Dort können sie eine einfache Funktion in in Python schreiben.

## Serverless Framework

Diese Übung beschäftigt sich mit dem Serverless Framework (https://www.serverless.com/open-source/),
einem CLI Tool zur einfachen und schnellen Entwicklung von von Event-getriebenen Funktionen.

Aufbauend auf den Übungen zu Kubeless führen sie die Übungen erneut mit Hilfe
des Serverless Frameworks aus.

### Kubeless

Eine Übersicht zum Kubeless Provider finden sie hier: https://serverless.com/framework/docs/providers/kubeless/
Folgen sie der Quickstart Anleitung, und wenden sie diese auf die in der Kubeless
Übung erstellte Funktion an.
