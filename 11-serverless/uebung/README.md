# Übung: Serverless Computing mit FaaS

In dieser Übung wollen wir FaaS auf AWS sowie verschiedene FaaS Frameworks auf Kubernetes kennenlernen.

## FaaS auf AWS: AWS Lambda mit Terraform

Um AWS Lambda aus dem Web ohne Zugriff auf die AWS API aufzurufen, ist es nötig, den API Gateway 
zu nutzen. Folgen Sie dazu dem folgenden Tutorial:

https://learn.hashicorp.com/tutorials/terraform/lambda-api-gateway

Hinweis: Dieses Tutorial zeigt auch die typische Organisation von Konfiguration, Variablen und Ressourcen in 
Terraform-Modulen - dies kann für die Arbeit zur ZV nützlich sein.

Zusatzaufgabe: Wechseln Sie die eingesetzte Sprache, z.B. auf Python.

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

Schreiben und deployen sie eine einfache Funktion in einer Sprache ihrer Wahl.

Zusatzaufgabe: Deployen Sie die gleiche Funktion auf Minikube mit z.B. Knative.
