FROM alpine
MAINTAINER Josef Adersberger, jad@qaware.de
RUN apk update
RUN apk add haproxy
ADD <CFG.URL> /etc/haproxy/haproxy-nginx.conf
ENTRYPOINT exec haproxy -f /etc/haproxy/haproxy-nginx.conf
EXPOSE 80
