FROM ubuntu:16.04

RUN apt-get update && \
    apt-get -y install wget nginx php7.0-fpm php7.0-mysql && \
    service nginx stop

RUN echo "\ndaemon off;" >> /etc/nginx/nginx.conf
# RUN sed -i -e "s/;\?daemonize\s*=\s*yes/daemonize = no/g" /etc/php/7.0/fpm/php-fpm.conf

EXPOSE 80
CMD service php7.0-fpm start && nginx
