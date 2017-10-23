FROM centos:7.4.1708

RUN yum install -y epel-release && \
    yum install -y wget nginx && \
    yum install -y php php-mysql php-fpm && \

    sed -i -e "s/;\?cgi.fix_pathinfo\s*=\s*1/cgi.fix_pathinfo = 0/g" /etc/php.ini && \
    sed -i -e "s/daemonize = no/daemonize = yes/g" /etc/php-fpm.conf && \

    sed -i -e "s/;\?listen.owner\s*=\s*nobody/listen.owner = nobody/g" /etc/php-fpm.d/www.conf && \
    sed -i -e "s/;\?listen.group\s*=\s*nobody/listen.group = nobody/g" /etc/php-fpm.d/www.conf && \

    sed -i -e "s/user = apache/user = nginx/g" /etc/php-fpm.d/www.conf && \
    sed -i -e "s/group = apache/group = nginx/g" /etc/php-fpm.d/www.conf

COPY docker/php.conf /etc/nginx/default.d/

COPY docker/index.html /usr/share/nginx/html/
COPY docker/info.php /usr/share/nginx/html/

EXPOSE 80
ENTRYPOINT php-fpm && nginx -g 'daemon off;'
