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
        iputils-ping \
        libssl-dev \
        lsb-release \
        python-all \
        python-dev \
        python-pip \
        python-software-properties \
        software-properties-common \
        rlwrap \
        vim \
        zlib1g \
        zlib1g-dev

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

# Install PHP and Compser
RUN apt-get install -y php5
RUN curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
COPY docker/php.ini /etc/php5/cli/php.ini 

# Python
RUN cd /lib && ln -s x86_64-linux-gnu/libz.so.1 libz.so
RUN cd /lib && ln -s /usr/lib/x86_64-linux-gnu/libssl.so.1.0.0 libssl.so
RUN wget http://www.python.org/ftp/python/2.6.9/Python-2.6.9.tar.xz
RUN xz -d Python-2.6.9.tar.xz && tar -xvf Python-2.6.9.tar && rm -rf Python-2.6.9.tar
RUN cd Python-2.6.9 && ./configure && make && make altinstall

RUN wget http://www.python.org/ftp/python/3.3.6/Python-3.3.6.tar.xz
RUN xz -d Python-3.3.6.tar.xz && tar -xvf Python-3.3.6.tar && rm -rf Python-3.3.6.tar
RUN cd Python-3.3.6 && ./configure && make && make altinstall

RUN wget http://www.python.org/ftp/python/3.4.3/Python-3.4.3.tar.xz
RUN xz -d Python-3.4.3.tar.xz && tar -xvf Python-3.4.3.tar && rm -rf Python-3.4.3.tar
RUN cd Python-3.4.3 && ./configure &&  make && make altinstall

RUN pip install --upgrade pip
RUN pip install --upgrade virtualenv
RUN pip install tox

COPY docker/ssh/ /root/.ssh

CMD /bin/bash

VOLUME ["/source"]
