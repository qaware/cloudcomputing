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

## Fn Project

Diese Übung beschäftigt sich mit Project Fn (http://fnproject.io), einer Container nativen
aber Cloud agnostischen Serverless platform.

1. Installieren sie zunächst lokal Project Fn. Folgen sie hierzu den Anweisungen der
Quickstart Dokumentation: https://github.com/fnproject/fn#top

2. Folgen sie anschließend den Anweisungen der Project Fn Tutorials. Schreiben und deployen
sie eine einfache Funktion in Java. https://fnproject.io/tutorials/JavaFDKIntroduction/

- http://fnproject.io/tutorials/
- https://github.com/fnproject/fn-helm
- https://medium.com/fnproject/fn-project-helm-chart-for-kubernetes-e97ded6f4f0c

## Serverless Framework

Diese Übung beschäftigt sich mit dem Serverless Framework (https://serverless.com/framework/),
einem CLI Tool zur einfachen und schnellen Entwicklung von von Event-getriebenen Funktionen.

Aufbauend auf den Übungen zu Kubeless und Project Fn, führen sie die Übungen erneut mit Hilfe
des Serverless Frameworks aus.

### Kubeless

Eine Übersicht zum Kubeless Provider finden sie hier: https://serverless.com/framework/docs/providers/kubeless/
Folgen sie der Quickstart Anleitung, und wenden sie diese auf die in der Kubeless
Übung erstellte Funktion an.

### Project Fn

Eine Übersicht zum Project Fn Provider finden sie hier: https://serverless.com/framework/docs/providers/fn/
Folgen sie der Quickstart Anleitung, und wenden sie diese auf die in der Project Fn
Übung erstellte Funktion an.
