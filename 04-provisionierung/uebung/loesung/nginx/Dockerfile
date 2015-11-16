FROM alpine
MAINTAINER Josef Adersberger, jad@qaware.de
RUN apk update
RUN apk add nginx
ADD <CFG.URL> /etc/nginx/nginx.conf
ENTRYPOINT exec nginx
EXPOSE 80
