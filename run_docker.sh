#!/bin/bash -e

if [ -d docker/ssh ]; then
    rm -rf docker/ssh
fi
mkdir docker/ssh
cp ~/.ssh/* docker/ssh
sudo docker build -t=basistech/build-image .
rm -rf docker/ssh
sudo docker run -it -v $PWD:/source basistech/build-image
