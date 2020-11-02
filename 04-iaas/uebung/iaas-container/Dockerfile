FROM ubuntu:20.04

WORKDIR /root

COPY setup.sh .
RUN /bin/bash ./setup.sh \
    rm setup.sh
COPY configure.sh bin/configure.sh
ENTRYPOINT ["/bin/bash"]