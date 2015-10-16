#!/bin/bash -e

cd target/github-publish/examples
for f in *.php
do
    if [ "$#" -gt 1 ]; then
        php $f --key $1 --url=$2
    else
        php $f --key $1
    fi
done
