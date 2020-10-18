# für die interaktive Konsole
docker pull alpine:latest
docker run -it alpine:latest /bin/sh

# um den nginx im daemon Modus off Modus zu starten und port 80 Weiterleitung
docker run -d -p 80:80 cloudcomputing/nginx nginx -g "daemon off;"

# dann in einem anderen Konsolenfenster
curl "localhost:80"

