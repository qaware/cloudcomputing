# Übung: Infrastructure Provisionierung mit Terraform

## Vorbereitung

* Sie erhalten die Zugriffsdaten beim Übungsbetreuer. Empfehlung: setzen sie die Credentials
als Umgebungsvariablen in der Shell.

```bash
$ export AWS_ACCESS_KEY_ID="<your-accesskey>"
$ export AWS_SECRET_ACCESS_KEY="<your-secretkey>"
$ export AWS_DEFAULT_REGION="eu-central-1"
```

* Installieren sie Terraform (https://www.terraform.io/downloads.html) auf ihrem Rechner.

## Getting Started with Terraform

Lesen und folgen sie der Anleitung vom Getting Started Guide von der offiziellen Terraform Seite.

- https://www.terraform.io/intro/index.html
- https://www.terraform.io/intro/getting-started/install.html

Starten sie eine EC2 Instanz in der `eu-central-1` AWS Region und verwenden sie ein Ubuntu 16.04 Image ("ami-97e953f8"). Siehe auch https://cloud-images.ubuntu.com/locator/ec2/

```bash
$ terraform init
$ terraform plan
$ terraform apply
$ terraform show
$ terraform plan -destroy
$ terraform destroy
```

## Advanced Terraform

Mit Terraform lassen sich auch sehr aufwändige Infrastrukturen aufbauen. Studieren sie das Beispiel
im `adavanced/` Ordner. Planen sie die Ausführung.

Sie benötigen einen SSH Key-Paar, dieses können
sie mit folgendem Kommando erstellen: `cd .ssh/ && ssh-keygen -t rsa -b 2048 -f terraform`

Stellen sie sicher dass das Schlüsselpaar zum lokalen SSH Agent hinzugefügt wurde, damit der Remote Exec Provisioniere die Verbindung erfolgreich aufbauen kann.

*Windows Nutzer wird empfohlen hierfür `WinSCP` mit der `Pageant` Komponente zu installieren.* 
