FROM centos:7.4.1708

RUN yum install -y epel-release && \
    yum install nginx vim wget && \
    systemctl start nginx && \
    systemctl enable nginx && \

RUN echo "\ndaemon off;" >> /etc/nginx/nginx.conf
RUN sed -i -e "s/;\?daemonize\s*=\s*yes/daemonize = no/g" /etc/php/7.0/fpm/php-fpm.conf

EXPOSE 80
CMD service php7.0-fpm start && nginx
