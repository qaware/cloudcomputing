# Übung: Setup

Ziel der heutigen Übung ist es, die benötigten Softwarekomponenten für die weiteren Übungen auf Ihren Rechnern zu installieren.

Falls Sie mit einer Linux-VM arbeiten möchten, gibt es die "DevBox"-VM, die auch in anderen Veranstaltungen der HM
verwendet wird: https://gitlab.lrz.de/hm/devbox/-/wikis/Releases


## Installationen

1. Git ([Installation](https://git-scm.com/downloads))
1. Ein Java 11 JDK (z.B. [Adoptium](https://adoptium.net/), ob HotSpot oder OpenJ9 spielt keine Rolle)
1. Eine Java IDE ihrer Wahl, z.B. [IntelliJ Community Edition](https://www.jetbrains.com/de-de/idea/download/)
1. Docker ([Windows](https://docs.docker.com/docker-for-windows/install/), [Mac](https://docs.docker.com/docker-for-mac/install/), [Linux](https://docs.docker.com/engine/install/))
1. Kubectl [Anleitung](https://kubernetes.io/docs/tasks/tools/)

## Test des Setups

1. Öffnen Sie eine Console in einem Ordner Ihrer Wahl und geben Sie `git clone https://github.com/JohannesEbke/cloudcomputing.git` ein
1. Öffnen Sie den Ordner [jdk-test-1](jdk-test-1/) in einer Console und geben sie `mvnw clean install` (`./mvnw clean install` unter Mac und Linux) ein
1. Öffnen Sie den Ordner [jdk-test-2](jdk-test-2/) in einer Console und geben sie `gradlew clean build` (`./gradlew clean build` unter Mac und Linux) ein
1. Importieren Sie die [jdk-test-1/pom.xml](jdk-test-1/pom.xml) in IntelliJ und starten Sie die `main`-Methode
1. Importieren Sie die [jdk-test-2/build.gradle](jdk-test-2/build.gradle) in IntelliJ und starten Sie die `main`-Methode
1. Testen Sie [ihre Docker-Installation](https://docs.docker.com/get-started/#test-docker-version)
1. Gehen Sie auf das [kube07-Portal](https://kube.cs.hm.edu) und legen Sie dort einen virtuellen Cluster an. Wählen Sie "Actions"/"Connect" und laden sich die `kubeconfig` herunter.
1. Sorgen Sie dafür dass kubectl die kubeconfig finden kann, und führen Sie zum Test `kubectl get pods --all-namespaces` aus.
