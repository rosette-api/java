FROM maven:latest
MAINTAINER Chris Park <cpark@basistech.com>
ENV REFRESHED_AT 2015-11-02

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
        iputils-ping \
        software-properties-common \
        python-software-properties

COPY docker/settings-local.xml /root/.m2/settings.xml
COPY docker/settings-global.xml /usr/share/maven/conf/settings.xml

# Install Node.js
RUN curl https://deb.nodesource.com/node_4.x/pool/main/n/nodejs/nodejs_4.1.1-1nodesource1~precise1_amd64.deb > node.deb \
    && dpkg -i node.deb \
    && rm node.deb

RUN npm install -g node-gyp\
    && ln -s $(which node-gyp) $(dirname $(which node-gyp))/node-gyp\
    && npm cache clear \
    && node-gyp configure || echo ""

RUN npm install -g grunt-cli

ENV NODE_ENV dev

CMD /bin/bash

VOLUME ["/source"]
