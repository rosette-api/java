FROM maven:latest
MAINTAINER Chris Park <cpark@basistech.com>
ENV REFRESHED_AT 2015-10-23

RUN apt-get -yqq update \
    && apt-get install -y --force-yes --no-install-recommends\
        apt-transport-https \
        build-essential \
        curl \
        ca-certificates \
        git \
        lsb-release \
        python-all \
        rlwrap \
        vim \
        iputils-ping

COPY docker/settings-local.xml .m2/settings.xml
COPY docker/settings-global.xml /usr/share/maven/conf/settings.xml

CMD /bin/bash

VOLUME ["/source"]
