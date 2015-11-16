FROM alpine
MAINTAINER Josef Adersberger, jad@qaware.de
RUN apk update
RUN apk add haproxy
ADD https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/haproxy/haproxy-nginx.cfg /etc/haproxy/haproxy-nginx.conf
ENTRYPOINT exec haproxy -f /etc/haproxy/haproxy-nginx.conf
EXPOSE 80
