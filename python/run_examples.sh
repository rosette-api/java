#!/usr/bin/env bash -e

mvn clean install
cd target/github-publish/examples
for f in *.py
do
    python $f --key $1
done
