FROM ubuntu:precise
MAINTAINER Chris Park <cpark@basistech.com>
ENV REFRESHED_AT 2015-10-07

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
        pound \
    && rm -rf /var/lib/apt/lists/*;

RUN curl https://deb.nodesource.com/node_4.x/pool/main/n/nodejs/nodejs_4.1.1-1nodesource1~precise1_amd64.deb > node.deb \
    && dpkg -i node.deb \
    && rm node.deb

RUN npm install -g node-gyp\
    && ln -s $(which node-gyp) $(dirname $(which node-gyp))/node-gyp\
    && npm cache clear \
    && node-gyp configure || echo ""

ENV NODE_ENV dev

RUN apt-get update \
    && apt-get upgrade -y --force-yes \
    && rm -rf /var/lib/apt/lists/*;

RUN cd /etc/ssl && openssl req -x509 -nodes -days 365 -subj '/C=US/ST=Massachusetts/L=Cambridge/CN=api.rosette.com' -newkey rsa:2014 -keyout api.rosette.com.pem -out api.rosette.com.pem
# Modify the pound.cfg file to route localhost:443 to jugmaster.basistech.net
RUN sed -i 's/^Control/# Control/' /etc/pound/pound.cfg
RUN sed -i 's/ListenHTTP/ListenHTTPS/' /etc/pound/pound.cfg
RUN sed -i 's/www-data/root/g' /etc/pound/pound.cfg
RUN sed -i 's/Port.*8080/Port 443\n\tCert "\/etc\/ssl\/api.rosette.com.pem"/' /etc/pound/pound.cfg
RUN sed -i ':a;N;$!ba;s/127.0.0.1/jugmaster.basistech.net/2' /etc/pound/pound.cfg
RUN sed -i 's/127.0.0.1/api.rosette.com/' /etc/pound/pound.cfg
RUN sed -i 's/startup=0/startup=1/' /etc/default/pound
# Route api.rosette.com to localhost - unfortunately, docker doesn't allow
# editing of the /etc/hosts file, so instead use the workaround from jasonincode.com/customizing-hosts-file-in-docker
# uncomment below to use the workaround

#RUN cp /etc/hosts /tmp/hosts
#RUN sed -i '/127.0.0.1.*localhost/a 127.0.0.1\tapi.rosette.com' /tmp/hosts
#RUN mkdir -p -- /lib-override && cp /lib/x86_64-linux-gnu/libnss_files.so.2 /lib-override
#RUN sed -i 's:/etc/hosts:/tmp/hosts:g' /lib-override/libnss_files.so.2
#ENV LD_LIBRARY_PATH /lib-override

# Or, just add the api.rosette.com route via --add-host="127.0.0.1 api.rosette.com" on the docker run command

# Tell Node not to worry about the self-signed certificate
ENV NODE_TLS_REJECT_UNAUTHORIZED 0

CMD /etc/init.d/pound restart && /bin/bash

VOLUME ["/test"]
