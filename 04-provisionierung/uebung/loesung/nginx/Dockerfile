FROM alpine
MAINTAINER Josef Adersberger, jad@qaware.de
RUN apk update
RUN apk add nginx
ADD https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/nginx/nginx.conf /etc/nginx/nginx.conf
RUN mkdir -p /run/nginx
ENTRYPOINT exec nginx
EXPOSE 80
