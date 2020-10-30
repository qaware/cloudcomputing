FROM ubuntu:20.04

WORKDIR /root

COPY setup.sh .
RUN /bin/bash ./setup.sh \
    rm setup.sh

ENTRYPOINT ["/bin/bash"]