Damit der Code funktioniert, muss die folgende Prozedur durchgeführt werden:
 
 * Ein DC/OS Cluster in Amazon EC2 starten: https://dcos.io/docs/1.7/administration/installing/cloud/aws
 * Im Reiter "Output" des Cloud Formation Service die Master-URL kopieren und in die statische Variable MARATHON_ENDPOINT einsetzen
 * Den DC/OS Kommandozeilen-Client installieren: https://dcos.io/docs/1.7/administration/security/managing-authentication/#log-in-cli
 * Einen Login des DC/OS Kommandozeilen-Clients durchführen und ggF. vorab die DCOS URL und den DCOS Token per `dcos config set` aktualisieren: https://dcos.io/docs/1.7/administration/security/managing-authentication/#log-in-cli
 * Den JWT-Key per `dcos config show core.dcos_acs_token` auslesen und in die Datei `dcos-jwt.txt` übernehmen.