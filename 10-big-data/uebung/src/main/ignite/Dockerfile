FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y gnupg ca-certificates && \
    echo "deb http://apache.org/dist/ignite/deb/ apache-ignite main" > /etc/apt/sources.list.d/ignite.list && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 0EE62FB37A00258D && \
    apt-get update && \
    apt-get install -y --no-install-recommends openjdk-11-jdk apache-ignite && \
    rm -rf /var/lib/apt/lists/*

ENTRYPOINT /usr/share/apache-ignite/bin/ignite.sh
