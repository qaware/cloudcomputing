# Übung: Infrastructure as Code mit Terraform auf AWS

Ziel dieser Übung ist das erlernen grundlegender Infrastructure as Code Fähigkeiten mit Terraform auf der AWS Cloud. Hierzu werden Sie die Architektur aus der letzten Übung mit Terrform nachbauen. Grundlegende Schritte sind hierfür schon vorbereitet.


0. Starten Sie den `iaas-container` und mounten Sie das Verzeichnis `teil-2` in den `/root/teil-2` im Container. Beispiel mit Bash aus dem Verzeichnis:
   ```
   docker run -it --rm -w /root --mount type=bind,source="$(pwd)",target=/root/teil-2 iaas-container
   ``` 
   Konfigurieren Sie wieder Ihren AWS Zungang mit `aws configure`.
   Initialisieren Sie dann im Verzeichnis `teil-2` Terraform mit `terraform init`.
4. Schauen Sie sich die bereits existierenden Terraform Dateien an und machen Sie sich mit der grundlegenden Struktur vertraut.
5. Implementieren Sie alle Stellen die mit `#TODO` annotiert sind. Definieren Sie sich selbst eine sinnvolle reihenfolge. In regelmäßigen Abständen sollten Sie ihre Implementierungsarbeiten auf die AWS Cloud anwenden mit `terraform apply`.
6. Am Ende sollte die Terraform Konfiguration einen Output Parameter mit einer validen und funktionierenden URL enthalten.
7. Erzeugen Sie einen neuen Workspace mit `terraform workspace new dev`, wechseln zu diesem `terraform workspace select dev` und überprüfen Sie ob Sie mit `terraform apply` eine zweite Umgebung erzeugen können. Wenn nicht passen Sie ihre Konfigurationen so an, dass dies möglich ist. Machen Sie dafür insbesondere Benennungen von Ressourcen abhängig vom verwendeten Workspace.
8. Zerstören Sie alle erzeugten Ressourcen mit `terraform destroy` auf beiden Workspaces.

